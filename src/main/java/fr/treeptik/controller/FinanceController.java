package fr.treeptik.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.treeptik.dto.PointCamenbertDTO;
import fr.treeptik.dto.PointGraphDTO;
import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Bilan;
import fr.treeptik.model.Finance;
import fr.treeptik.model.TypePayment;
import fr.treeptik.service.FinanceService;
import fr.treeptik.util.DatePointComparator;

@Controller
@RequestMapping("/finance")
public class FinanceController {

	private Logger logger = Logger.getLogger(FinanceController.class);

	@Inject
	private FinanceService financeService;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForms(Model model) throws ControllerException {
		logger.info("--create formulaire FinanceController--");

		List<TypePayment> typesPaymentCombo = new ArrayList<TypePayment>(
				Arrays.asList(TypePayment.values()));

		model.addAttribute("finance", new Finance());
		model.addAttribute("typesPaymentCombo", typesPaymentCombo);
		return "/finance/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Finance finance, BindingResult errors,
			HttpServletRequest request) throws ControllerException {

		{
			if (errors.hasErrors()) {
				errors.getAllErrors().forEach(
						e -> System.out.println(e.getDefaultMessage()));
			}
		}
		logger.info("--create FinanceController-- finance : " + finance);

		try {
			Double lastTotal = financeService.selectLastTotal();
			Double newTotal = lastTotal + finance.getMontant();
			finance.setTotal(newTotal);
			financeService.update(finance);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/finance/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update FinanceController-- financeId : " + id);
		Finance finance = null;
		List<TypePayment> typesPaymentCombo = new ArrayList<TypePayment>(
				Arrays.asList(TypePayment.values()));

		try {
			finance = financeService.findById(id);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("typesPaymentCombo", typesPaymentCombo);
		model.addAttribute("finance", finance);
		return "/finance/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete FinanceController-- financeId : " + id);

		try {
			financeService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/finance/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list FinanceController--");

		List<Finance> finances = null;
		Double sumOfMonth = 0.0D;
		Double averageOfMonth = 0.0D;
		Double sumOfBeforeMonth = 0.0D;
		Double averageOfBeforeMonth = 0.0D;

		try {

			sumOfBeforeMonth = financeService.countBeforeMonthSum();
			averageOfBeforeMonth = financeService.countBeforeMonthAverage();
			sumOfMonth = financeService.countMonthSum();
			averageOfMonth = financeService.countMonthAverage();
			finances = financeService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("averageOfMonth", averageOfMonth);
		model.addAttribute("averageOfBeforeMonth", averageOfBeforeMonth);
		model.addAttribute("sumOfMonth", sumOfMonth);
		model.addAttribute("sumOfBeforeMonth", sumOfBeforeMonth);
		model.addAttribute("finances", finances);
		return "/finance/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/graph" })
	public String graph(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--graph FinanceController--");

		return "/finance/graph";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/bilan" })
	public String bilan(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list FinanceController--");

		List<Bilan> bilans = new ArrayList<>();

		try {

			bilans = financeService.listAllBilans();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("bilans", bilans);
		return "/finance/bilan";
	}

	/**
	 * On initialise avec la premiere alerte du premier capteur en retirant le
	 * capteur de temperature
	 *
	 * @param request
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/init/camenbert/points")
	public @ResponseBody List<PointCamenbertDTO> initPointsCamenbert(
			HttpServletRequest request) throws ControllerException {

		logger.info("--initPointsCamenbert MesureController");

		List<Bilan> bilans = new ArrayList<>();
		List<PointCamenbertDTO> points = new ArrayList<>();

		try {
			


				bilans = financeService.listAllBilans();
				for (Bilan bilan : bilans) {
					points.add(financeService.transformBilanInCamenbertPoint(bilan));
				}	
			
			System.out.println(points);
				
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return points;
	}

	
	/**
	 *
	 * @param request
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/init/graph/points")
	public @ResponseBody List<PointGraphDTO> initPointsGraph(
			HttpServletRequest request) throws ControllerException {

		logger.info("--initPointsGraph MesureController");

		List<PointGraphDTO> points = new ArrayList<>();

		try {
			
			
			List<Finance> allFinances = financeService.findAll();

			for (Finance finance : allFinances) {
				points.add(financeService.transformFinanceInPoint(finance));
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
		logger.info("--initBinder MesureController --");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		System.out.println("initBinder");

		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}
}
