package fr.treeptik.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Administrateur;
import fr.treeptik.service.AdministrateurService;

@Controller
@RequestMapping("/administrateur")
public class AdministrateurController {

	private Logger logger = Logger.getLogger(AdministrateurController.class);

	@Inject
	private AdministrateurService administrateurService;

	@Inject
	private AdministrateurValidator validator;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForm(Model model) {
		logger.info("--initForm AdministrateurController--");

		model.addAttribute("administrateur", new Administrateur());
		return "/administrateur/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Administrateur administrateur,
			Model model, BindingResult result) throws ControllerException {
		logger.info("--create AdministrateurController-- administrateur : "
				+ administrateur);
		try {

			validator.validate(administrateur, result);
			if (result.hasErrors()) {
				model.addAttribute("administrateur", administrateur);
				return "/administrateur/create";
			}

			administrateurService.create(administrateur);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/administrateur/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update AdministrateurController-- administrateurId : " + id);

		Administrateur administrateur = null;
		try {
			administrateur = administrateurService.findById(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("administrateur", administrateur);
		return "/administrateur/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete AdministrateurController-- administrateurId : " + id);

		try {
			administrateurService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/administrateur/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String list(Model model) throws ControllerException {
		logger.info("--list AdministrateurController--");

		List<Administrateur> administrateurs = null;
		try {
			administrateurs = administrateurService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("administrateurs", administrateurs);
		return "/administrateur/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/audit-log")
	public String getAuditLog(HttpServletResponse response, Model model)
			throws ControllerException {
		logger.info("-- get audit log --");

		try {

			model.addAttribute("logs", administrateurService.getAuditLog());

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "/administrateur/log";
	}
}
