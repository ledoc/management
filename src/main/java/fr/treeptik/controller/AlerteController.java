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

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Alerte;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.TendanceAlerte;
import fr.treeptik.model.TypeAlerte;
import fr.treeptik.service.AlerteService;
import fr.treeptik.service.EnregistreurService;

@Controller
@RequestMapping("/alerte")
public class AlerteController {

	private Logger logger = Logger.getLogger(AlerteController.class);

	@Inject
	private AlerteService alerteService;
	@Inject
	private EnregistreurService enregistreurService;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForm(Model model) throws ControllerException {
		logger.info("--initForm AlerteController--");

		List<Enregistreur> enregistreursCombo;
		List<TypeAlerte> typesAlerteCombo = new ArrayList<TypeAlerte>(
				Arrays.asList(TypeAlerte.values()));

		List<TendanceAlerte> tendancesAlerteCombo = new ArrayList<TendanceAlerte>(
				Arrays.asList(TendanceAlerte.values()));

		try {

			enregistreursCombo = enregistreurService.findAll();

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("alerte", new Alerte());
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("typesAlerteCombo", typesAlerteCombo);
		model.addAttribute("tendancesAlerteCombo", tendancesAlerteCombo);
		return "/alerte/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Alerte alerte, Model model,
			BindingResult result) throws ControllerException {
		logger.info("--create AlerteController-- alerte : " + alerte);
		try {
			alerte.setEmise(false);
			alerteService.create(alerte);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/alerte/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update AlerteController-- alerteId : " + id);

		List<Enregistreur> enregistreursCombo;
		List<TypeAlerte> typesAlerteCombo = new ArrayList<TypeAlerte>(
				Arrays.asList(TypeAlerte.values()));

		List<TendanceAlerte> tendancesAlerteCombo = new ArrayList<TendanceAlerte>(
				Arrays.asList(TendanceAlerte.values()));
		Alerte alerte = null;
		try {
			alerte = alerteService.findById(id);
			enregistreursCombo = enregistreurService.findAll();

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("alerte", alerte);
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("typesAlerteCombo", typesAlerteCombo);
		model.addAttribute("tendancesAlerteCombo", tendancesAlerteCombo);
		return "/alerte/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete AlerteController-- alerteId : " + id);

		try {
			alerteService.remove(id);
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

		List<Alerte> alertes = null;
		List<Alerte> historiqueAlertes = null;
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
				
				alertes = alerteService.findAll();
				historiqueAlertes = alerteService.findAllAlertesEmises();
				alertesTotales = alerteService.countAllAlertes();
				alertesActives = alerteService.countAlertesActives();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				
				alertes = alerteService.findByClientLogin(userLogin);
				historiqueAlertes = alerteService.findAlertesEmisesByClientLogin(userLogin);
				alertesTotales = alerteService
						.countAllAlertesByClientLogin(userLogin);
				alertesActives = alerteService
						.countAlertesActivesByClientLogin(userLogin);

			}

			alertes = alerteService.findAll();
			enregistreursCombo = enregistreurService.findAll();

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("historiqueAlertes", historiqueAlertes);
		model.addAttribute("alertesTotales", alertesTotales);
		model.addAttribute("alertesActives", alertesActives);
		model.addAttribute("alertes", alertes);
		model.addAttribute("alerte", new Alerte());
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("typesAlerteCombo", typesAlerteCombo);
		model.addAttribute("tendancesAlerteCombo", tendancesAlerteCombo);
		return "/alerte/list";
	}

}
