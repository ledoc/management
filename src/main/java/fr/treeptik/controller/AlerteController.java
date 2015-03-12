package fr.treeptik.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.AlerteDescription;
import fr.treeptik.model.AlerteEmise;
import fr.treeptik.model.Capteur;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.TendanceAlerte;
import fr.treeptik.model.TypeAlerte;
import fr.treeptik.model.TypeMesureOrTrame;
import fr.treeptik.service.AlerteDescriptionService;
import fr.treeptik.service.AlerteEmiseService;
import fr.treeptik.service.CapteurService;
import fr.treeptik.service.EnregistreurService;

@Controller
@RequestMapping("/alerte")
public class AlerteController {

	private Logger logger = Logger.getLogger(AlerteController.class);

	@Inject
	private AlerteDescriptionService alerteDescriptionService;
	@Inject
	private AlerteEmiseService alerteEmiseService;

	@Inject
	private CapteurService capteurService;
	@Inject
	private EnregistreurService enregistreurService;

	@RequestMapping(method = RequestMethod.GET, value = "/acquittement/{id}")
	public String acquittementAlerte(@PathVariable("id") Integer id,
			RedirectAttributes redirectAttributes) throws ControllerException {

		logger.info("--acquittementAlerte AlerteController--");

		try {

			alerteEmiseService.acquittementAlerte(id);

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		redirectAttributes.addFlashAttribute("messageSuccess", "OK");

		return "redirect:/alerte/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/description/create")
	public String initForm(Model model) throws ControllerException {
		logger.info("--initForm AlerteController--");

		List<Enregistreur> enregistreursCombo;
		List<TypeMesureOrTrame> typesMesureOrTrameCombo = new ArrayList<TypeMesureOrTrame>(
				Arrays.asList(TypeMesureOrTrame.values()));

		List<TendanceAlerte> tendancesAlerteCombo = new ArrayList<TendanceAlerte>(
				Arrays.asList(TendanceAlerte.values()));

		try {

			enregistreursCombo = enregistreurService.findAll();

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("alerte", new AlerteDescription());
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("typesMesureOrTrameCombo", typesMesureOrTrameCombo);
		model.addAttribute("tendancesAlerteCombo", tendancesAlerteCombo);
		return "/alerte/create";
	}

	@RequestMapping(value = "/description/create", method = RequestMethod.POST)
	public String create(@ModelAttribute AlerteDescription alerteDescription,
			Model model, BindingResult result) throws ControllerException {
		logger.info("--create AlerteController-- alerte : " + alerteDescription);
		try {
			Capteur capteur;

			System.out.println(alerteDescription.getCapteur()
					.getTypeMesureOrTrame()
					+ " -- - - -- "
					+ alerteDescription.getCapteur().getEnregistreur().getId());

			if (alerteDescription.getId() != null) {
				capteur = capteurService.findById(alerteDescription
						.getCapteur().getId());
				alerteDescriptionService.create(alerteDescription);
			} else {
				capteur = capteurService
						.findByEnregistreurAndTypeMesureOrTrame(
								alerteDescription.getCapteur()
										.getTypeMesureOrTrame(),
								alerteDescription.getCapteur()
										.getEnregistreur().getId());
				alerteDescription.setCapteur(capteur);
				alerteDescription.setaSurveiller(false);
				alerteDescription.setCompteurRetourNormal(0);
				alerteDescriptionService.create(alerteDescription);
			}

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/alerte/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/description/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update AlerteController-- alerteId : " + id);

		List<Enregistreur> enregistreursCombo;
		List<TypeAlerte> typesAlerteCombo = new ArrayList<TypeAlerte>(
				Arrays.asList(TypeAlerte.values()));

		List<TendanceAlerte> tendancesAlerteCombo = new ArrayList<TendanceAlerte>(
				Arrays.asList(TendanceAlerte.values()));
		AlerteDescription alerteDescription = null;
		try {
			alerteDescription = alerteDescriptionService.findById(id);
			enregistreursCombo = enregistreurService.findAll();

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("alerte", alerteDescription);
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("typesAlerteCombo", typesAlerteCombo);
		model.addAttribute("tendancesAlerteCombo", tendancesAlerteCombo);
		return "/alerte/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/emise/view/{id}")
	public String viewAlerteEmise(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update AlerteController-- alerteId : " + id);

		AlerteEmise alerteEmise = null;
		try {
			alerteEmise = alerteEmiseService.findById(id);

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("alerte", alerteEmise);
		return "/alerte/view-emise";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/description/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete AlerteController-- alerteId : " + id);

		try {
			alerteDescriptionService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/alerte/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list AlerteController--");

		List<AlerteDescription> alerteDescriptions = null;
		List<AlerteEmise> historiqueAlertes = null;
		List<Enregistreur> enregistreursCombo;
		List<TypeAlerte> typesAlerteCombo = new ArrayList<TypeAlerte>(
				Arrays.asList(TypeAlerte.values()));

		List<TendanceAlerte> tendancesAlerteCombo = new ArrayList<TendanceAlerte>(
				Arrays.asList(TendanceAlerte.values()));

		Long alertesActives = null;
		Long alertesTotales = null;

		try {

			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {

				enregistreursCombo = enregistreurService.findAll();
				alerteDescriptions = alerteDescriptionService.findAll();
				historiqueAlertes = alerteEmiseService.findAll();
				alertesTotales = alerteDescriptionService.countAll();
				alertesActives = alerteDescriptionService.countAllActives();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);

				alerteDescriptions = alerteDescriptionService
						.findAllByClientLogin(userLogin);
				historiqueAlertes = alerteEmiseService
						.findAllByClientLogin(userLogin);

				alertesTotales = alerteDescriptionService
						.countAllByClientLogin(userLogin);
				alertesActives = alerteDescriptionService
						.countAllActivesByClientLogin(userLogin);
				enregistreursCombo = enregistreurService
						.findByClientLogin(userLogin);
			}

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("historiqueAlertes", historiqueAlertes);
		model.addAttribute("alertesTotales", alertesTotales);
		model.addAttribute("alertesActives", alertesActives);
		model.addAttribute("alertes", alerteDescriptions);
		model.addAttribute("alerte", new AlerteDescription());
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("typesAlerteCombo", typesAlerteCombo);
		model.addAttribute("tendancesAlerteCombo", tendancesAlerteCombo);
		return "/alerte/list";
	}

}
