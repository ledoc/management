package fr.treeptik.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Alerte;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.TypeMesure;
import fr.treeptik.service.AlerteService;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.OuvrageService;

@Controller
@RequestMapping("/enregistreur")
@SessionAttributes("enregistreur")
public class EnregistreurController {

	private Logger logger = Logger.getLogger(EnregistreurController.class);

	@Inject
	private EnregistreurService enregistreurService;
	@Inject
	private OuvrageService ouvrageService;
	@Inject
	private MesureService mesureService;
	@Inject
	private AlerteService alerteService;

	@RequestMapping(value = "/init", method = RequestMethod.POST)
	public String initEnregistreur(@ModelAttribute Enregistreur enregistreur,
			BindingResult result, Model model) throws ControllerException {

		logger.debug(result.getAllErrors());
		logger.info("--create EnregistreurController-- " + enregistreur);

		List<Alerte> alertesCombo;
		Map<Integer, Alerte> alerteCache;

		List<TypeMesure> typesMesureCombo = new ArrayList<TypeMesure>(
				Arrays.asList(TypeMesure.values()));
		alerteCache = new HashMap<Integer, Alerte>();

		try {

			alertesCombo = alerteService.findAll();

			for (Alerte alerte : alertesCombo) {
				alerteCache.put(alerte.getId(), alerte);
			}
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("enregistreur", enregistreur);
		model.addAttribute("typesMesureCombo", typesMesureCombo);
		model.addAttribute("alertesCombo", alertesCombo);

		return "/enregistreur/create";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/create/{ouvrageId}")
	public String initForm(Model model,
			@PathVariable("ouvrageId") Integer ouvrageId)
			throws ControllerException {
		logger.info("--create formulaire EnregistreurController-- ouvrageId = "
				+ ouvrageId);

		Enregistreur enregistreur = new Enregistreur();
		Ouvrage ouvrage = new Ouvrage();
		List<TypeMesure> typesMesureCombo = new ArrayList<TypeMesure>(
				Arrays.asList(TypeMesure.values()));
		List<Alerte> alertesCombo;
		Map<Integer, Alerte> alerteCache;

		try {
			ouvrage = ouvrageService.findById(ouvrageId);
			alertesCombo = alerteService.findAll();

			enregistreur.setOuvrage(ouvrage);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		alerteCache = new HashMap<Integer, Alerte>();
		for (Alerte alerte : alertesCombo) {
			alerteCache.put(alerte.getId(), alerte);
		}

		model.addAttribute("enregistreur", enregistreur);
		model.addAttribute("mesure", new Mesure());
		model.addAttribute("typesMesureCombo", typesMesureCombo);
		model.addAttribute("alertesCombo", alertesCombo);
		return "/enregistreur/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Enregistreur enregistreur,
			BindingResult result) throws ControllerException {
		logger.debug(result.getAllErrors());
		logger.info("--create EnregistreurController--");
		logger.debug("enregistreur : " + enregistreur);
		logger.debug(" enregistreur Mesure Enregistreur "
				+ enregistreur.getDerniereMesure());
		Ouvrage ouvrage = new Ouvrage();
		try {

			ouvrage = ouvrageService
					.findByIdWithJoinFetchEnregistreurs(enregistreur
							.getOuvrage().getId());

			// TODO HACK A supprimer
			if (enregistreur.getDerniereMesure() != null
					&& enregistreur.getDerniereMesure().getId() == null) {
				enregistreur.setDerniereMesure(null);
			}
			if (enregistreur.getNiveauManuel() != null
					&& enregistreur.getNiveauManuel().getId() == null) {
				Mesure niveauManuel = mesureService.create(enregistreur
						.getNiveauManuel());
				enregistreur = enregistreurService.create(enregistreur);
				niveauManuel.setEnregistreur(enregistreur);
				mesureService.update(niveauManuel);
			} else {
				enregistreur = enregistreurService.create(enregistreur);
			}
			if (!ouvrage.getEnregistreurs().contains(enregistreur)) {
				ouvrage.getEnregistreurs().add(enregistreur);
				ouvrageService.update(ouvrage);
			}

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/ouvrage/update/" + ouvrage.getId() ;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update EnregistreurController--");
		logger.debug("enregistreurId : " + id);

		Enregistreur enregistreur = null;
		List<Alerte> alertesCombo;
		Map<Integer, Alerte> alerteCache;

		List<TypeMesure> typesMesureCombo = new ArrayList<TypeMesure>(
				Arrays.asList(TypeMesure.values()));
		alerteCache = new HashMap<Integer, Alerte>();

		try {
			enregistreur = enregistreurService
					.findByIdWithJoinFetchAlertesActives(id);

			alertesCombo = alerteService.findAll();

			for (Alerte alerte : alertesCombo) {
				alerteCache.put(alerte.getId(), alerte);
			}
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("enregistreur", enregistreur);
		model.addAttribute("typesMesureCombo", typesMesureCombo);
		model.addAttribute("alertesCombo", alertesCombo);
		return "/enregistreur/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete EnregistreurController--");
		logger.debug("enregistreurId : " + id);
		Integer ouvrageId;
		try {
			ouvrageId = enregistreurService.findById(id).getOuvrage().getId();

			enregistreurService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/ouvrage/update/" + ouvrageId ;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list EnregistreurController--");

		List<Enregistreur> enregistreurs = null;
		try {

			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				enregistreurs = enregistreurService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				enregistreurs = enregistreurService.findAll();
			}

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("enregistreurs", enregistreurs);
		return "/enregistreur/list";
	}

	@RequestMapping(value = "/update/niveau/manuel", method = RequestMethod.POST)
	public String updateNiveauManuel(@ModelAttribute Enregistreur enregistreur,
			BindingResult result) throws ControllerException {
		logger.info(result.getGlobalError());

		logger.info("--updateNiveauManuel EnregistreurController--");
		logger.debug("enregistreur : " + enregistreur);

		Mesure mesure = new Mesure();
		try {

			mesure = mesureService.create(enregistreur.getNiveauManuel());
			logger.info("mesure : " + mesure);
			enregistreur.setNiveauManuel(mesure);
			enregistreur = enregistreurService.create(enregistreur);
			mesure.setEnregistreur(enregistreur);
			mesureService.update(mesure);

			logger.info("enregistreur.getNiveauManuel() : "
					+ enregistreur.getNiveauManuel());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/enregistreur/update/" + enregistreur.getId();

	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ControllerException {

		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"), true));

		binder.registerCustomEditor(List.class, "alertes",
				new CustomCollectionEditor(List.class) {
					protected Object convertElement(Object element) {
						Map<Integer, Alerte> alerteCache = new HashMap<Integer, Alerte>();

						if (element instanceof Alerte) {
							logger.info("Conversion d'un Alerte en Alerte: "
									+ element);
							return element;
						}
						if (element instanceof String
								|| element instanceof Integer) {
							Alerte alerte;
							if (element instanceof String) {
								alerte = alerteCache.get(Integer
										.valueOf((String) element));
							} else {

								alerte = alerteCache.get(element);
								logger.info("Recherche du alerte pour l'Id : "
										+ element + ": " + alerte);
							}
							return alerte;
						}
						logger.debug("Problème avec l'élement : " + element);
						return null;
					}
				});
	}

}