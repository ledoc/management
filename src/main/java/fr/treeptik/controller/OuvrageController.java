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

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;
import fr.treeptik.model.TypeMesure;
import fr.treeptik.model.TypeOuvrage;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.OuvrageService;
import fr.treeptik.service.SiteService;

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
	private Map<Integer, Enregistreur> enregistreurCache;
	private Map<Integer, Site> siteCache;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String create(Model model) throws ControllerException {
		logger.info("--create formulaire OuvrageController--");

		Ouvrage ouvrage = new Ouvrage();
		List<Enregistreur> enregistreursCombo;
		List<Site> sitesCombo;
		List<TypeOuvrage> typesOuvrageCombo = new ArrayList<TypeOuvrage>(
				Arrays.asList(TypeOuvrage.values()));
		try {
			sitesCombo = siteService.findAll();
			enregistreursCombo = enregistreurService.findFreeEnregistreurs();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		enregistreurCache = new HashMap<Integer, Enregistreur>();
		for (Enregistreur enregistreur : enregistreursCombo) {
			enregistreurCache.put(enregistreur.getId(), enregistreur);
		}

		siteCache = new HashMap<Integer, Site>();
		for (Site site : sitesCombo) {
			siteCache.put(site.getId(), site);
		}

		model.addAttribute("ouvrage", ouvrage);
		model.addAttribute("sitesCombo", sitesCombo);
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("typesOuvrageCombo", typesOuvrageCombo);
		return "/ouvrage/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update OuvrageController--");
		logger.debug("ouvrageId : " + id);
		Ouvrage ouvrage = null;

		List<Enregistreur> enregistreursCombo;
		List<Site> sitesCombo;
		List<TypeOuvrage> typesOuvrageCombo = new ArrayList<TypeOuvrage>(
				Arrays.asList(TypeOuvrage.values()));

		enregistreurCache = new HashMap<Integer, Enregistreur>();
		siteCache = new HashMap<Integer, Site>();

		try {
			ouvrage = ouvrageService.findByIdWithJoinFetchEnregistreurs(id);

			enregistreursCombo = enregistreurService.findFreeEnregistreurs();
			enregistreursCombo.addAll(ouvrage.getEnregistreurs());
			sitesCombo = siteService.findAll();

			for (Enregistreur enregistreur : enregistreursCombo) {
				enregistreurCache.put(enregistreur.getId(), enregistreur);
			}

			for (Site site : sitesCombo) {
				siteCache.put(site.getId(), site);
			}

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("typesOuvrageCombo", typesOuvrageCombo);
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("ouvrage", ouvrage);
		return "/ouvrage/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete OuvrageController--");
		logger.debug("ouvrageId : " + id);

		try {
			ouvrageService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/ouvrage/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Ouvrage ouvrage, BindingResult result)
			throws ControllerException {
		logger.info(result.getAllErrors());

		logger.info("--create OuvrageController--");
		logger.debug("ouvrage : " + ouvrage);
		logger.info("enregistreurs de l'ouvrage : "
				+ ouvrage.getEnregistreurs());
//		logger.info("niveau manuel : " + ouvrage.getNiveauManuel());
		try {
//			if (ouvrage.getNiveauManuel() != null) {
//				mesureService.findById(ouvrage.getNiveauManuel().getId());
//			}
			ouvrageService.create(ouvrage);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/ouvrage/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
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

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ControllerException {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"), true));

		binder.registerCustomEditor(List.class, "enregistreurs",
				new CustomCollectionEditor(List.class) {
					protected Object convertElement(Object element) {
						if (element instanceof Enregistreur) {
							logger.info("Conversion d'un Enregistreur en Enregistreur: "
									+ element);
							return element;
						}
						if (element instanceof String
								|| element instanceof Integer) {
							Enregistreur enregistreur;
							if (element instanceof String) {
								enregistreur = enregistreurCache.get(Integer
										.valueOf((String) element));
							} else {

								enregistreur = enregistreurCache.get(element);
								logger.info("Recherche de l'enregistreur pour l'Id : "
										+ element + ": " + enregistreur);
							}
							return enregistreur;
						}
						logger.debug("Problème avec l'élement : " + element);
						return null;
					}
				});

		binder.registerCustomEditor(List.class, "sitesCombo",
				new CustomCollectionEditor(List.class) {
					protected Object convertElement(Object element) {
						if (element instanceof Site) {
							logger.info("Conversion d'un Site en Site: "
									+ element);
							return element;
						}
						if (element instanceof String
								|| element instanceof Integer) {
							Site site;
							if (element instanceof String) {
								site = siteCache.get(Integer
										.valueOf((String) element));
							} else {

								site = siteCache.get(element);
								logger.info("Recherche du site pour l'Id : "
										+ element + ": " + site);
							}
							return site;
						}
						logger.debug("Problème avec l'élement : " + element);
						return null;
					}
				});
	}

	public Map<Integer, Enregistreur> getEnregistreurCache() {
		return enregistreurCache;
	}

	public void setEnregistreurCache(
			Map<Integer, Enregistreur> enregistreurCache) {
		this.enregistreurCache = enregistreurCache;
	}
}
