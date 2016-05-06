package fr.treeptik.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import fr.treeptik.service.AlimentService;
import fr.treeptik.service.impl.ExportCSV;

@Controller
@RequestMapping("/aliment")
public class AlimentController {

	private Logger logger = Logger.getLogger(AlimentController.class);

	@Inject
	private AlimentService alimentService;
	
	@Inject
	private ExportCSV exportCSV;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForms(Model model) throws ControllerException {
		logger.info("--create formulaire AlimentController--");

		model.addAttribute("aliment", new Aliment());
		return "/aliment/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Aliment aliment)
			throws ControllerException {
		logger.info("--create AlimentController-- aliment : " + aliment);

		try {
			alimentService.create(aliment);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/aliment/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Long id)
			throws ControllerException {
		logger.info("--update AlimentController-- alimentId : " + id);

		Aliment aliment = null;

		try {
			aliment = alimentService.findById(id);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("aliment", aliment);
		return "/aliment/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Long id)
			throws ControllerException {
		logger.info("--delete AlimentController-- alimentId : " + id);

		try {
			alimentService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/aliment/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list AlimentController--");

		List<Aliment> aliments = null;
		try {
			aliments = alimentService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("aliments", aliments);
		return "/aliment/list";
	}

	@RequestMapping(value = "/download/report", method = RequestMethod.GET)
	public String downloadCSV(HttpServletResponse response)
			throws ServiceException, IOException {

		String report = exportCSV.exportAlimentCSV();

		response.setContentType("text/csv");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String beginDateString = simpleDateFormat.format(new Date());
		String reportName = beginDateString +"-Aliment" + ".csv";
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				reportName);
		response.setHeader(headerKey, headerValue);

		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.print(report);
		outputStream.flush();
		outputStream.close();
		return "redirect:/aliment/list";
	}

}
