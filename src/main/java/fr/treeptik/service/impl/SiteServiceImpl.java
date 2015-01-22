package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.SiteDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Site;
import fr.treeptik.service.SiteService;

@Service
public class SiteServiceImpl implements SiteService {

	@Inject
	private SiteDAO siteDAO;

	private Logger logger = Logger.getLogger(SiteServiceImpl.class);

	@Override
	public Site findById(Integer id) throws ServiceException {
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
		logger.info("--DELETE site --");
		logger.debug("siteId : " + id);
		siteDAO.delete(id);
	}

	@Override
	public List<Site> findAll() throws ServiceException {
		logger.info("--FINDALL site --");
		return siteDAO.findAll();
	}
	
	
	

}
