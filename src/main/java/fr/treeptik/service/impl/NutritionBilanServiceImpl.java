package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.NutritionBilanDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.NutritionBilan;
import fr.treeptik.service.NutritionBilanService;

@Service
public class NutritionBilanServiceImpl implements NutritionBilanService {

	@Inject
	private NutritionBilanDAO nutritionBilanDAO;

	private Logger logger = Logger.getLogger(NutritionBilanServiceImpl.class);

	@Override
	public NutritionBilan findById(Integer id) throws ServiceException {
		return nutritionBilanDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public NutritionBilan create(NutritionBilan nutritionBilan) throws ServiceException {
		logger.info("--CREATE NutritionBilanService --");
		logger.debug("NutritionBilan : " + nutritionBilan);
		return nutritionBilanDAO.save(nutritionBilan);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public NutritionBilan update(NutritionBilan nutritionBilan) throws ServiceException {
		logger.info("--UPDATE NutritionBilanService --");
		logger.debug("NutritionBilan : " + nutritionBilan);
		try {

			nutritionBilan = nutritionBilanDAO.save(nutritionBilan);
		} catch (PersistenceException e) {
			logger.error("Error NutritionBilanService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return nutritionBilan;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE NutritionBilanService -- nutritionBilanId : " + id);
		try {
			nutritionBilanDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error NutritionBilanService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<NutritionBilan> findAll() throws ServiceException {
		logger.info("--FINDALL NutritionBilanService --");
		List<NutritionBilan> listNutritionBilan;
		try {
			listNutritionBilan = nutritionBilanDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error NutritionBilanService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listNutritionBilan;
	}

}
