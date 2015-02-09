package fr.treeptik.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import fr.treeptik.spring.ClientValidator;

@Controller
@RequestMapping("/client")
public class ClientController {

	private Logger logger = Logger.getLogger(ClientController.class);

	@Inject
	private ClientService clientService;
	@Inject
	private EtablissementService etablissementService;

	@Inject
	private ClientValidator clientValidator;

	private Map<Integer, Etablissement> etablissementCache;

	/**
	 * 
	 * Init Create en GET
	 * 
	 * @param model
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String initForm(Model model) throws ControllerException {

		List<Etablissement> etablissementsCombo;
		try {
			etablissementsCombo = etablissementService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		etablissementCache = new HashMap<Integer, Etablissement>();
		for (Etablissement etablissement : etablissementsCombo) {
			etablissementCache.put(etablissement.getId(), etablissement);
		}

		model.addAttribute("client", new Client());
		model.addAttribute("etablissementsCombo", etablissementsCombo);
		return "/client/create";
	}

	/**
	 *
	 * Create En POST
	 * 
	 * @param client
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Client client, Model model,
			BindingResult result) throws ControllerException {
		logger.info("--create ClientController--");
		logger.debug("client : " + client);

		try {

			clientValidator.validate(client, result);

			if (result.hasErrors()) {

				List<Etablissement> etablissementsCombo = etablissementService
						.findAll();
				
				etablissementCache = new HashMap<Integer, Etablissement>();

				for (Etablissement etablissement : etablissementsCombo) {
					etablissementCache
							.put(etablissement.getId(), etablissement);
				}

				model.addAttribute("client", client);
				model.addAttribute("etablissementsCombo", etablissementsCombo);
				return "/client/create";
			}

			clientService.create(client);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/client/list";

	}

	/**
	 * 
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update ClientController -- clientId : " + id);

		Client client = null;
		List<Etablissement> etablissementsCombo;
		etablissementCache = new HashMap<Integer, Etablissement>();

		try {
			client = clientService.findByIdWithJoinFetchEtablissements(id);

			etablissementsCombo = etablissementService.findAll();
			for (Etablissement etablissement : etablissementsCombo) {
				etablissementCache.put(etablissement.getId(), etablissement);
			}
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("etablissementsCombo", etablissementsCombo);
		model.addAttribute("client", client);
		return "/client/create";
	}

	/**
	 * 
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete ClientController--");
		logger.debug("clientId : " + id);

		try {
			clientService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/client/list";
	}

	/**
	 * 
	 * 
	 * @param model
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String list(Model model) throws ControllerException {
		logger.info("--list ClientController--");

		List<Client> clients = null;
		try {
			clients = clientService.findAll();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("clients", clients);
		return "/client/list";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ControllerException {
		binder.registerCustomEditor(List.class, "etablissements",
				new CustomCollectionEditor(List.class) {
					protected Object convertElement(Object element) {
						List<Etablissement> etablissements = null;
						try {
							etablissementCache = new HashMap<Integer, Etablissement>();
							etablissements = etablissementService
									.findAll();
						} catch (ServiceException e) {
							logger.error(e.getMessage());
						}

						for (Etablissement etablissement : etablissements) {
							etablissementCache.put(etablissement.getId(),
									etablissement);
						}

						if (element instanceof Etablissement) {
							logger.debug("Conversion d'Etablissement en Etablissement: "
									+ element);
							return element;
						}
						if (element instanceof String
								|| element instanceof Integer) {
							Etablissement etablissement;
							if (element instanceof String) {
								etablissement = etablissementCache.get(Integer
										.valueOf((String) element));
							} else {

								etablissement = etablissementCache.get(element);
								logger.debug("Recherche d'établissement pour l'Id : "
										+ element + ": " + etablissement);
							}
							return etablissement;
						}
						logger.debug("Problème avec l'element : " + element);
						return null;
					}
				});
	}

	public Map<Integer, Etablissement> getEtablissementCache() {
		return etablissementCache;
	}

	public void setEtablissementCache(
			Map<Integer, Etablissement> etablissementCache) {
		this.etablissementCache = etablissementCache;
	}

}
