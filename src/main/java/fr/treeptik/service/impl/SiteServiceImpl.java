package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.SiteDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Client;
import fr.treeptik.model.Site;
import fr.treeptik.service.SiteService;

@Service
public class SiteServiceImpl implements SiteService {

	@Inject
	private SiteDAO siteDAO;

	private Logger logger = Logger.getLogger(SiteServiceImpl.class);

	@Override
	public Site findById(Integer id) throws ServiceException {
		logger.info("--findById site --");
		return siteDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Site create(Site site) throws ServiceException {
		logger.info("--CREATE site --");
		logger.debug("site : " + site);
		return siteDAO.save(site);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Site update(Site site) throws ServiceException {
		logger.info("--UPDATE site --");
		logger.debug("site : " + site);
		return siteDAO.saveAndFlush(site);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Site site) throws ServiceException {
		logger.info("--DELETE site --");
		logger.debug("site : " + site);
		siteDAO.delete(site);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE site Id --");
		logger.debug("siteId : " + id);
		siteDAO.delete(id);
	}

	@Override
	public List<Site> findAll() throws ServiceException {
		logger.info("--FINDALL site --");
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
		logger.info("--findByIdWithJoinFetchOuvrages site --");
		logger.info("id : " + id);

		Site site;
		try {
			site = siteDAO.findByIdWithJoinFetchOuvrages(id);
		} catch (PersistenceException e) {
			logger.error("Error EtablissementService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return site;
	}

	@Override
	public Site setCodeWithCheck(Site site) throws ServiceException {
		logger.info("--setCodeWithCheck SiteServiceImpl --");

		int i = 1;
		List<Site> allSites = this.findAll();
		site.setCode(site.getNom() + i);
		for (Site site2 : allSites) {
			if (site.getNom().equalsIgnoreCase(site2.getNom())) {
				site.setCode(site.getNom() + i);
				if (site.getCode().equalsIgnoreCase(site2.getCode())) {
					i++;
					site.setCode(site.getNom() + i);
					logger.info(site.getCode());
				}
			}
		}
		logger.debug("Site : " + site);
		return site;
	}

	@Override
	public List<Site> findByClient(String userLogin) {
		logger.info("--FINDALL bY CLIENT LOGIN --");
		return siteDAO.findByClientLogin(userLogin);
	}

}
