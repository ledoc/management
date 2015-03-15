package fr.treeptik.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Capteur;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.TypeCaptAlerteMesure;
import fr.treeptik.model.TypeOuvrage;
import fr.treeptik.service.CapteurService;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.TypeCaptAlerteMesureService;
import fr.treeptik.util.DateMesureComparator;

@Controller
@SessionAttributes("enregistreur")
@RequestMapping("/capteur")
public class CapteurController {

	private Logger logger = Logger.getLogger(CapteurController.class);

	@Inject
	private CapteurService capteurService;

	@Inject
	private EnregistreurService enregistreurService;

	@Inject
	private TypeCaptAlerteMesureService typeCaptAlerteMesureService;

	@Inject
	private MesureService mesureService;

	/**
	 * 
	 * @param model
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/create/{enregistreurId}")
	public String initForm(Model model,
			@PathVariable("enregistreurId") Integer enregistreurId)
			throws ControllerException {
		logger.info("--initForm CapteurController-- enregistreurId = "
				+ enregistreurId);

		Enregistreur enregistreur = new Enregistreur();
		Capteur capteur = new Capteur();
		List<TypeCaptAlerteMesure> typeCaptAlerteMesureCombo;

		try {

			typeCaptAlerteMesureCombo = typeCaptAlerteMesureService.findAll();

			typeCaptAlerteMesureCombo.removeIf(t -> t.getNom().equals(
					"NIVEAUMANUEL"));

			enregistreur = enregistreurService.findById(enregistreurId);
			capteur.setEnregistreur(enregistreur);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("typeCaptAlerteMesureCombo",
				typeCaptAlerteMesureCombo);
		model.addAttribute("capteur", capteur);
		model.addAttribute("mesure", new Mesure());
		return "/capteur/create";
	}

	/**
	 *
	 * Create En POST
	 * 
	 * @param capteur
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Capteur capteur, Model model)
			throws ControllerException {
		logger.info("--create CapteurController-- capteur : " + capteur);

		Enregistreur enregistreur = new Enregistreur();

		try {

			capteur = capteurService.create(capteur);

			mesureService.findByCapteurIdWithFetch(capteur.getId());

			enregistreur = enregistreurService.findByIdWithJoinCapteurs(capteur
					.getEnregistreur().getId());

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/enregistreur/update/" + enregistreur.getId();

	}

	@RequestMapping(value = "/redirect/capteur", method = RequestMethod.POST)
	public String redirectCapteur(@ModelAttribute Capteur capteur,
			BindingResult result, Model model) throws ControllerException {

		logger.debug(result.getAllErrors());
		logger.info("--redirectCapteur CapteurController-- capteur" + capteur);

		List<Mesure> listNiveauxManuels = new ArrayList<Mesure>();

		try {

			if (capteur.getMesures() != null) {
				listNiveauxManuels = capteur
						.getMesures()
						.stream()
						.filter(m -> m.getTypeCaptAlerteMesure().getNom()
								.equals("NIVEAUMANUEL"))
						.collect(Collectors.toList());
			}

		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("listNiveauxManuels", listNiveauxManuels);

		return "/capteur/create";

	}

	/**
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(HttpServletRequest request, Model model,
			@PathVariable("id") Integer id) throws ControllerException {
		logger.info("--update CapteurController -- capteurId : " + id);

		Capteur capteur = null;
		List<Mesure> listNiveauxManuels = new ArrayList<Mesure>();
		List<TypeCaptAlerteMesure> typeCaptAlerteMesureCombo;
		
		try {

			List<Mesure> listMesures = mesureService
					.findByCapteurIdWithFetch(id);
			capteur = capteurService.findById(id);
			typeCaptAlerteMesureCombo = typeCaptAlerteMesureService.findAll();

			if (listMesures != null) {
				listNiveauxManuels = listMesures
						.stream()
						.filter(m -> m.getTypeCaptAlerteMesure().getNom()
								.equals("NIVEAUMANUEL"))
						.collect(Collectors.toList());
			}

			Collections.sort(listNiveauxManuels, new DateMesureComparator());
			Collections.reverse(listNiveauxManuels);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("listNiveauxManuels", listNiveauxManuels);
		
		model.addAttribute("capteur", capteur);
		model.addAttribute("typeCaptAlerteMesureCombo", typeCaptAlerteMesureCombo);
		return "/capteur/create";
	}

	/**
	 * @param model
	 * @param id
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete CapteurController--");
		logger.debug("capteurId : " + id);
		Integer enregistreurId;

		try {
			enregistreurId = capteurService.findById(id).getEnregistreur()
					.getId();
			capteurService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/enregistreur/update/" + enregistreurId;
	}

	/**
	 * 
	 * 
	 * @param model
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model) throws ControllerException {
		logger.info("--list CapteurController--");

		List<Capteur> capteurs = null;
		try {
			capteurs = capteurService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("capteurs", capteurs);
		return "/capteur/list";
	}

	@RequestMapping(value = "/create/niveau/manuel", method = RequestMethod.POST)
	public String createNiveauManuel(@ModelAttribute Capteur capteur,
			BindingResult result) throws ControllerException {
		logger.info(result.getGlobalError());

		logger.info("--createNiveauManuel CapteurController -- capteur : "
				+ capteur);

		System.out.println(capteur.getNiveauManuel().getDate());

		Mesure mesure = new Mesure();
		try {

			logger.info("mesure : " + mesure);
			mesure = mesureService.create(capteur.getNiveauManuel());
			TypeCaptAlerteMesure typeCaptAlerteMesure = typeCaptAlerteMesureService
					.findById(capteur.getTypeCaptAlerteMesure().getId());
			capteur = capteurService.create(capteur);
			mesure.setCapteur(capteur);
			mesure.setTypeCaptAlerteMesure(typeCaptAlerteMesureService
					.findByNom("NIVEAUMANUEL"));
			mesureService.update(mesure);

			logger.info("capteur.getNiveauManuel() : "
					+ capteur.getNiveauManuel());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/capteur/update/" + capteur.getId();
	}
}
