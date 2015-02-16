package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.TrameDWDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.TrameDW;
import fr.treeptik.service.TrameDWService;

@Service
public class TrameDWServiceImpl implements TrameDWService {

	@Inject
	private TrameDWDAO trameDWDAO;

	private Logger logger = Logger.getLogger(TrameDWServiceImpl.class);

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public TrameDW findById(Integer id) throws ServiceException {
		return trameDWDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public TrameDW create(TrameDW trameDW) throws ServiceException {
		logger.info("--CREATE trameDW --");
		logger.debug("trameDW : " + trameDW);
		return trameDWDAO.save(trameDW);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public TrameDW update(TrameDW trameDW) throws ServiceException {
		logger.info("--UPDATE trameDW --");
		logger.debug("trameDW : " + trameDW);
		return trameDWDAO.saveAndFlush(trameDW);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE trameDW --");
		logger.debug("trameDWId : " + id);
		trameDWDAO.delete(id);
	}

	@Override
	public List<TrameDW> findAll() throws ServiceException {
		logger.info("--FINDALL trameDW --");
		return trameDWDAO.findAll();
	}

	
	

}
