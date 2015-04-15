package fr.treeptik.controller;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.*;
import fr.treeptik.model.assembler.CapteurAssembler;
import fr.treeptik.model.assembler.EnregistreurAssembler;
import fr.treeptik.model.assembler.OuvrageAssembler;
import fr.treeptik.model.assembler.SiteAssembler;
import fr.treeptik.service.*;
import fr.treeptik.shared.dto.capteur.CapteurDTO;
import fr.treeptik.shared.dto.capteur.OuvrageDTO;
import fr.treeptik.shared.dto.capteur.SiteDTO;
import fr.treeptik.shared.dto.graph.GraphDTO;
import fr.treeptik.shared.dto.graph.PointGraphDTO;
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

    private CapteurAssembler capteurAssembler = new CapteurAssembler();
    private EnregistreurAssembler enregistreurAssembler = new EnregistreurAssembler();
    private OuvrageAssembler ouvrageAssembler = new OuvrageAssembler(enregistreurAssembler);
    private SiteAssembler siteAssembler = new SiteAssembler(ouvrageAssembler);

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

    @RequestMapping(method = RequestMethod.GET, value = "/list/{ouvrageId}")
    public String list(Model model, HttpServletRequest request, @PathVariable("ouvrageId") Integer ouvrageId) throws ControllerException {
        logger.info("--list MesureController--");
        List<Mesure> mesures = mesures(request.isUserInRole("ADMIN"), SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("mesures", mesures);
        model.addAttribute("ouvrageId", ouvrageId);
        return "/mesure/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/list", "/"})
    public String list(Model model, HttpServletRequest request)
            throws ControllerException {
        logger.info("--list MesureController--");
        List<Mesure> mesures = mesures(request.isUserInRole("ADMIN"), SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("mesures", mesures);
        return "/mesure/list";
    }

    private List<Mesure> mesures(boolean isAdmin, String userLogin) throws ControllerException {
        List<Mesure> mesures = new ArrayList<>();
        try {
            logger.debug("USER ROLE ADMIN : " + isAdmin);
            List<Enregistreur> enregistreurs = new ArrayList<Enregistreur>();
            if (isAdmin) {
                enregistreurs = enregistreurService.findAll();
            } else {
                logger.debug("USER LOGIN : " + userLogin);
                enregistreurs = enregistreurService.findByClientLogin(userLogin);
            }
            for (Enregistreur enregistreur : enregistreurs) {
                enregistreur = enregistreurService.findByIdWithJoinCapteurs(enregistreur.getId());
                for (Capteur capteur : enregistreur.getCapteurs()) {
                    mesures.addAll(capteur.getMesures());
                }
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }

        for (Mesure mesure : mesures) {
            mesureService.convertForDisplay(mesure);
        }
        Collections.sort(mesures, new DateMesureComparator());
        Collections.reverse(mesures);
        return mesures;
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
    List<SiteDTO> initSite(HttpServletRequest request)
            throws ControllerException {
        logger.info("--initSite MesureController");
        return siteAssembler.toDTOs(sitesWithOuvrage(request.isUserInRole("ADMIN"), SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    private List<Site> sitesWithOuvrage(Boolean isAdmin, String userLogin) throws ControllerException {
        try {
            logger.debug("USER ROLE ADMIN : " + isAdmin);
            if (isAdmin) {
                return siteService.findAll();
            }
            logger.debug("USER LOGIN : " + userLogin);
            return siteService.findByClientLogin(userLogin);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/site/refresh/ouvrage/{siteId}")
    public
    @ResponseBody
    List<OuvrageDTO> refreshOuvrage(HttpServletRequest request, @PathVariable("siteId") int siteId)
            throws ControllerException {
        logger.info("--refreshEnregistreur MesureController");
        return ouvrageAssembler.toDTOs(ouvragesFromSiteId(siteId));
    }

    private List<Ouvrage> ouvragesFromSiteId(int siteId) {
        try {
            Site site = siteService.findByIdWithJoinFetchOuvrages(siteId);
            return site.getOuvrages();
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return new ArrayList<Ouvrage>();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ouvrage/refresh/capteur/{ouvrageId}")
    public
    @ResponseBody
    List<CapteurDTO> refreshCapteur(HttpServletRequest request, @PathVariable("ouvrageId") int ouvrageId)
            throws ControllerException {
        logger.info("--refreshEnregistreur MesureController");
        return capteurAssembler.toDTOs(capteursFromOuvrageId(ouvrageId));
    }

    private List<Capteur> capteursFromOuvrageId(int ouvrageId) {
        try {
            return capteurService.findAllByOuvrageId(ouvrageId);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return new ArrayList<Capteur>();
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/init/site/ouvrage/{ouvrageId}")
    public
    @ResponseBody
    SiteDTO initWithOuvrageId(HttpServletRequest request, @PathVariable("ouvrageId") int ouvrageId)
            throws ControllerException {
        logger.info("--refreshEnregistreur MesureController");
        return siteAssembler.toDTO(siteFromOuvrageId(ouvrageId));
    }

    private Site siteFromOuvrageId(int ouvrageId) {
        try {
            Ouvrage ouvrage = ouvrageService.findById(ouvrageId);
            return siteService.findByIdWithJoinFetchOuvrages(ouvrage.getSite().getId());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return new Site();
        }
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
    GraphDTO initPointsGraph(HttpServletRequest request)
            throws ControllerException {

        logger.info("--initPointsGraph MesureController");
        GraphDTO graph = new GraphDTO();

        List<Enregistreur> allEnregistreurs = new ArrayList<Enregistreur>();
        List<PointGraphDTO> points = new ArrayList<PointGraphDTO>();

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

            graph.setMid(enregistreur.getMid());
            if(!capteurs.get(0).getMesures().isEmpty()){
                graph.setName(capteurs.get(0).getTypeCaptAlerteMesure().getDescription());
                graph.setUnite(capteurs.get(0).getMesures().get(0).getUnite());
                for (Mesure item : capteurs.get(0).getMesures()) {
                    points.add(mesureService.transformMesureInPoint(item));
                }
                Collections.sort(points, new DatePointComparator());
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        graph.setPoints(points);
        return graph;
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
    GraphDTO getCapteurPoints(
            HttpServletRequest request,
            @PathVariable("capteurId") Integer capteurId)
            throws ControllerException {
        logger.info("--getCapteurPoints MesureController--");

        List<Mesure> mesures = new ArrayList<Mesure>();
        GraphDTO graph = new GraphDTO();
        List<PointGraphDTO> points = new ArrayList<PointGraphDTO>();
        try {
            mesures = mesureService.findByCapteurIdWithFetch(capteurId);
            if(!mesures.isEmpty()){
                graph.setMid(mesures.get(0).getCapteur().getEnregistreur().getMid());
                graph.setName(mesures.get(0).getCapteur().getTypeCaptAlerteMesure().getDescription());
                graph.setUnite(mesures.get(0).getUnite());
                for (Mesure item : mesures) {
                    points.add(mesureService.transformMesureInPoint(item));
                }
                Collections.sort(points, new DatePointComparator());
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        graph.setPoints(points);
        return graph;
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

    @RequestMapping(method = RequestMethod.GET, value = "/capteur/points/{capteurId}/{dateDebut}/{dateFin}")
    public
    @ResponseBody
    GraphDTO getCapteurPointsByDate(
            HttpServletRequest request,
            @PathVariable("capteurId") Integer capteurId,
            @PathVariable("dateDebut") Date dateDebut,
            @PathVariable("dateFin") Date dateFin) throws ControllerException {
        logger.info("--getEnregistreurPoints MesureController-- "
                + " dateDebut : " + dateDebut + " -- dateFin : " + dateFin);


        GraphDTO graph = new GraphDTO();
		List<Mesure> mesures = new ArrayList<Mesure>();
		List<PointGraphDTO> points = new ArrayList<PointGraphDTO>();

        try {
            mesures = mesureService.findByCapteurIdBetweenDates(capteurId,
                    dateDebut, dateFin);
            if(!mesures.isEmpty()){
                graph.setMid(mesures.get(0).getCapteur().getEnregistreur().getMid());
                graph.setName(mesures.get(0).getCapteur().getTypeCaptAlerteMesure().getDescription());
                graph.setUnite(mesures.get(0).getUnite());
                for (Mesure item : mesures) {
                    points.add(mesureService.transformMesureInPoint(item));
                }
                Collections.sort(points, new DatePointComparator());
            }

        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        graph.setPoints(points);
        return graph;

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
