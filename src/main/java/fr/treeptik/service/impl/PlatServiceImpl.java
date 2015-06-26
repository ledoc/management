package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.PlatDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Plat;
import fr.treeptik.service.PlatService;

@Service
public class PlatServiceImpl implements PlatService {

	@Inject
	private PlatDAO platDAO;

	private Logger logger = Logger.getLogger(PlatServiceImpl.class);

	@Override
	public Plat findById(Integer id) throws ServiceException {
		return platDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Plat create(Plat plat) throws ServiceException {
		logger.info("--CREATE PlatService --");
		logger.debug("plat : " + plat);
		return platDAO.save(plat);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Plat update(Plat plat) throws ServiceException {
		logger.info("--UPDATE PlatService --");
		logger.debug("plat : " + plat);
		try {

			plat = platDAO.save(plat);
		} catch (PersistenceException e) {
			logger.error("Error PlatService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		return plat;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE PlatService -- platId : " + id);
		try {
			platDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error PlatService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

	}

	@Override
	public List<Plat> findAll() throws ServiceException {
		logger.info("--FINDALL PlatService --");
		List<Plat> plats;
		try {
			plats = platDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error PlatService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return plats;
	}

}
