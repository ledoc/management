package fr.treeptik.controller;

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
import fr.treeptik.model.Aliment;
import fr.treeptik.model.Plat;
import fr.treeptik.service.AlimentService;
import fr.treeptik.service.PlatService;

@Controller
@RequestMapping("/plat")
public class PlatController {

	private Logger logger = Logger.getLogger(PlatController.class);

	@Inject
	private PlatService platService;
	
	@Inject
	private AlimentService alimentService;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForms(Model model) throws ControllerException {
		logger.info("--create formulaire PlatController--");

		List<Aliment> alimentCombo = null;
		try {
			alimentCombo = alimentService.findAll();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("alimentCombo", alimentCombo);
		model.addAttribute("plat", new Plat());
		return "/plat/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Plat plat)
			throws ControllerException {
		logger.info("--create PlatController-- plat : " + plat);

		try {
			platService.update(plat);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/plat/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update PlatController-- platId : " + id);

		Plat plat = null;

		try {
			plat = platService.findById(id);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("plat", plat);
		return "/plat/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete PlatController-- platId : " + id);

		try {
			platService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/plat/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list PlatController--");

		List<Plat> plats = null;
		try {
			plats = platService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("listPlat", plats);
		return "/plat/list";
	}
}
