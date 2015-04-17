package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.PoidsDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Poids;
import fr.treeptik.service.PoidsService;

@Service
public class PoidsServiceImpl implements PoidsService {

	@Inject
	private PoidsDAO poidsDAO;

	private Logger logger = Logger.getLogger(PoidsServiceImpl.class);

	@Override
	public Poids findById(Integer id) throws ServiceException {
		return poidsDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Poids create(Poids Poids) throws ServiceException {
		logger.info("--CREATE PoidsService --");
		logger.debug("Poids : " + Poids);
		return poidsDAO.save(Poids);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Poids update(Poids Poids) throws ServiceException {
		logger.info("--UPDATE PoidsService --");
		logger.debug("Poids : " + Poids);
		try {

			Poids = poidsDAO.save(Poids);
		} catch (PersistenceException e) {
			logger.error("Error PoidsService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return Poids;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE PoidsService -- poidsId : " + id);
		try {
			poidsDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error PoidsService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<Poids> findAll() throws ServiceException {
		logger.info("--FINDALL PoidsService --");
		List<Poids> listPoids;
		try {
			listPoids = poidsDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error PoidsService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listPoids;
	}

}
