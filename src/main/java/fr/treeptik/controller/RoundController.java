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
import fr.treeptik.model.Exercice;
import fr.treeptik.model.Round;
import fr.treeptik.service.ExerciceService;
import fr.treeptik.service.RoundService;

@Controller
@RequestMapping("/round")
public class RoundController {

	private Logger logger = Logger.getLogger(RoundController.class);

	@Inject
	private RoundService roundService;
	
	@Inject
	private ExerciceService exerciceService;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForms(Model model) throws ControllerException {
		logger.info("--create formulaire RoundController--");
		try {
			List<Exercice> exercices = exerciceService.findAll();
		model.addAttribute("exerciceCombo", exercices);
		model.addAttribute("round", new Round());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/round/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Round round)
			throws ControllerException {
		logger.info("--create RoundController-- round : " + round);

		try {
			roundService.update(round);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/round/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Long id)
			throws ControllerException {
		logger.info("--update RoundController-- roundId : " + id);

		Round round = null;

		try {
			round = roundService.findById(id);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("round", round);
		return "/round/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Long id)
			throws ControllerException {
		logger.info("--delete RoundController-- roundId : " + id);

		try {
			roundService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/round/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list RoundController--");

		List<Round> listRound = null;
		try {
			listRound = roundService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("listRound", listRound);
		return "/round/list";
	}
}
