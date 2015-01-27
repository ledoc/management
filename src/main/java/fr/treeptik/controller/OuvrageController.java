package fr.treeptik.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
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
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.OuvrageService;

@Controller
public class OuvrageController {
	
	private Logger logger = Logger.getLogger(OuvrageController.class);



	@Inject
	private OuvrageService ouvrageService;
	@Inject
	private EnregistreurService enregistreurService;
	private Map<Integer, Enregistreur> enregistreurCache;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String create(Model model) throws ControllerException {
		logger.info("--create formulaire OuvrageController--");
		
		List<Enregistreur> enregistreursCombo;
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

		model.addAttribute("ouvrage", new Ouvrage());
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		return "/ouvrage/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id) throws ControllerException {
		logger.info("--update OuvrageController--");
		logger.debug("ouvrageId : " + id);

		Ouvrage ouvrage = null;
		List<Enregistreur> enregistreursCombo;
		enregistreurCache = new HashMap<Integer, Enregistreur>();

		try {
			ouvrage = ouvrageService.findByIdWithJoinFetchEnregistreurs(id);

			enregistreursCombo = enregistreurService.findAll();

			for (Enregistreur enregistreur : enregistreursCombo) {
				enregistreurCache.put(enregistreur.getId(), enregistreur);
			}
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("enregistreursCombo", enregistreursCombo);
		model.addAttribute("ouvrage", ouvrage);
		return "/ouvrage/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id) throws ControllerException {
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
	public String create(@ModelAttribute Ouvrage ouvrage) throws ControllerException {
		logger.info("--create OuvrageController--");
		logger.debug("ouvrage : " + ouvrage);
		logger.info(ouvrage.getEnregistreurs());
		try {
			ouvrageService.create(ouvrage);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/ouvrage/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String list(Model model) throws ControllerException {
		logger.info("--list OuvrageController--");

		List<Ouvrage> ouvrages = null;
		try {
			ouvrages = ouvrageService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("ouvrages", ouvrages);
		return "/ouvrage/list";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ControllerException {
		binder.registerCustomEditor(List.class, "enregistreurs", new CustomCollectionEditor(List.class) {
			protected Object convertElement(Object element) {
				if (element instanceof Enregistreur) {
					logger.info("Conversion d'un Enregistreur en Enregistreur: " + element);
					return element;
				}
				if (element instanceof String || element instanceof Integer) {
					Enregistreur enregistreur;
					if (element instanceof String) {
						enregistreur = enregistreurCache.get(Integer.valueOf((String) element));
					} else {

						enregistreur = enregistreurCache.get(element);
						logger.info("Recherche du enregistreur pour l'Id : " + element + ": " + enregistreur);
					}
					return enregistreur;
				}
				logger.debug("Problème avec l'élement : " + element);
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
