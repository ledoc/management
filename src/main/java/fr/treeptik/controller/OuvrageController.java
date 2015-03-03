package fr.treeptik.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.ResponseBody;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;
import fr.treeptik.model.TypeOuvrage;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.OuvrageService;
import fr.treeptik.service.SiteService;
import fr.treeptik.service.TypeOuvrageService;

@Controller
@RequestMapping("/ouvrage")
public class OuvrageController {

	private Logger logger = Logger.getLogger(OuvrageController.class);

	@Inject
	private OuvrageService ouvrageService;
	@Inject
	private EnregistreurService enregistreurService;
	@Inject
	private SiteService siteService;
	@Inject
	private TypeOuvrageService typeOuvrageService;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForm(Model model) throws ControllerException {
		logger.info("--initForm OuvrageController--");
		List<Ouvrage> ouvragesCombo;
		List<TypeOuvrage> typesOuvrageCombo;
		List<Enregistreur> enregistreursCombo;
		List<Site> sitesCombo;
		Ouvrage ouvrage = new Ouvrage();

		try {
			sitesCombo = siteService.findAll();
			enregistreursCombo = enregistreurService.findFreeEnregistreurs();
			ouvragesCombo = ouvrageService.findAll();
			typesOuvrageCombo = typeOuvrageService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("ouvrage", ouvrage);
		model.addAttribute("ouvragesCombo", ouvragesCombo);
		model.addAttribute("sitesCombo", sitesCombo);
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("typesOuvrageCombo", typesOuvrageCombo);
		return "/ouvrage/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Ouvrage ouvrage, BindingResult result)
			throws ControllerException {
		logger.info(result.getAllErrors());

		logger.info("--create OuvrageController-- ouvrage : " + ouvrage
				+ " -- enregistreurs de l'ouvrage : "
				+ ouvrage.getEnregistreurs());
		try {

			if (ouvrage.getOuvrageMaitre() != null
					&& ouvrage.getOuvrageMaitre().getId() == null) {
				ouvrage.setOuvrageMaitre(null);
			}

			ouvrageService.create(ouvrage);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/ouvrage/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update OuvrageController-- ouvrageId : " + id);
		Ouvrage ouvrage = null;
		List<Ouvrage> ouvragesCombo;
		List<Enregistreur> enregistreursCombo;
		List<Site> sitesCombo;
		List<TypeOuvrage> typesOuvrageCombo;

		
		try {
			ouvrage = ouvrageService.findByIdWithJoinFetchEnregistreurs(id);

			ouvragesCombo = ouvrageService.findAll();
			ouvragesCombo.remove(ouvrage);
			typesOuvrageCombo = typeOuvrageService.findAll();
			enregistreursCombo = enregistreurService.findFreeEnregistreurs();
			enregistreursCombo.addAll(ouvrage.getEnregistreurs());
			sitesCombo = siteService.findAll();

			
			
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("ouvrage", ouvrage);
		model.addAttribute("ouvragesCombo", ouvragesCombo);
		model.addAttribute("sitesCombo", sitesCombo);
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("typesOuvrageCombo", typesOuvrageCombo);
		return "/ouvrage/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete OuvrageController-- ouvrageId : " + id);

		try {
			ouvrageService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/ouvrage/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = {"/list", "/"})
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list OuvrageController--");

		List<Ouvrage> ouvrages = null;
		try {
			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				ouvrages = ouvrageService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				ouvrages = ouvrageService.findByClientLogin(userLogin);
			}
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("ouvrages", ouvrages);
		return "/ouvrage/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/init/type/ouvrage")
	public @ResponseBody List<TypeOuvrage> initSelectTypeOuvrage()
			throws ControllerException {

		logger.info("--initSelectTypeOuvrage OuvrageController");

		List<TypeOuvrage> allTypeOuvrage = new ArrayList<TypeOuvrage>();
		try {
			allTypeOuvrage = typeOuvrageService.findAll();
			logger.debug("liste des types d'ouvrage renvoyée : "
					+ allTypeOuvrage);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return allTypeOuvrage;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ControllerException {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"), true));

	}



}
