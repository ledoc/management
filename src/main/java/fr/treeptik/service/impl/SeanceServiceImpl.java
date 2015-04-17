package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.SeanceDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Seance;
import fr.treeptik.service.SeanceService;

@Service
public class SeanceServiceImpl implements SeanceService {

	@Inject
	private SeanceDAO seanceDAO;

	private Logger logger = Logger.getLogger(SeanceServiceImpl.class);

	@Override
	public Seance findById(Integer id) throws ServiceException {
		return seanceDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Seance create(Seance seance) throws ServiceException {
		logger.info("--CREATE SeanceService --");
		logger.debug("seance : " + seance);
		return seanceDAO.save(seance);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Seance update(Seance seance) throws ServiceException {
		logger.info("--UPDATE SeanceService --");
		logger.debug("seance : " + seance);
		try {

			seance = seanceDAO.save(seance);
		} catch (PersistenceException e) {
			logger.error("Error SeanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		return seance;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE SeanceService -- seanceId : " + id);
		try {
			seanceDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error SeanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

	}

	@Override
	public List<Seance> findAll() throws ServiceException {
		logger.info("--FINDALL SeanceService --");
		List<Seance> seances;
		try {
			seances = seanceDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error SeanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return seances;
	}

}
