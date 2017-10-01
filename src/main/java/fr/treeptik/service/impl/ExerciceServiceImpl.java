package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.ExerciceDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Exercice;
import fr.treeptik.service.ExerciceService;

@Service
public class ExerciceServiceImpl implements ExerciceService {

	@Inject
	private ExerciceDAO exerciceDAO;

	private Logger logger = Logger.getLogger(ExerciceServiceImpl.class);

	@Override
	public Exercice findById(Long id) throws ServiceException {
		return exerciceDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Exercice create(Exercice Exercice) throws ServiceException {
		logger.info("--CREATE ExerciceService --");
		logger.debug("Exercice : " + Exercice);
		return exerciceDAO.save(Exercice);
	}

	// update exercice
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Exercice update(Exercice Exercice) throws ServiceException {
		logger.info("--UPDATE ExerciceService --");
		logger.debug("Exercice : " + Exercice);
		try {

			Exercice = exerciceDAO.save(Exercice);
		} catch (PersistenceException e) {
			logger.error("Error ExerciceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return Exercice;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Long id) throws ServiceException {
		logger.info("--DELETE ExerciceService -- exerciceId : " + id);
		try {
			exerciceDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error ExerciceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<Exercice> findAll() throws ServiceException {
		logger.info("--FINDALL ExerciceService --");
		List<Exercice> listExercice;
		try {
			listExercice = exerciceDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error ExerciceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listExercice;
	}

}
