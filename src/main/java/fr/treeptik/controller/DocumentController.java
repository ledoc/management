package fr.treeptik.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Alerte;
import fr.treeptik.model.Client;
import fr.treeptik.model.Document;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.service.ClientService;
import fr.treeptik.service.DocumentService;
import fr.treeptik.service.OuvrageService;

@Controller
@RequestMapping("/document")
public class DocumentController {

	private Logger logger = Logger.getLogger(DocumentController.class);

	@Inject
	private DocumentService documentService;
	@Inject
	private OuvrageService ouvrageService;
	@Inject
	private ClientService clientService;
	private Map<Integer, Client> clientsCache;
	private Map<Integer, Ouvrage> ouvragesCache;

	@RequestMapping(method = RequestMethod.GET, value = "/assign")
	public String upload(Model model) throws ControllerException {
		List<Ouvrage> ouvragesCombo;
		List<Client> clientsCombo;
		try {
			ouvragesCombo = ouvrageService.findAll();
			clientsCombo = clientService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		clientsCache = new HashMap<Integer, Client>();
		for (Client client : clientsCombo) {
			clientsCache.put(client.getId(), client);
		}

		ouvragesCache = new HashMap<Integer, Ouvrage>();
		for (Ouvrage ouvrage : ouvragesCombo) {
			ouvragesCache.put(ouvrage.getId(), ouvrage);
		}

		model.addAttribute("clientsCombo", clientsCombo);
		model.addAttribute("ouvragesCombo", ouvragesCombo);
		model.addAttribute("document", new Document());
		return "/document/assign";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list DocumentController--");

		List<Document> documents = null;
		try {

			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				documents = documentService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				documents = documentService.findByClientLogin(userLogin);
			}
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("documents", documents);
		return "/document/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update DocumentController -- documentId : " + id);

		Document document = new Document();
		List<Ouvrage> ouvragesCombo;
		List<Client> clientsCombo;
		try {
			document = documentService.findById(id);
			ouvragesCombo = ouvrageService.findAll();
			clientsCombo = clientService.findAll();

			clientsCache = new HashMap<Integer, Client>();
			for (Client client : clientsCombo) {
				clientsCache.put(client.getId(), client);
			}

			ouvragesCache = new HashMap<Integer, Ouvrage>();
			for (Ouvrage ouvrage : ouvragesCombo) {
				ouvragesCache.put(ouvrage.getId(), ouvrage);
			}
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("clientsCombo", clientsCombo);
		model.addAttribute("ouvragesCombo", ouvragesCombo);
		model.addAttribute("document", document);

		return "/document/assign";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete DocumentController-- documentId : " + id);

		try {
			documentService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/document/list";
	}

	@RequestMapping(value = "/upload/file/{clientId}/{ouvrageId}", method = RequestMethod.POST)
	public String uploadFileHandler(@PathVariable("clientId") Integer clientId,
			@PathVariable("ouvrageId") Integer ouvrageId, MultipartFile file)
			throws ControllerException {

		try {
			documentService.uploadFileAndAssign(file, clientId, ouvrageId);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/document/list";
	}

	@RequestMapping(value = "/download/file/{documentId}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable("documentId") Integer documentId,
			HttpServletRequest request, HttpServletResponse response)
			throws ControllerException {

		logger.info("--downloadFile DocumentController-- documentId : "
				+ documentId);
		Document document = new Document();
		try {
			document = documentService.findById(documentId);
			documentService.downloadFile(document, request, response);
		} catch (ServiceException e) {
			logger.error("Error DocumentController  -- downloadFile : "
					+ document.getNom());
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ControllerException {

		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("dd-MM-yyyy"), true));

		binder.registerCustomEditor(List.class, "clients",
				new CustomCollectionEditor(List.class) {
					protected Object convertElement(Object element) {
						if (element instanceof Alerte) {
							logger.info("Conversion d'un client en client: "
									+ element);
							return element;
						}
						if (element instanceof String
								|| element instanceof Integer) {
							Client client;
							if (element instanceof String) {
								client = clientsCache.get(Integer
										.valueOf((String) element));
							} else {

								client = clientsCache.get(element);
								logger.info("Recherche du client pour l'Id : "
										+ element + ": " + client);
							}
							return client;
						}
						logger.debug("Problème avec l'élement : " + element);
						return null;
					}
				});

		binder.registerCustomEditor(List.class, "ouvrages",
				new CustomCollectionEditor(List.class) {
					protected Object convertElement(Object element) {
						if (element instanceof Alerte) {
							logger.info("Conversion d'un ouvrage en ouvrage: "
									+ element);
							return element;
						}
						if (element instanceof String
								|| element instanceof Integer) {
							Ouvrage ouvrage;
							if (element instanceof String) {
								ouvrage = ouvragesCache.get(Integer
										.valueOf((String) element));
							} else {

								ouvrage = ouvragesCache.get(element);
								logger.info("Recherche du ouvrage pour l'Id : "
										+ element + ": " + ouvrage);
							}
							return ouvrage;
						}
						logger.debug("Problème avec l'élement : " + element);
						return null;
					}
				});

	}

	@RequestMapping(method = RequestMethod.GET, value = "/refresh/ouvrage/{clientId}")
	public @ResponseBody List<Ouvrage> refreshOuvrage(
			@PathVariable("clientId") Integer clientId)
			throws ControllerException {
		System.out.println("--refreshOuvrage DocumentController-- clientId : "
				+ clientId);

		List<Ouvrage> ouvragesOfClient;
		try {
			ouvragesOfClient = ouvrageService.findByClientId(clientId);
			System.out.println("liste d'ouvrages renvoyée : "
					+ ouvragesOfClient);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return ouvragesOfClient;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/refresh/client/{ouvrageId}")
	public @ResponseBody List<Client> findClientByOuvrageId(
			@PathVariable("ouvrageId") Integer ouvrageId)
			throws ControllerException {

		System.out
				.println("--findClientByOuvrageId DocumentController-- ouvrageId : "
						+ ouvrageId);

		List<Client> listClientTemp = new ArrayList<Client>();
		Client clientOfOuvrage;
		try {
			clientOfOuvrage = clientService.findClientByOuvrageId(ouvrageId);
			listClientTemp.add(clientOfOuvrage);
			System.out.println("liste de client renvoyée : " + clientOfOuvrage);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return listClientTemp;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/init/ouvrage")
	public @ResponseBody List<Ouvrage> initOuvrageCombobox()
			throws ControllerException {
		System.out.println("--initOuvrageCombobox DocumentController--");

		List<Ouvrage> allOuvrages;
		try {
			allOuvrages = ouvrageService.findAll();
			System.out.println("liste d'ouvrages renvoyée : " + allOuvrages);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return allOuvrages;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/init/client")
	public @ResponseBody List<Client> initClientCombobox()
			throws ControllerException {

		System.out.println("--initClientCombobox DocumentController");

		List<Client> allClients = new ArrayList<Client>();
		try {
			allClients = clientService.findAll();
			System.out.println("liste de client renvoyée : " + allClients);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return allClients;

	}

}
