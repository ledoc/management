package fr.treeptik.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.treeptik.dto.PointGraphDTO;
import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Poids;
import fr.treeptik.service.PoidsService;
import fr.treeptik.util.DatePointComparator;

@Controller
@RequestMapping("/poids")
public class PoidsController {

	private Logger logger = Logger.getLogger(PoidsController.class);

	@Inject
	private PoidsService poidsService;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForms(Model model) throws ControllerException {
		logger.info("--create formulaire PoidsController--");

		model.addAttribute("poids", new Poids());
		return "/poids/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Poids poids)
			throws ControllerException {
		logger.info("--create PoidsController-- poids : " + poids);

		try {
			poidsService.update(poids);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/poids/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Long id)
			throws ControllerException {
		logger.info("--update PoidsController-- poidsId : " + id);

		Poids poids = null;

		try {
			poids = poidsService.findById(id);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("poids", poids);
		return "/poids/create";
	}
	
	
	
	
	

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Long id)
			throws ControllerException {
		logger.info("--delete PoidsController-- poidsId : " + id);

		try {
			poidsService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/poids/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list PoidsController--");

		List<Poids> listPoids = null;
		try {
			listPoids = poidsService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("listPoids", listPoids);
		return "/poids/list";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = { "/graph" })
	public String graph(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--graph PoidsController--");

		return "/poids/graph";
	}
	
	/**
	 * On initialise avec la premiere alerte du premier capteur en retirant le
	 * capteur de temperature
	 *
	 * @param request
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/init/graph/points")
	public @ResponseBody List<PointGraphDTO> initPointsGraph(
			HttpServletRequest request) throws ControllerException {

		logger.info("--initPointsGraph PoidsController");

		List<Poids> listPoids = new ArrayList<>();
		List<PointGraphDTO> points = new ArrayList<>();

		try {
			listPoids = poidsService.findAll();

			for (Poids poids : listPoids) {
				points.add(poidsService.transformPoidsInPoint(poids));
			}

			Collections.sort(points, new DatePointComparator());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return points;
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		logger.info("--initBinder PoidsController --");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}
	
}
