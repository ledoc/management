package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.AlimentDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Aliment;
import fr.treeptik.service.AlimentService;

@Service
public class AlimentServiceImpl implements AlimentService {

	@Inject
	private AlimentDAO alimentDAO;

	private Logger logger = Logger.getLogger(AlimentServiceImpl.class);

	@Override
	public Aliment findById(Integer id) throws ServiceException {
		return alimentDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Aliment create(Aliment Aliment) throws ServiceException {
		logger.info("--CREATE AlimentService --");
		logger.debug("Aliment : " + Aliment);
		return alimentDAO.save(Aliment);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Aliment update(Aliment Aliment) throws ServiceException {
		logger.info("--UPDATE AlimentService --");
		logger.debug("Aliment : " + Aliment);
		try {

			Aliment = alimentDAO.save(Aliment);
		} catch (PersistenceException e) {
			logger.error("Error AlimentService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return Aliment;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE AlimentService -- alimentId : " + id);
		try {
			alimentDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error AlimentService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<Aliment> findAll() throws ServiceException {
		logger.info("--FINDALL AlimentService --");
		List<Aliment> listAliment;
		try {
			listAliment = alimentDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error AlimentService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listAliment;
	}

}
