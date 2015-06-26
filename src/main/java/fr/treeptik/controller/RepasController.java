package fr.treeptik.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Repas;
import fr.treeptik.model.TypeRepas;
import fr.treeptik.service.RepasService;

@Controller
@RequestMapping("/repas")
public class RepasController {

	private Logger logger = Logger.getLogger(RepasController.class);

	@Inject
	private RepasService repasService;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForms(Model model) throws ControllerException {
		logger.info("--create formulaire RepasController--");

		List<TypeRepas> typesRepasCombo = new ArrayList<TypeRepas>(
				Arrays.asList(TypeRepas.values()));
		
		
		model.addAttribute("typesRepasCombo", typesRepasCombo);
		model.addAttribute("repas", new Repas());
		return "/repas/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Repas repas)
			throws ControllerException {
		logger.info("--create RepasController-- repas : " + repas);

		try {
			repasService.update(repas);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/repas/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update RepasController-- repasId : " + id);

		Repas repas = null;

		try {
			repas = repasService.findById(id);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("repas", repas);
		return "/repas/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete RepasController-- repasId : " + id);

		try {
			repasService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/repas/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list RepasController--");

		List<Repas> listRepas = null;
		try {
			listRepas = repasService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("listRepas", listRepas);
		return "/repas/list";
	}
}
