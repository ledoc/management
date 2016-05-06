package fr.treeptik.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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
import fr.treeptik.model.Plat;
import fr.treeptik.model.Round;
import fr.treeptik.model.Seance;
import fr.treeptik.service.PlatService;
import fr.treeptik.service.RoundService;
import fr.treeptik.service.SeanceService;

@Controller
@RequestMapping("/seance")
public class SeanceController {

	private Logger logger = Logger.getLogger(SeanceController.class);

	@Inject
	private SeanceService seanceService;
	
	@Inject
	private RoundService roundService;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForms(Model model) throws ControllerException {
		logger.info("--create formulaire SeanceController--");

		List<Round> roundCombo = null;
		try {
			roundCombo = roundService.findAll();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("roundCombo", roundCombo);
		model.addAttribute("seance", new Seance());
		return "/seance/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Seance seance)
			throws ControllerException {
		logger.info("--create SeanceController-- seance : " + seance);

		try {
			seanceService.update(seance);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/seance/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Long id)
			throws ControllerException {
		logger.info("--update SeanceController-- seanceId : " + id);

		Seance seance = null;

		try {
			seance = seanceService.findById(id);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("seance", seance);
		return "/seance/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Long id)
			throws ControllerException {
		logger.info("--delete SeanceController-- seanceId : " + id);

		try {
			seanceService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/seance/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list SeanceController--");

		List<Seance> seances = null;
		try {
			seances = seanceService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("seances", seances);
		return "/seance/list";
	}
}
