package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
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
	public TrameDW create(TrameDW trameDW) throws ServiceException {
		logger.info("--CREATE MESURE --");
		logger.debug("trameDW : " + trameDW);
		return trameDWDAO.save(trameDW);
	}

	@Override
	public TrameDW update(TrameDW trameDW) throws ServiceException {
		logger.info("--UPDATE MESURE --");
		logger.debug("trameDW : " + trameDW);
		return trameDWDAO.saveAndFlush(trameDW);
	}

	@Override
	public void remove(TrameDW trameDW) throws ServiceException {
		logger.info("--DELETE MESURE --");
		logger.debug("trameDW : " + trameDW);
		trameDWDAO.delete(trameDW);
	}

	@Override
	public List<TrameDW> findAll() throws ServiceException {
		logger.info("--FINDALL MESURE --");
		return trameDWDAO.findAll();
	}


}
