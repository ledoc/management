package fr.treeptik.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import fr.treeptik.service.EtablissementService;
import fr.treeptik.service.SiteService;

@Controller
@RequestMapping("/etablissement")
public class EtablissementController {

	private Logger logger = Logger.getLogger(SiteController.class);
	

	@Inject
	private EtablissementService etablissementService;
	@Inject
	private SiteService siteService;
	private Map<Integer, Site> siteCache;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String create(Model model) throws ControllerException {
		logger.info("--create formulaire EtablissementController--");
		
		List<Site> sitesCombo;
		try {
			sitesCombo = siteService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		siteCache = new HashMap<Integer, Site>();
		for (Site site : sitesCombo) {
			siteCache.put(site.getId(), site);
		}

		model.addAttribute("etablissement", new Etablissement());
		model.addAttribute("sitesCombo", sitesCombo);
		return "/etablissement/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id) throws ControllerException {
		logger.info("--update EtablissementController--");
		logger.debug("etablissementId : " + id);

		Etablissement etablissement = null;
		List<Site> sitesCombo;
		siteCache = new HashMap<Integer, Site>();

		try {
			etablissement = etablissementService.findByIdWithJoinFetchSites(id);

			sitesCombo = siteService.findAll();

			for (Site site : sitesCombo) {
				siteCache.put(site.getId(), site);
			}
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("sitesCombo", sitesCombo);
		model.addAttribute("etablissement", etablissement);
		return "/etablissement/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id) throws ControllerException {
		logger.info("--delete EtablissementController--");
		logger.debug("etablissementId : " + id);

		try {
			etablissementService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/etablissement/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Etablissement etablissement) throws ControllerException {
		logger.info("--create EtablissementController--");
		logger.debug("etablissement : " + etablissement);
		logger.info(etablissement.getSites());
		try {
			etablissementService.create(etablissement);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/etablissement/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String list(Model model, HttpServletRequest request) throws ControllerException {
		logger.info("--list EtablissementController--");

		List<Etablissement> etablissements = null;
		try {
			
			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin){
				etablissements = etablissementService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				etablissements = etablissementService.findByClient(userLogin);
			}
			
			
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("etablissements", etablissements);
		return "/etablissement/list";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ControllerException {
		binder.registerCustomEditor(List.class, "sites", new CustomCollectionEditor(List.class) {
			protected Object convertElement(Object element) {
				if (element instanceof Site) {
					logger.info("Conversion d'un Site en Site: " + element);
					return element;
				}
				if (element instanceof String || element instanceof Integer) {
					Site site;
					if (element instanceof String) {
						site = siteCache.get(Integer.valueOf((String) element));
					} else {

						site = siteCache.get(element);
						logger.info("Recherche du site pour l'Id : " + element + ": " + site);
					}
					return site;
				}
				logger.debug("Problème avec l'élement : " + element);
				return null;
			}
		});
	}

	public Map<Integer, Site> getSiteCache() {
		return siteCache;
	}

	public void setSiteCache(Map<Integer, Site> siteCache) {
		this.siteCache = siteCache;
	}

}
