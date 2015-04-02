package fr.treeptik.controller;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.*;
import fr.treeptik.service.*;
import fr.treeptik.util.DateMesureComparator;
import fr.treeptik.util.DatePointComparator;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    private CapteurService capteurService;

    @Inject
    private AlerteDescriptionService alerteDescriptionService;

    @Inject
    private MesureService mesureService;

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}/{capteurId}")
    public String delete(Model model, @PathVariable("id") Integer id,
                         @PathVariable("capteurId") Integer capteurId)
            throws ControllerException {
        logger.info("--delete MesureController-- mesureId : " + id
                + " -- capteurId : " + capteurId);

        try {
            mesureService.remove(id);
        } catch (NumberFormatException | ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        return "redirect:/capteur/update/" + capteurId;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/list", "/"})
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
                enregistreur = enregistreurService
                        .findByIdWithJoinCapteurs(enregistreur.getId());
                for (Capteur capteur : enregistreur.getCapteurs())
                    mesures.addAll(capteur.getMesures());
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

    @RequestMapping(method = RequestMethod.GET, value = "/init/site")
    public
    @ResponseBody
    List<Site> initSiteCombobox(HttpServletRequest request)
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

    @RequestMapping(method = RequestMethod.GET, value = "/init/ouvrage")
    public
    @ResponseBody
    List<Ouvrage> initOuvrageCombobox(
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
    public
    @ResponseBody
    List<Enregistreur> initEnregistreurCombobox(
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

    /**
     * On initialise avec la premiere alerte du premier capteur en retirant le
     * capteur de temperature
     *
     * @param request
     * @return
     * @throws ControllerException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/init/graph/points")
    public
    @ResponseBody
    List<Point> initPointsGraph(HttpServletRequest request)
            throws ControllerException {

        logger.info("--initPointsGraph MesureController");

        List<Enregistreur> allEnregistreurs = new ArrayList<Enregistreur>();
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
			enregistreur = enregistreurService
					.findByIdWithJoinCapteurs(enregistreur.getId());
			List<Capteur> capteurs = enregistreur.getCapteurs();

			// RETRAIT du capteur de temperature pour l'init
			capteurs.removeIf(c -> c.getTypeCaptAlerteMesure().getNom()
					.equals("TEMPERATURE"));

			for (Mesure item : capteurs.get(0).getMesures()) {
				points.add(mesureService.transformMesureInPoint(item));
			}
			Collections.sort(points, new DatePointComparator());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return points;
    }

    /**
     * On initialise avec la premiere alerte du premier capteur en retirant le
     * capteur de temperature
     *
     * @param request
     * @return
     * @throws ControllerException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/init/graph/plotLines")
    public
    @ResponseBody
    AlerteDescription initPlotLinesGraph(
            HttpServletRequest request) throws ControllerException {

        logger.info("--initPlotLinesGraph MesureController");

        List<Enregistreur> allEnregistreurs = new ArrayList<Enregistreur>();
        List<AlerteDescription> alerteDescriptions = null;
        AlerteDescription alerteDescription = null;

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
            enregistreur = enregistreurService
                    .findByIdWithJoinCapteurs(enregistreur.getId());
            List<Capteur> capteurs = enregistreur.getCapteurs();

            // RETRAIT du capteur de temperature pour l'init
            capteurs.removeIf(c -> c.getTypeCaptAlerteMesure().getNom()
                    .equals("TEMPERATURE"));

            alerteDescriptions = alerteDescriptionService
                    .findAlertesActivesByCapteurId(capteurs.get(0).getId());

            if (alerteDescriptions.size() > 0) {
                alerteDescription = alerteDescriptions.get(0);
            }

        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        return alerteDescription;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/capteur/points/{capteurId}")
    public
    @ResponseBody
    List<Point> getCapteurPoints(
            HttpServletRequest request,
            @PathVariable("capteurId") Integer capteurId)
            throws ControllerException {
        logger.info("--getCapteurPoints MesureController--");

		List<Mesure> mesures = new ArrayList<Mesure>();
		List<Point> points = new ArrayList<Point>();

		try {

			mesures = mesureService.findByCapteurIdWithFetch(capteurId);

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

    /**
     * Après changement de capteur selectionner le graphe est initialisé avec la
     * première alerte de la liste
     *
     * @param request
     * @param capteurId
     * @return
     * @throws ControllerException
     */

    @RequestMapping(method = RequestMethod.GET, value = "/capteur/plotLines/{capteurId}")
    public
    @ResponseBody
    AlerteDescription refreshAlertePlotLinesByCapteur(
            HttpServletRequest request,
            @PathVariable("capteurId") Integer capteurId)
            throws ControllerException {
        logger.info("--refreshAlertePlotLinesByCapteur MesureController--");

        List<AlerteDescription> alerteDescriptions = null;
        AlerteDescription alerteDescription = null;

        try {

            alerteDescriptions = alerteDescriptionService
                    .findAlertesActivesByCapteurId(capteurId);

            if (alerteDescriptions.size() > 0) {
                alerteDescription = alerteDescriptions.get(0);
            }

        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        return alerteDescription;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/change/alerte/plotLines/{alerteId}")
    public
    @ResponseBody
    AlerteDescription changeAlertePlotLinesGraph(
            HttpServletRequest request,
            @PathVariable("alerteId") Integer alerteId)
            throws ControllerException {

        logger.info("--changeAlertePlotLinesGraph MesureController -- alerteId : "
                + alerteId);

        AlerteDescription alerteDescription = new AlerteDescription();

        try {
            alerteDescription = alerteDescriptionService.findById(alerteId);

        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        return alerteDescription;

    }

    @RequestMapping(method = RequestMethod.GET, value = "site/refresh/ouvrage/{siteId}")
    public
    @ResponseBody
    List<Ouvrage> refreshOuvrageBySite(
            HttpServletRequest request, @PathVariable("siteId") Integer siteId)
            throws ControllerException {
        logger.info("--refreshOuvrageBySite MesureController -- siteId : "
                + siteId);

        List<Ouvrage> allOuvrages = new ArrayList<Ouvrage>();
        Site site;
        try {
            site = siteService.findByIdWithJoinFetchOuvrages(siteId);

            allOuvrages = site.getOuvrages();

        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        return allOuvrages;
    }

    @RequestMapping(method = RequestMethod.GET, value = "capteur/refresh/alerte/{capteurId}")
    public
    @ResponseBody
    List<AlerteDescription> refreshAlerteComboboxByCapteur(
            HttpServletRequest request,
            @PathVariable("capteurId") Integer capteurId)
            throws ControllerException {
        logger.info("--refreshAlerteComboboxByCapteur MesureController -- capteurId : "
                + capteurId);

        List<AlerteDescription> allAlertesActives = new ArrayList<AlerteDescription>();
        Capteur capteur;
        try {
            capteur = capteurService
                    .findByIdWithJoinFetchAlertesActives(capteurId);

            allAlertesActives = capteur.getAlerteDescriptions();

        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        return allAlertesActives;
    }

    @RequestMapping(method = RequestMethod.GET, value = "site/refresh/enregistreur/{siteId}")
    public
    @ResponseBody
    List<Enregistreur> refreshEnregistreurBySite(
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
    public
    @ResponseBody
    List<Enregistreur> refreshEnregistreurByOuvrage(
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

    @RequestMapping(method = RequestMethod.GET, value = "enregistreur/refresh/capteur/{enregistreurId}")
    public
    @ResponseBody
    List<Capteur> refreshCapteursByEnregistreur(
            HttpServletRequest request,
            @PathVariable("enregistreurId") Integer enregistreurId)
            throws ControllerException {
        logger.info("--refreshCapteursByEnregistreur MesureController -- enregistreurId : "
                + enregistreurId);

        List<Capteur> allCapteurs = new ArrayList<Capteur>();
        try {
            allCapteurs = capteurService
                    .findAllByEnregistreurId(enregistreurId);

        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        return allCapteurs;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/capteur/points/{capteurId}/{dateDebut}/{dateFin}")
    public
    @ResponseBody
    List<Point> getCapteurPointsByDate(
            HttpServletRequest request,
            @PathVariable("capteurId") Integer capteurId,
            @PathVariable("dateDebut") Date dateDebut,
            @PathVariable("dateFin") Date dateFin) throws ControllerException {
        logger.info("--getEnregistreurPoints MesureController-- "
                + " dateDebut : " + dateDebut + " -- dateFin : " + dateFin);

		List<Mesure> mesures = new ArrayList<Mesure>();
		List<Point> points = new ArrayList<Point>();

		try {
			mesures = mesureService.findByCapteurIdBetweenDates(capteurId,
					dateDebut, dateFin);

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

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "YYYY-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

}
