package fr.treeptik.service.impl;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Capteur;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.Project;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.OuvrageService;
import fr.treeptik.service.ProjectService;
import fr.treeptik.service.SiteService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private SiteService siteService;

    @Inject
    private OuvrageService ouvrageService;

    @Inject
    private EnregistreurService enregistreurService;

    /*@Inject
    private CapteurService capteurService;

    @Inject
    private AlerteDescriptionService alerteDescriptionService;

    @Inject
    private MesureService mesureService;*/

    private Logger logger = Logger.getLogger(ProjectServiceImpl.class);

    @Override
    public Project find(boolean isAdmin, String userLogin) throws ServiceException {
        logger.debug("USER ROLE ADMIN : " + isAdmin);
        Project project = new Project();
        if (isAdmin) {
            project.setEnregistreurs(enregistreurService.findAll());
            project.setSites(siteService.findAll());
            project.setOuvrages(ouvrageService.findAll());

        } else {
            logger.debug("USER LOGIN : " + userLogin);
            project.setEnregistreurs(enregistreurService.findByClientLogin(userLogin));
            project.setSites(siteService.findByClientLogin(userLogin));
            project.setOuvrages(ouvrageService.findByClientLogin(userLogin));
        }
        List<Mesure> mesures = new ArrayList<>();
        for (Enregistreur enregistreur : project.getEnregistreurs()) {
            enregistreur = enregistreurService
                    .findByIdWithJoinCapteurs(enregistreur.getId());
            for (Capteur capteur : enregistreur.getCapteurs()) {
                mesures.addAll(capteur.getMesures());
            }
        }
        project.setMesures(mesures);
        return project;

    }

}
