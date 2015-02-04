package fr.treeptik.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;
import fr.treeptik.model.TypeSite;
import fr.treeptik.service.OuvrageService;
import fr.treeptik.service.SiteService;

@Controller
@RequestMapping("/site")
public class SiteController {

	private Logger logger = Logger.getLogger(SiteController.class);

	@Inject
	private OuvrageService ouvrageService;
	@Inject
	private SiteService siteService;
	private Map<Integer, Ouvrage> ouvrageCache;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String create(Model model) throws ControllerException {
		logger.info("--create formulaire SiteController--");

		List<Ouvrage> ouvragesCombo;
		List<TypeSite> typesSiteCombo = new ArrayList<TypeSite>(
				Arrays.asList(TypeSite.values()));
		try {

			ouvragesCombo = ouvrageService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		ouvrageCache = new HashMap<Integer, Ouvrage>();
		for (Ouvrage ouvrage : ouvragesCombo) {
			ouvrageCache.put(ouvrage.getId(), ouvrage);
		}
		model.addAttribute("typesSiteCombo", typesSiteCombo);
		model.addAttribute("site", new Site());
		model.addAttribute("ouvragesCombo", ouvragesCombo);
		return "/site/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update SiteController--");
		logger.debug("siteId : " + id);

		Site site = null;
		List<Ouvrage> ouvragesCombo;
		ouvrageCache = new HashMap<Integer, Ouvrage>();
		List<TypeSite> typesSiteCombo = new ArrayList<TypeSite>(
				Arrays.asList(TypeSite.values()));

		try {
			site = siteService.findByIdWithJoinFetchOuvrages(id);

			// ne renvoie que les sites libres et ceux déjà rattachés à
			// l'établissement
			ouvragesCombo = ouvrageService.findFreeOuvrages();
			ouvragesCombo.addAll(site.getOuvrages());

			for (Ouvrage ouvrage : ouvragesCombo) {
				ouvrageCache.put(ouvrage.getId(), ouvrage);
			}
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("typesSiteCombo", typesSiteCombo);
		model.addAttribute("ouvragesCombo", ouvragesCombo);
		model.addAttribute("site", site);
		return "/site/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete SiteController--");
		logger.debug("siteId : " + id);

		try {
			siteService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/site/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Site site, BindingResult result)
			throws ControllerException {
		logger.info("Erreurs : " + result.getAllErrors());

		logger.info("--create SiteController-- site : " + site);
		logger.info(" ouvrages : " + site.getOuvrages());

		List<Ouvrage> ouvrages = site.getOuvrages();
		try {
			// pour une update
			if (site.getId() != null) {
				logger.debug(" update d'un site : id = " + site.getId());
				if (ouvrages != null) {
					logger.debug("ouvrages actuelles : " + ouvrages);
					Site siteInBase = siteService
							.findByIdWithJoinFetchOuvrages(site.getId());
					logger.debug("ouvrages avant update : "
							+ siteInBase.getOuvrages());
					if (!ouvrages.equals(siteInBase.getOuvrages())) {
						logger.debug("La liste d'ouvrage a changée ");
						for (Ouvrage ouvrage : ouvrages) {
							logger.debug("ouvrage mis à jour : " + ouvrage);
							ouvrage = ouvrageService.findById(ouvrage.getId());
							ouvrage.setSite(site);
							ouvrageService.update(ouvrage);
						}
					}
					siteService.update(site);
				} else {
					site = siteService.create(site);
				}
				// pour la création d'un site
			} else {
				if (ouvrages != null) {
					site = siteService.create(site);
					for (Ouvrage ouvrage : ouvrages) {
						ouvrage = ouvrageService.findById(ouvrage.getId());
						ouvrage.setSite(site);
						ouvrageService.update(ouvrage);
					}
				} else {
					site = siteService.create(site);
				}
			}

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/site/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
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
		binder.registerCustomEditor(List.class, "ouvrages",
				new CustomCollectionEditor(List.class) {
					protected Object convertElement(Object element) {
						if (element instanceof Ouvrage) {
							logger.info("Conversion d'un ouvrage en ouvrage : "
									+ element);
							return element;
						}
						if (element instanceof String
								|| element instanceof Integer) {
							Ouvrage ouvrage;
							if (element instanceof String) {
								ouvrage = ouvrageCache.get(Integer
										.valueOf((String) element));
							} else {

								ouvrage = ouvrageCache.get(element);
								logger.info("Recherche du ouvrage pour l'Id : "
										+ element + ": " + ouvrage);
							}
							return ouvrage;
						}
						logger.debug("Problème avec l'élement : " + element);
						return null;
					}
				});
	}

	public Map<Integer, Ouvrage> getOuvrageCache() {
		return ouvrageCache;
	}

	public void setOuvrageCache(Map<Integer, Ouvrage> ouvrageCache) {
		this.ouvrageCache = ouvrageCache;
	}

}
