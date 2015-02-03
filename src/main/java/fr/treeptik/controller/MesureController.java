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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.TypeMesure;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.MesureService;

@Controller
@RequestMapping("/mesure")
public class MesureController {

	private Logger logger = Logger.getLogger(MesureController.class);

	@Inject
	private MesureService mesureService;
	@Inject
	private EnregistreurService enregistreurService;

	private Map<Integer, Enregistreur> enregistreurCache;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String create(Model model) throws ControllerException {
		logger.info("--create formulaire MesureController--");

		List<Enregistreur> enregistreursCombo;
		List<TypeMesure> typesMesureCombo = new ArrayList<TypeMesure>(
				Arrays.asList(TypeMesure.values()));
		try {
			enregistreursCombo = enregistreurService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		
		enregistreurCache = new HashMap<Integer, Enregistreur>();
		for (Enregistreur enregistreur : enregistreursCombo) {
			enregistreurCache.put(enregistreur.getId(), enregistreur);
		}
		
		model.addAttribute("typesMesureCombo", typesMesureCombo);
		model.addAttribute("mesure", new Mesure());
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		return "/mesure/create";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Mesure mesure,
			BindingResult result) throws ControllerException {
		logger.debug(result.getAllErrors());
		logger.info("--create MesureController--");
		logger.debug("mesure : " + mesure);
		try {

			/**
			 * TODO : comprendre pourquoi il faut sauver un objet null
			 * :(((((((((
			 */
			mesureService.create(mesure);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/mesure/list";

	}
	

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update MesureController--");
		logger.debug("mesureId : " + id);

		Mesure mesure = null;
		List<Enregistreur> enregistreursCombo;
		enregistreurCache = new HashMap<Integer, Enregistreur>();

		try {
			mesure = mesureService.findById(id);

			enregistreursCombo = enregistreurService.findAll();

			for (Enregistreur enregistreur : enregistreursCombo) {
				enregistreurCache.put(enregistreur.getId(), enregistreur);
			}
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("ouvragesCombo", enregistreursCombo);
		model.addAttribute("mesure", mesure);
		return "/mesure/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete MesureController--");
		logger.debug("mesureId : " + id);

		try {
			mesureService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/mesure/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list MesureController--");

		List<Mesure> mesures = null;
		try {

			mesures = mesureService.findAll();

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("mesures", mesures);
		return "/mesure/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list/enregistreur/{id}")
	public String listByEnregistreur(Model model, HttpServletRequest request,
			@PathVariable("id") Integer id) throws ControllerException {
		logger.info("--listByEnregistreur MesureController--");

		List<Mesure> mesures = null;
		try {

			mesures = mesureService.findByEnregistreurId(id);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("mesures", mesures);
		return "/mesure/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list/ouvrage/{id}")
	public String listByOuvrage(Model model, HttpServletRequest request,
			@PathVariable("id") Integer id) throws ControllerException {
		logger.info("--listByOuvrage MesureController--");

		List<Mesure> mesures = null;
		try {

			mesures = mesureService.findByEnregistreurId(id);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("mesures", mesures);
		return "/mesure/list";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ControllerException {


		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"), true));
		
		binder.registerCustomEditor(List.class, "enregistreurs",
				new CustomCollectionEditor(List.class) {
					protected Object convertElement(Object element) {
						if ((!element.equals("")) || (!element.equals(null))) {
							if (element instanceof Enregistreur) {
								logger.info("Conversion d'un Enregistreur en Enregistreur: "
										+ element);
								return element;
							}
							try {
								if (element instanceof String
										|| element instanceof Integer) {
									Enregistreur enregistreur;
									if (element instanceof String) {
										enregistreur = enregistreurService.findById(Integer
												.valueOf((String) element));
									} else {

										enregistreur = enregistreurService
												.findById((int) element);

										logger.info("Recherche de l'enregistreur pour l'Id : "
												+ element + ": " + enregistreur);
									}
									return enregistreur;
								}
							} catch (ServiceException e) {
								logger.error(e.getMessage());
							}
							logger.debug("Problème avec l'élement : " + element);
						}
						return null;
					}
				});

	}

	public Map<Integer, Enregistreur> getEnregistreurCache() {
		return enregistreurCache;
	}

	public void setEnregistreurCache(Map<Integer, Enregistreur> enregistreurCache) {
		this.enregistreurCache = enregistreurCache;
	}

}
