package fr.treeptik.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
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
import fr.treeptik.model.Aliment;
import fr.treeptik.model.Plat;
import fr.treeptik.model.Repas;
import fr.treeptik.model.RepasDate;
import fr.treeptik.service.AlimentService;
import fr.treeptik.service.PlatService;
import fr.treeptik.service.RepasDateService;
import fr.treeptik.service.RepasService;
import fr.treeptik.service.impl.ExportCSV;

@Controller
@RequestMapping("/plat")
public class PlatController {

	private Logger logger = Logger.getLogger(PlatController.class);

	@Inject
	private PlatService platService;
	@Inject
	private RepasService repasService;
	@Inject
	private RepasDateService repasDateService;

	@Inject
	private AlimentService alimentService;

	@Inject
	private ExportCSV exportCSV;

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

	@RequestMapping(method = RequestMethod.POST, value = "/create/isolated")
	public String createIsolated(@ModelAttribute Plat plat,
			BindingResult errors, HttpServletRequest request)
			throws ControllerException {
		logger.info("--createIsolated PlatController--");

		{
			if (errors.hasErrors()) {
				errors.getAllErrors().forEach(
						e -> System.out.println(e.getDefaultMessage()));
			}
		}

		try {
			platService.create(plat);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/plat/list";

	}

	@RequestMapping(value = "/create/{repasId}/{repasDateId}", method = RequestMethod.POST)
	public String create(@ModelAttribute Plat plat,
			@PathVariable("repasId") Long repasId,
			@PathVariable("repasDateId") Long repasDateId)
			throws ControllerException {
		logger.info("--create PlatController-- repasId : " + repasId);
		logger.info("--create PlatController-- repasDateId : " + repasDateId);
		logger.info("--create PlatController-- plat : " + plat);

		try {
			RepasDate repasDate = null;
			Repas repas = null;

			if (repasDateId == 0) {
				repasDate = new RepasDate();
			} else {
				repasDate = repasDateService.findById(repasDateId);
			}

			if (repasId == 0) {
				repas = new Repas();
			} else {
				repas = repasService.findByIdWithListPlat(repasId);
			}
			repasDate.setRepas(repas);
			plat = platService.create(plat);
			List<Plat> listPlats = repas.getListPlats();
			if (listPlats == null) {
				listPlats = new ArrayList<Plat>();
			}

			listPlats.add(plat);
			repas.setListPlats(listPlats);
			if (repasId == 0) {
				repas = repasService.create(repas);
			} else {
				repas = repasService.update(repas);
			}
			repasDate.setRepas(repas);
			if (repasDateId == 0) {
				repasDate = repasDateService.create(repasDate);
			} else {
				repasDate = repasDateService.update(repasDate);
			}
			repasId = repas.getId();
			repasDateId = repasDate.getId();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/repasdate/update/" + repasId + "/" + repasDateId;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Long id)
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
	public String delete(Model model, @PathVariable("id") Long id)
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

	@RequestMapping(value = "/download/report", method = RequestMethod.GET)
	public String downloadCSV(HttpServletResponse response)
			throws ServiceException, IOException {

		String report = exportCSV.exportPlatCSV();

		response.setContentType("text/csv");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String beginDateString = simpleDateFormat.format(new Date());
		String reportName = beginDateString + "-Plat" + ".csv";
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				reportName);
		response.setHeader(headerKey, headerValue);

		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.print(report);
		outputStream.flush();
		outputStream.close();
		return "redirect:/plat/list";
	}

}
