package fr.treeptik.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Aliment;
import fr.treeptik.model.BilanRepas;
import fr.treeptik.model.Plat;
import fr.treeptik.model.Repas;
import fr.treeptik.model.RepasDate;
import fr.treeptik.service.AlimentService;
import fr.treeptik.service.PlatService;
import fr.treeptik.service.RepasDateService;
import fr.treeptik.service.RepasService;
import fr.treeptik.service.impl.ExportCSV;
import fr.treeptik.util.DateBilanRepasComparator;

@Controller
@RequestMapping("/repasdate")
public class RepasDateController {

	private Logger logger = Logger.getLogger(RepasDateController.class);

	@Inject
	private RepasDateService repasDateService;
	@Inject
	private RepasService repasService;
	@Inject
	private PlatService platService;
	@Inject
	private AlimentService alimentService;
	@Inject
	private ExportCSV exportCSV;

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "date", new CustomDateEditor(
				dateFormat, false));

		binder.registerCustomEditor(List.class, "repas.listPlats",
				new CustomCollectionEditor(List.class) {
					protected Object convertElement(Object element) {
						if (element instanceof Plat) {
							return element;
						}
						if (element instanceof String) {
							try {
								return platService.findById(Long
										.valueOf(element.toString()));
							} catch (NumberFormatException | ServiceException e) {
								e.printStackTrace();
							}
						}
						return null;
					}
				});
	}

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForms(Model model) throws ControllerException {
		logger.info("--create formulaire RepasController--");

		try {
			List<Aliment> listAlimentCombo = null;
			List<Plat> listPlatCombo = null;
			RepasDate repasDate = new RepasDate();
			repasDate.setRepas(new Repas());
			try {
				listPlatCombo = platService.findAll();
				listAlimentCombo = alimentService.findAll();
			} catch (ServiceException e) {
				e.printStackTrace();
			}

			model.addAttribute("listPlatCombo", listPlatCombo);
			model.addAttribute("listAlimentCombo", listAlimentCombo);
			model.addAttribute("plat", new Plat());
			List<Repas> listRepasCombo = repasService.findAllWithListPlat();
			model.addAttribute("listRepasCombo", listRepasCombo);
			model.addAttribute("repasDate", repasDate);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/repasdate/create";
	}

	@RequestMapping(params="returnToList", value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute RepasDate repasDate,
			BindingResult bindingResult) throws ControllerException {
		logger.info("--create RepasDateController-- repasDate : " + repasDate);

		if (bindingResult.hasErrors()) {
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				logger.info(objectError.getDefaultMessage());
			}
		}

		try {

			Long copyRepasId = repasDate.getCopyRepasId();
			if (copyRepasId != null && copyRepasId != 0L) {
				Repas copyRepas = repasService
						.findByIdWithListPlat(copyRepasId);
				Date date = repasDate.getDate();
				repasDate = new RepasDate(copyRepas);
				repasDate.setDate(date);
			}

			Repas repas = repasDate.getRepas();
			if (repas != null) {
				List<Plat> listPlats = repas.getListPlats();

				if (listPlats != null && !listPlats.isEmpty()) {
					Boolean checkRepasExist = repasService
							.checkRepasExist(listPlats);

					if (!checkRepasExist) {
						repas = repasService.create(repas);
					} else {
						repas = repasService.update(repas);
					}
				}
				repasDate.setRepas(repas);
			}

			repasDateService.create(repasDate);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/repasdate/list";

	}

	@RequestMapping(params="createAnother" , value = "/create", method = RequestMethod.POST)
	public String createAndResetForAnother(@ModelAttribute RepasDate repasDate,
			BindingResult bindingResult) throws ControllerException {
		logger.info("--createAndResetForAnother RepasDateController-- repasDate : " + repasDate);

		if (bindingResult.hasErrors()) {
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				logger.info(objectError.getDefaultMessage());
			}
		}

		try {

			Long copyRepasId = repasDate.getCopyRepasId();
			if (copyRepasId != null && copyRepasId != 0L) {
				Repas copyRepas = repasService
						.findByIdWithListPlat(copyRepasId);
				Date date = repasDate.getDate();
				repasDate = new RepasDate(copyRepas);
				repasDate.setDate(date);
			}

			Repas repas = repasDate.getRepas();
			if (repas != null) {
				List<Plat> listPlats = repas.getListPlats();

				if (listPlats != null && !listPlats.isEmpty()) {
					Boolean checkRepasExist = repasService
							.checkRepasExist(listPlats);

					if (!checkRepasExist) {
						repas = repasService.create(repas);
					} else {
						repas = repasService.update(repas);
					}
				}
				repasDate.setRepas(repas);
			}

			repasDateService.create(repasDate);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/repasdate/create";

	}
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/update/{repasId}/{repasDateId}")
	public String doUpdate(Model model, @PathVariable("repasId") Long repasId,
			@PathVariable("repasDateId") Long repasDateId)
			throws ControllerException {
		logger.info("--doUpdate RepasDateController-- repasId : " + repasId);
		logger.info("--doUpdate RepasDateController-- repasDateId : "
				+ repasDateId);

		RepasDate repasDate = null;
		List<Plat> listPlatCombo = null;
		List<Aliment> listAlimentCombo = null;
		List<Repas> listRepasCombo = null;
		try {

			listAlimentCombo = alimentService.findAll();
			listPlatCombo = platService.findAll();
			listRepasCombo = repasService.findAllWithListPlat();

			repasDate = repasDateService.findByIdWithListPlat(repasDateId);

			model.addAttribute("repasDate", repasDate);
			model.addAttribute("plat", new Plat());
			model.addAttribute("listPlatCombo", listPlatCombo);
			model.addAttribute("listAlimentCombo", listAlimentCombo);
			model.addAttribute("listRepasCombo", listRepasCombo);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "/repasdate/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Long id)
			throws ControllerException {
		logger.info("--delete RepasDateController-- repasId : " + id);

		try {
			repasDateService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/repasdate/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list RepasController--");

		List<RepasDate> listRepasDate = null;
		try {
			listRepasDate = repasDateService.findAllWithListPlat();
			for (RepasDate repasDate : listRepasDate) {
				logger.info(repasDate);

			}

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("listRepasDate", listRepasDate);
		return "/repasdate/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/graph" })
	public String graph(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--graph RepasController--");

		return "/repasdate/graph";
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/init/graph/points")
	public @ResponseBody List<BilanRepas> initPointsGraphRepas(
			HttpServletRequest request) throws ControllerException {

		logger.info("--initPointsGraph RepasDateController");

		List<BilanRepas> listAllBilanRepas = new ArrayList<BilanRepas>();
		try {
			listAllBilanRepas = repasDateService.createBilanByDate();

			Collections.sort(listAllBilanRepas, new DateBilanRepasComparator());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return listAllBilanRepas;
	}

	@RequestMapping(value = "/download/report", method = RequestMethod.GET)
	public String downloadCSV(HttpServletResponse response)
			throws ServiceException, IOException {
		logger.info("--downloadCSV RepasDateController");

		String report = exportCSV.exportRepasCSV();

		response.setContentType("text/csv");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String beginDateString = simpleDateFormat.format(new Date());
		String reportName = beginDateString + "-Repas" + ".csv";
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				reportName);
		response.setHeader(headerKey, headerValue);

		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.print(report);
		outputStream.flush();
		outputStream.close();
		return "redirect:/repasdate/list";
	}

}
