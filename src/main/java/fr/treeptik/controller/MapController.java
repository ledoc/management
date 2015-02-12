package fr.treeptik.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Marker;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;
import fr.treeptik.service.EtablissementService;
import fr.treeptik.service.MapService;
import fr.treeptik.service.OuvrageService;
import fr.treeptik.service.SiteService;

@Controller
@RequestMapping("/carto")
public class MapController {

	private Logger logger = Logger.getLogger(MapController.class);

	@Inject
	private MapService mapService;
	@Inject
	private EtablissementService etablissementService;
	@Inject
	private SiteService siteService;
	@Inject
	private OuvrageService ouvrageService;

	@RequestMapping(method = RequestMethod.GET, value = "/carto")
	public String goToCarto(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--goToCarto MapController--");

		List<Marker> markers = new ArrayList<Marker>();
		List<Site> sitesCombo = null;
		List<Etablissement> etablissementsCombo = null;
		List<Ouvrage> ouvragesCombo = null;

		try {

			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);

			if (isAdmin) {
				sitesCombo = siteService.findAll();
				ouvragesCombo = ouvrageService.findAll();
				etablissementsCombo = etablissementService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);

				sitesCombo = siteService.findByClientLogin(userLogin);
				etablissementsCombo = etablissementService
						.findByClientLogin(userLogin);
				ouvragesCombo = ouvrageService.findByClientLogin(userLogin);
			}

			for (Site item : sitesCombo) {
				markers.add(mapService.transformSiteInMarker(item));
			}
			for (Ouvrage item : ouvragesCombo) {
				markers.add(mapService.transformOuvrageInMarker(item));
			}

			for (Etablissement item : etablissementsCombo) {
				markers.add(mapService.transformEtablissementInMarker(item));
			}

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("sitesCombo", sitesCombo);
		model.addAttribute("etablissementsCombo", etablissementsCombo);
		model.addAttribute("ouvragesCombo", ouvragesCombo);
		return "/carto/carto";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allItems")
	public @ResponseBody List<Marker> getAllItems(HttpServletRequest request)
			throws ControllerException {
		logger.info("--getAllOuvrages MapController--");

		List<Marker> markers = new ArrayList<Marker>();
		List<Site> sitesCombo = null;
		List<Etablissement> etablissementsCombo = null;
		List<Ouvrage> ouvragesCombo = null;

		try {

			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);

			if (isAdmin) {
				sitesCombo = siteService.findAll();
				ouvragesCombo = ouvrageService.findAll();
				etablissementsCombo = etablissementService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);

				sitesCombo = siteService.findByClientLogin(userLogin);
				etablissementsCombo = etablissementService
						.findByClientLogin(userLogin);
				ouvragesCombo = ouvrageService.findByClientLogin(userLogin);
			}

			for (Site item : sitesCombo) {
				markers.add(mapService.transformSiteInMarker(item));
			}
			for (Ouvrage item : ouvragesCombo) {
				markers.add(mapService.transformOuvrageInMarker(item));
			}

			for (Etablissement item : etablissementsCombo) {
				markers.add(mapService.transformEtablissementInMarker(item));
			}

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return markers;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/ouvrage/{ouvrageId}")
	public @ResponseBody Marker localizeOuvrage(
			@PathVariable("ouvrageId") Integer ouvrageId)
			throws ControllerException {
		logger.info("--localizeOuvrage MapController-- ouvrageId : "
				+ ouvrageId);

		Marker marker = new Marker();
		try {
			Ouvrage ouvrage = ouvrageService.findById(ouvrageId);

			logger.debug("-- ouvrage : " + ouvrage);

			marker = mapService.transformOuvrageInMarker(ouvrage);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return marker;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/site/{siteId}")
	public @ResponseBody Marker localizeSite(
			@PathVariable("siteId") Integer siteId) throws ControllerException {
		logger.info("--localizeOuvrage MapController-- siteId : " + siteId);

		Marker marker = new Marker();
		try {
			Site site = siteService.findById(siteId);
			logger.debug("--site : " + site);

			marker = mapService.transformSiteInMarker(site);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return marker;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/etablissement/{etablissementId}")
	public @ResponseBody Marker localizeEtablissement(
			@PathVariable("etablissementId") Integer etablissementId)
			throws ControllerException {
		logger.info("--localizeEtablissement MapController-- etablissementId : "
				+ etablissementId);

		Marker marker = new Marker();
		try {
			Etablissement etablissement = etablissementService
					.findById(etablissementId);

			logger.debug("--etablissement : " + etablissement);

			marker = mapService.transformEtablissementInMarker(etablissement);

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return marker;
	}
}
