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
import fr.treeptik.service.ExerciceService;

@Controller
@RequestMapping("/exercice")
public class ExerciceController {

	private Logger logger = Logger.getLogger(ExerciceController.class);

	@Inject
	private ExerciceService exerciceService;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForms(Model model) throws ControllerException {
		logger.info("--create formulaire ExerciceController--");

		
		model.addAttribute("exercice", new Exercice());
		return "/exercice/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Exercice exercice)
			throws ControllerException {
		logger.info("--create ExerciceController-- exercice : " + exercice);

		try {
			exerciceService.update(exercice);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/exercice/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update ExerciceController-- exerciceId : " + id);

		Exercice exercice = null;

		try {
			exercice = exerciceService.findById(id);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("exercice", exercice);
		return "/exercice/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete ExerciceController-- exerciceId : " + id);

		try {
			exerciceService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/exercice/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list ExerciceController--");

		List<Exercice> listExercice = null;
		try {
			listExercice = exerciceService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("listExercice", listExercice);
		return "/exercice/list";
	}
}
