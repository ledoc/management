package fr.treeptik.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
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
import fr.treeptik.model.Client;
import fr.treeptik.model.Etablissement;
import fr.treeptik.service.ClientService;
import fr.treeptik.service.EtablissementService;
import fr.treeptik.spring.ClientCustomEditor;

@Controller
@RequestMapping("/etablissement")
public class EtablissementController {

	private Logger logger = Logger.getLogger(EtablissementController.class);

	@Inject
	private EtablissementService etablissementService;
	@Inject
	private ClientService clientService;

	@Inject
	private ClientCustomEditor clientCustomEditor;

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForms(Model model) throws ControllerException {
		logger.info("--create formulaire EtablissementController--");

		List<Client> clientsCombo;

		try {
			clientsCombo = clientService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("etablissement", new Etablissement());
		model.addAttribute("clientsCombo", clientsCombo);
		return "/etablissement/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Etablissement etablissement)
			throws ControllerException {
		logger.info("--create EtablissementController-- etablissement : "
				+ etablissement + " -- sites : " + etablissement.getSites()
				+ " -- clients : " + etablissement.getClients());

		try {
			etablissementService.update(etablissement);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return "redirect:/etablissement/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update EtablissementController-- etablissementId : "
				+ id);

		Etablissement etablissement = null;
		List<Client> clientsCombo;

		try {
			clientsCombo = clientService.findAll();
			etablissement = etablissementService
					.findByIdWithJoinFetchClients(id);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("etablissement", etablissement);
		model.addAttribute("clientsCombo", clientsCombo);
		return "/etablissement/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete EtablissementController-- etablissementId : "
				+ id);

		try {
			etablissementService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/etablissement/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list EtablissementController--");

		List<Etablissement> etablissements = null;
		try {

			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				etablissements = etablissementService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				etablissements = etablissementService
						.findByClientLogin(userLogin);
			}

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("etablissements", etablissements);
		return "/etablissement/list";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ControllerException {
		binder.registerCustomEditor(Client.class, clientCustomEditor);
	}

}
