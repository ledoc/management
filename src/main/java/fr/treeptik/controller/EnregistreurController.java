package fr.treeptik.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Capteur;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Repas;
import fr.treeptik.model.TypeEnregistreur;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.OuvrageService;

@Controller
@RequestMapping("/enregistreur")
@SessionAttributes("enregistreur")
public class EnregistreurController {

	private Logger logger = Logger.getLogger(EnregistreurController.class);

	@Inject
	private EnregistreurService enregistreurService;
	@Inject
	private OuvrageService ouvrageService;

	@RequestMapping(method = RequestMethod.GET, value = "/create/{ouvrageId}")
	public String initForm(Model model,
			@PathVariable("ouvrageId") Integer ouvrageId)
			throws ControllerException {
		logger.info("--create formulaire EnregistreurController-- ouvrageId = "
				+ ouvrageId);

		Enregistreur enregistreur = new Enregistreur();
		Repas ouvrage = new Repas();

		try {
			ouvrage = ouvrageService.findById(ouvrageId);

			enregistreur.setOuvrage(ouvrage);
			/**
			 * TODO : attention
			 */
			enregistreur.setMaintenance(false);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("enregistreur", enregistreur);
		return "/enregistreur/create";
	}

	/**
	 *
	 * Create En POST
	 * 
	 * @param enregistreur
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Enregistreur enregistreur,
			BindingResult result) throws ControllerException {
		logger.debug(result.getAllErrors());
		logger.info("--create EnregistreurController-- enregistreur : "
				+ enregistreur);
		Repas ouvrage = new Repas();
		try {

			ouvrage = ouvrageService
					.findByIdWithJoinFetchEnregistreurs(enregistreur
							.getOuvrage().getId());

			enregistreur = enregistreurService.create(enregistreur);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/ouvrage/update/" + ouvrage.getId();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--update EnregistreurController-- enregistreurId : " + id);

		Enregistreur enregistreur = null;
		List<Capteur> capteurs;

		List<TypeEnregistreur> typesEnregistreurCombo = new ArrayList<TypeEnregistreur>(
				Arrays.asList(TypeEnregistreur.values()));

		try {
			enregistreur = enregistreurService.findByIdWithJoinCapteurs(id);
			capteurs = enregistreur.getCapteurs();

		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("capteurs", capteurs);
		model.addAttribute("typesEnregistreurCombo", typesEnregistreurCombo);
		model.addAttribute("enregistreur", enregistreur);
		return "/enregistreur/create";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id)
			throws ControllerException {
		logger.info("--delete EnregistreurController--");
		logger.debug("enregistreurId : " + id);
		Integer ouvrageId;
		try {
			ouvrageId = enregistreurService.findById(id).getOuvrage().getId();

			enregistreurService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/ouvrage/update/" + ouvrageId;
	}

}