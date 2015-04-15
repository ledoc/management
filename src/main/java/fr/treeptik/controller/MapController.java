package fr.treeptik.controller;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Marker;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;
import fr.treeptik.model.assembler.MarkerAssembler;
import fr.treeptik.service.EtablissementService;
import fr.treeptik.service.MapService;
import fr.treeptik.service.OuvrageService;
import fr.treeptik.service.SiteService;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carto")
public class MapController {

    public static final String SESSION_ATTRIBUT_MARKER = "marker";
    private Logger logger = Logger.getLogger(MapController.class);

	@Inject
	private MapService mapService;
	@Inject
	private EtablissementService etablissementService;
	@Inject
	private SiteService siteService;
	@Inject
	private OuvrageService ouvrageService;
    @Inject
	private MarkerAssembler markerAssembler;

    @RequestMapping(method = RequestMethod.GET, value = "/carto")
	public String goToCarto(Model model, HttpServletRequest request)
			throws ControllerException {
		logger.info("--goToCarto MapController--");

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
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}

		model.addAttribute("sitesCombo", sitesCombo);
		model.addAttribute("ouvragesCombo", ouvragesCombo);
		return "/carto/carto";
	}


	@RequestMapping(method = RequestMethod.GET, value = "/allItems")
	public @ResponseBody List<Marker> getAllItems(HttpServletRequest request)
			throws ControllerException {
		logger.info("--getAllOuvrages MapController--");

		List<Marker> markers = new ArrayList<Marker>();
		List<Site> sitesCombo = null;
		List<Ouvrage> ouvragesCombo = null;

		try {
			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);

			if (isAdmin) {
				sitesCombo = siteService.findAll();
				ouvragesCombo = ouvrageService.findAll();
			} else {
				String userLogin = SecurityContextHolder.getContext()
						.getAuthentication().getName();
				logger.debug("USER LOGIN : " + userLogin);

				sitesCombo = siteService.findByClientLogin(userLogin);
				ouvragesCombo = ouvrageService.findByClientLogin(userLogin);
			}

			for (Site item : sitesCombo) {
				if(mapService.isGeoLocalised(item)){
					markers.add(markerAssembler.fromSite(item));
				}
			}
			for (Ouvrage item : ouvragesCombo) {
				if(mapService.isGeoLocalised(item)){
					markers.add(markerAssembler.fromOuvrage(item));
				}
			}

		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return markers;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/ouvrage/{ouvrageId}")
	public @ResponseBody Marker localizeOuvrage(
			@PathVariable("ouvrageId") Integer ouvrageId, HttpServletRequest request)
			throws ControllerException {
		logger.info("--localizeOuvrage MapController-- ouvrageId : "
				+ ouvrageId);
		Marker marker = new Marker();
		try {
			Ouvrage ouvrage = ouvrageService.findById(ouvrageId);
			logger.debug("-- ouvrage : " + ouvrage);
			marker = markerAssembler.fromOuvrage(ouvrage);
            setSession(request, marker);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return marker;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/site/{siteId}")
	public @ResponseBody Marker localizeSite(
			@PathVariable("siteId") Integer siteId, HttpServletRequest request) throws ControllerException {
		logger.info("--localizeOuvrage MapController-- siteId : " + siteId);

		Marker marker = new Marker();
		try {
			Site site = siteService.findById(siteId);
			logger.debug("--site : " + site);

			marker = markerAssembler.fromSite(site);
            setSession(request, marker);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return marker;
	}

    @RequestMapping(method = RequestMethod.GET, value = "/init")
    public @ResponseBody Marker localizeEtablissement(HttpServletRequest request)
            throws ControllerException {
        logger.info("--init MapController--");
        return getSession(request);
    }

    private Marker getSession(HttpServletRequest request)  {
        Marker marker = (Marker) request.getSession().getAttribute(SESSION_ATTRIBUT_MARKER);
        if(marker != null){
            return marker;
        }
        return new Marker();
    }

    private void setSession(HttpServletRequest request, Marker marker){
        request.getSession().setAttribute(SESSION_ATTRIBUT_MARKER, marker);
    }
}
