package fr.treeptik.service.impl;

import fr.treeptik.dao.SiteDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Site;
import fr.treeptik.service.SiteService;
import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    @Inject
    private SiteDAO siteDAO;


    private Logger logger = Logger.getLogger(SiteServiceImpl.class);

    @Override
    public Site findById(Integer id) throws ServiceException {
        logger.info("--findById SiteServiceImpl --");
        return siteDAO.findOne(id);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Site create(Site site) throws ServiceException {
        logger.info("--CREATE SiteServiceImpl --");
        logger.debug("site : " + site);
        return siteDAO.save(site);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Site update(Site site) throws ServiceException {
        logger.info("--UPDATE SiteServiceImpl --");
        logger.debug("site : " + site);
        return siteDAO.saveAndFlush(site);
    }

    @Override
    @Secured("ADMIN")
    @Transactional(rollbackFor = ServiceException.class)
    public void remove(Integer id) throws ServiceException {
        logger.info("--DELETE SiteServiceImpl Id --");
        logger.debug("siteId : " + id);
        siteDAO.delete(id);
    }

    @Override
    public List<Site> findAll() throws ServiceException {
        logger.info("--FINDALL SiteServiceImpl --");
        return siteDAO.findAll();
    }


    /**
     * Méthode spécifique pour récupérer les sites associées à un etablissement
     * dû au FetchType.Lazy
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Site findByIdWithJoinFetchOuvrages(Integer id)
            throws ServiceException {
        logger.info("--findByIdWithJoinFetchOuvrages SiteServiceImpl --");
        logger.debug("id : " + id);

        Site site;
        try {
            site = siteDAO.findByIdWithJoinFetchOuvrages(id);
        } catch (PersistenceException e) {
            logger.error("Error SiteService : " + e);
            throw new ServiceException(e.getLocalizedMessage(), e);
        }
        return site;
    }

    @Override
    public List<Site> findByClientLogin(String userLogin) throws ServiceException {
        logger.info("--findByClientLogin userLogin SiteServiceImpl--");
        logger.debug("userLogin : " + userLogin);
        return siteDAO.findByClientLogin(userLogin);
    }
}
