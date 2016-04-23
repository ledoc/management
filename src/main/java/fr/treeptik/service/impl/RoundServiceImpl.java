package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.RoundDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Round;
import fr.treeptik.service.RoundService;

@Service
public class RoundServiceImpl implements RoundService {

	@Inject
	private RoundDAO roundDAO;

	private Logger logger = Logger.getLogger(RoundServiceImpl.class);

	@Override
	public Round findById(Integer id) throws ServiceException {
		return roundDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Round create(Round Round) throws ServiceException {
		logger.info("--CREATE RoundService --");
		logger.debug("Round : " + Round);
		return roundDAO.save(Round);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Round update(Round Round) throws ServiceException {
		logger.info("--UPDATE RoundService --");
		logger.debug("Round : " + Round);
		try {

			Round = roundDAO.save(Round);
		} catch (PersistenceException e) {
			logger.error("Error RoundService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return Round;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE RoundService -- roundId : " + id);
		try {
			roundDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error RoundService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<Round> findAll() throws ServiceException {
		logger.info("--FINDALL RoundService --");
		List<Round> listRound;
		try {
			listRound = roundDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error RoundService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listRound;
	}

}
