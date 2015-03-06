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
import fr.treeptik.util.DateMesureComparator;
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
		logger.info("--delete MesureController-- mesureId : " + id
				+ " -- enregistreurId : " + enregistreurId);

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
		List<AlerteDescription> alertesActivesCombo = new ArrayList<AlerteDescription>();

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

		Collections.sort(mesures, new DateMesureComparator());
		Collections.reverse(mesures);
		
		model.addAttribute("alertesActivesCombo", alertesActivesCombo);
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
	public @ResponseBody AlerteDescription refreshAlertePlotLinesByEnregistreur(
			HttpServletRequest request,
			@PathVariable("enregistreurId") Integer enregistreurId)
			throws ControllerException {
		logger.info("--refreshAlertePlotLinesByEnregistreur MesureController--");

		List<AlerteDescription> alerteDescriptions = new ArrayList<AlerteDescription>();
		AlerteDescription alerteDescription = new AlerteDescription();

		try {

			alerteDescriptions = alerteDescriptionService
					.findAlertesActivesByEnregistreurId(enregistreurId);
			alerteDescription = alerteDescriptions.get(0);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return alerteDescription;
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
	public @ResponseBody AlerteDescription initPlotLinesGraph(
			HttpServletRequest request) throws ControllerException {

		logger.info("--initPlotLinesGraph MesureController");

		List<Enregistreur> allEnregistreurs = new ArrayList<Enregistreur>();
		List<AlerteDescription> alerteDescriptions = new ArrayList<AlerteDescription>();
		AlerteDescription alerteDescription = new AlerteDescription();

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
			alerteDescription = alerteDescriptions.get(0);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return alerteDescription;
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/change/alerte/plotLines/{alerteId}")
	public @ResponseBody AlerteDescription changeAlertePlotLinesGraph(
			HttpServletRequest request, @PathVariable("alerteId") Integer alerteId) throws ControllerException {
		
		logger.info("--changeAlertePlotLinesGraph MesureController -- alerteId : "
					+ alerteId);

		AlerteDescription alerteDescription = new AlerteDescription();

		try {
			alerteDescription = alerteDescriptionService
					.findById(alerteId);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return alerteDescription;

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
		logger.info("--refreshOuvrageBySite MesureController -- siteId : "
				+ siteId);

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
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "enregistreur/refresh/alerte/{enregistreurId}")
	public @ResponseBody List<AlerteDescription> refreshAlerteComboboxByEnregistreur(
			HttpServletRequest request, @PathVariable("enregistreurId") Integer enregistreurId)
			throws ControllerException {
		logger.info("--refreshAlerteByEnregistreur MesureController -- enregistreurId : "
				+ enregistreurId);

		List<AlerteDescription> allAlertesActives = new ArrayList<AlerteDescription>();
		Enregistreur enregistreur;
		try {
			enregistreur = enregistreurService.findByIdWithJoinFetchAlertesActives(enregistreurId);

			allAlertesActives = enregistreur.getAlerteDescriptions();

			logger.debug("liste d'alertes renvoyée : " + allAlertesActives);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return allAlertesActives;
	}
	
	
	
	
	
	

	@RequestMapping(method = RequestMethod.GET, value = "site/refresh/enregistreur/{siteId}")
	public @ResponseBody List<Enregistreur> refreshEnregistreurBySite(
			HttpServletRequest request, @PathVariable("siteId") Integer siteId)
			throws ControllerException {
		logger.info("--refreshEnregistreurBySite MesureController -- siteId : "
				+ siteId);

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
		logger.info("--refreshEnregistreurByOuvrage MesureController -- ouvrageId : "
				+ ouvrageId);

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
		logger.info("--getEnregistreurPoints MesureController-- id : "
				+ enregistreurId + " dateDebut : " + dateDebut
				+ " -- dateFin : " + dateFin);

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
		logger.info("--initBinder MesureController --");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

}
