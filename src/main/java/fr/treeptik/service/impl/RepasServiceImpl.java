package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.RepasDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Repas;
import fr.treeptik.service.RepasService;

@Service
public class RepasServiceImpl implements RepasService {

	@Inject
	private RepasDAO repasDAO;

	private Logger logger = Logger.getLogger(RepasServiceImpl.class);

	@Override
	public Repas findById(Integer id) throws ServiceException {
		return repasDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Repas create(Repas repas) throws ServiceException {
		logger.info("--CREATE RepasService --");
		logger.debug("repas : " + repas);
		return repasDAO.save(repas);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Repas update(Repas repas) throws ServiceException {
		logger.info("--UPDATE RepasService --");
		logger.debug("repas : " + repas);
		try {

			repas = repasDAO.save(repas);
		} catch (PersistenceException e) {
			logger.error("Error RepasService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return repas;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE RepasService -- repasId : " + id);
		try {
			repasDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error RepasService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<Repas> findAll() throws ServiceException {
		logger.info("--FINDALL RepasService --");
		List<Repas> listRepas;
		try {
			listRepas = repasDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error repasService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listRepas;
	}

}
