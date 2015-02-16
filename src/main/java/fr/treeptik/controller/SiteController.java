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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Site;
import fr.treeptik.model.TypeSite;
import fr.treeptik.service.EtablissementService;
import fr.treeptik.service.SiteService;
import fr.treeptik.spring.EtablissementCustomEditor;

@Controller
@RequestMapping("/site")
public class SiteController {

	private Logger logger = Logger.getLogger(SiteController.class);

	@Inject
	private EtablissementCustomEditor etablissementCustomEditor;
	
	@Inject
	private SiteService siteService;
	@Inject
	private EtablissementService etablissementService;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForm(Model model) throws ControllerException {
		logger.info("--initForm SiteController--");

		List<TypeSite> typesSiteCombo = new ArrayList<TypeSite>(
				Arrays.asList(TypeSite.values()));

		List<Etablissement> etablissementsCombo;

		Site site = new Site();

		try {
			etablissementsCombo = etablissementService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("site", site);
		model.addAttribute("typesSiteCombo", typesSiteCombo);
		model.addAttribute("etablissementsCombo", etablissementsCombo);

		return "/site/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Site site, BindingResult result)
			throws ControllerException {
		logger.info("Erreurs : " + result.getAllErrors());

		logger.info("--create SiteController-- site : " + site
				+ " -- ouvrages : " + site.getOuvrages());

		logger.info("--create SiteController-- etablissment : "
				+ site.getEtablissement());
		try {
			site = siteService.create(site);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/site/list";

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update SiteController-- siteId : " + id);

		Site site = new Site();
		List<TypeSite> typesSiteCombo = new ArrayList<TypeSite>(
				Arrays.asList(TypeSite.values()));

		List<Etablissement> etablissementsCombo;

		try {
			site = siteService.findByIdWithJoinFetchOuvrages(id);
			etablissementsCombo = etablissementService.findAll();

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("site", site);
		
		model.addAttribute("typesSiteCombo", typesSiteCombo);
		model.addAttribute("etablissementsCombo", etablissementsCombo);
		return "/site/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete SiteController-- siteId : " + id);

		try {
			siteService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/site/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = {"/list", "/"})
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list SiteController--");

		List<Site> sites = null;
		try {
			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				sites = siteService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				sites = siteService.findByClientLogin(userLogin);
			}
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("sites", sites);
		return "/site/list";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ControllerException {
		binder.registerCustomEditor(Etablissement.class, "etablissement", etablissementCustomEditor);
	}

}
