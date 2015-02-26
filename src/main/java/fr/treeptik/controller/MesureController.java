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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.AlerteDescription;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Point;
import fr.treeptik.model.Site;
import fr.treeptik.service.AlerteDescriptionService;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.OuvrageService;
import fr.treeptik.service.SiteService;
import fr.treeptik.util.DatePointComparator;

@Controller
@RequestMapping("/mesure")
public class MesureController {

	private Logger logger = Logger.getLogger(MesureController.class);

	@Inject
	private SiteService siteService;

	@Inject
	private OuvrageService ouvrageService;

	@Inject
	private EnregistreurService enregistreurService;

	@Inject
	private AlerteDescriptionService alerteDescriptionService;

	@Inject
	private MesureService mesureService;

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}/{enregistreurId}")
	public String delete(Model model, @PathVariable("id") Integer id,
			@PathVariable("enregistreurId") Integer enregistreurId)
			throws ControllerException {
		logger.info("--delete MesureController--");
		logger.debug("mesureId : " + id);

		try {
			mesureService.remove(id);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/enregistreur/update/" + enregistreurId;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/list", "/" })
	public String list(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--list MesureController--");

		List<Site> sitesCombo;
		List<Ouvrage> ouvragesCombo;
		List<Enregistreur> enregistreursCombo = new ArrayList<Enregistreur>();
		List<Mesure> mesures = new ArrayList<Mesure>();

		try {

			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				enregistreursCombo = enregistreurService.findAll();
				sitesCombo = siteService.findAll();
				ouvragesCombo = ouvrageService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				enregistreursCombo = enregistreurService.findAll();
				sitesCombo = siteService.findByClientLogin(userLogin);
				ouvragesCombo = ouvrageService.findByClientLogin(userLogin);
			}

			for (Enregistreur enregistreur : enregistreursCombo) {
				mesures.addAll(enregistreur.getMesures());
			}

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("mesures", mesures);
		model.addAttribute("ouvragesCombo", ouvragesCombo);
		model.addAttribute("sitesCombo", sitesCombo);
		model.addAttribute("enregistreursCombo", enregistreursCombo);
		return "/mesure/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/affect/niveau/manuel/{mesureId}")
	public String affectNewNiveauManuel(
			@PathVariable("mesureId") Integer mesureId)
			throws ControllerException {
		logger.info("--affectNewNiveauManuel MesureController--");
		logger.debug("mesureId : " + mesureId);

		try {
			mesureService.affectNewNiveauManuel(mesureId);


		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "redirect:/mesure/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/enregistreur/points/{enregistreurId}")
	public @ResponseBody List<Point> getEnregistreurPoints(
			HttpServletRequest request,
			@PathVariable("enregistreurId") Integer enregistreurId)
			throws ControllerException {
		logger.info("--getEnregistreurPoints MesureController--");

		List<Mesure> mesures = new ArrayList<Mesure>();
		List<Point> points = new ArrayList<Point>();

		try {

			mesures = mesureService.findByEnregistreurId(enregistreurId);


			for (Mesure item : mesures) {
				points.add(mesureService.transformMesureInPoint(item));
			}
			Collections.sort(points, new DatePointComparator());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return points;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/enregistreur/plotLines/{enregistreurId}")
	public @ResponseBody List<AlerteDescription> getEnregistreurAlertes(
			HttpServletRequest request,
			@PathVariable("enregistreurId") Integer enregistreurId)
			throws ControllerException {
		logger.info("--getEnregistreurAlertes MesureController--");

		List<AlerteDescription> alerteDescriptions = new ArrayList<AlerteDescription>();

		try {

			alerteDescriptions = alerteDescriptionService
					.findAlertesActivesByEnregistreurId(enregistreurId);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return alerteDescriptions;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/init/site")
	public @ResponseBody List<Site> initSiteCombobox(HttpServletRequest request)
			throws ControllerException {

		logger.info("--initSiteCombobox MesureController");

		List<Site> allSites = new ArrayList<Site>();
		try {

			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				allSites = siteService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				allSites = siteService.findByClientLogin(userLogin);
			}

			logger.debug("liste de site renvoyée : " + allSites);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		return allSites;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/init/graph/points")
	public @ResponseBody List<Point> initPointsGraph(HttpServletRequest request)
			throws ControllerException {

		logger.info("--initPointsGraph MesureController");

		List<Enregistreur> allEnregistreurs = new ArrayList<Enregistreur>();
		List<Mesure> mesures = new ArrayList<Mesure>();
		List<Point> points = new ArrayList<Point>();

		try {
			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				allEnregistreurs = enregistreurService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				allEnregistreurs = enregistreurService.findAll();
			}
			Enregistreur enregistreur = allEnregistreurs.get(0);
			mesures = enregistreur.getMesures();

			for (Mesure item : mesures) {
				points.add(mesureService.transformMesureInPoint(item));
			}
			Collections.sort(points, new DatePointComparator());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return points;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/init/graph/plotLines")
	public @ResponseBody List<AlerteDescription> initPlotLinesGraph(
			HttpServletRequest request) throws ControllerException {

		logger.info("--initPlotLinesGraph MesureController");

		List<Enregistreur> allEnregistreurs = new ArrayList<Enregistreur>();
		List<AlerteDescription> alerteDescriptions = new ArrayList<AlerteDescription>();

		try {
			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				allEnregistreurs = enregistreurService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				allEnregistreurs = enregistreurService.findAll();
			}

			alerteDescriptions = alerteDescriptionService
					.findAlertesActivesByEnregistreurId(allEnregistreurs.get(0)
							.getId());

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return alerteDescriptions;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/init/ouvrage")
	public @ResponseBody List<Ouvrage> initOuvrageCombobox(
			HttpServletRequest request) throws ControllerException {

		logger.info("--initOuvrageCombobox MesureController");

		List<Ouvrage> allOuvrages = new ArrayList<Ouvrage>();
		try {
			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				allOuvrages = ouvrageService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				allOuvrages = ouvrageService.findByClientLogin(userLogin);
			}

			logger.debug("liste d'ouvrage renvoyée : " + allOuvrages);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return allOuvrages;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/init/enregistreur")
	public @ResponseBody List<Enregistreur> initEnregistreurCombobox(
			HttpServletRequest request) throws ControllerException {

		logger.info("--initEnregistreurCombobox MesureController");

		List<Enregistreur> allEnregistreurs = new ArrayList<Enregistreur>();
		try {
			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);
			if (isAdmin) {
				allEnregistreurs = enregistreurService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);
				allEnregistreurs = enregistreurService.findAll();
			}
			logger.debug("liste des Enregistreurs renvoyée : "
					+ allEnregistreurs);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return allEnregistreurs;

	}

	@RequestMapping(method = RequestMethod.GET, value = "site/refresh/ouvrage/{siteId}")
	public @ResponseBody List<Ouvrage> refreshOuvrageBySite(
			HttpServletRequest request, @PathVariable("siteId") Integer siteId)
			throws ControllerException {
		logger.info("--refreshOuvrageBySite MesureController");

		List<Ouvrage> allOuvrages = new ArrayList<Ouvrage>();
		Site site;
		try {
			site = siteService.findByIdWithJoinFetchOuvrages(siteId);

			allOuvrages = site.getOuvrages();

			logger.debug("liste d'ouvrage renvoyée : " + allOuvrages);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return allOuvrages;
	}

	@RequestMapping(method = RequestMethod.GET, value = "site/refresh/enregistreur/{siteId}")
	public @ResponseBody List<Enregistreur> refreshEnregistreurBySite(
			HttpServletRequest request, @PathVariable("siteId") Integer siteId)
			throws ControllerException {
		logger.info("--refreshEnregistreurBySite MesureController");

		List<Enregistreur> allEnregistreurs = new ArrayList<Enregistreur>();
		try {

			allEnregistreurs = enregistreurService.findBySiteId(siteId);

			logger.debug("liste d'enregistreur renvoyée : " + allEnregistreurs);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return allEnregistreurs;
	}

	@RequestMapping(method = RequestMethod.GET, value = "ouvrage/refresh/enregistreur/{ouvrageId}")
	public @ResponseBody List<Enregistreur> refreshEnregistreurByOuvrage(
			HttpServletRequest request,
			@PathVariable("ouvrageId") Integer ouvrageId)
			throws ControllerException {
		logger.info("--refreshEnregistreurByOuvrage MesureController");

		List<Enregistreur> allEnregistreurs = new ArrayList<Enregistreur>();
		Ouvrage ouvrage;
		try {
			ouvrage = ouvrageService
					.findByIdWithJoinFetchEnregistreurs(ouvrageId);

			allEnregistreurs = ouvrage.getEnregistreurs();

			logger.debug("liste d'enregistreur renvoyée : " + allEnregistreurs);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return allEnregistreurs;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/enregistreur/points/{enregistreurId}/{dateDebut}/{dateFin}")
	public @ResponseBody List<Point> getEnregistreurPointsByDate(
			HttpServletRequest request,
			@PathVariable("enregistreurId") Integer enregistreurId,
			@PathVariable("dateDebut") Date dateDebut,
			@PathVariable("dateFin") Date dateFin) throws ControllerException {
		logger.info("--getEnregistreurPoints MesureController--");

		List<Mesure> mesures = new ArrayList<Mesure>();
		List<Point> points = new ArrayList<Point>();


		try {
			mesures = mesureService.findByEnregistreurIdBetweenDates(
					enregistreurId, dateDebut, dateFin);


			for (Mesure item : mesures) {
				points.add(mesureService.transformMesureInPoint(item));
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

}
