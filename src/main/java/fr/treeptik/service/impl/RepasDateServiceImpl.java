package fr.treeptik.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.RepasDateDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.BilanRepas;
import fr.treeptik.model.NutritionBilan;
import fr.treeptik.model.RepasDate;
import fr.treeptik.service.RepasDateService;

@Service
public class RepasDateServiceImpl implements RepasDateService {

	@Inject
	private RepasDateDAO repasDateDAO;

	private Logger logger = Logger.getLogger(RepasDateServiceImpl.class);

	@Override
	public RepasDate findById(Long id) throws ServiceException {
		return repasDateDAO.findOne(id);
	}

	@Override
	public RepasDate findByIdWithListPlat(Long id) throws ServiceException {
		logger.info("--findByIdWithListPlat RepasDateService -- id = " + id);
		RepasDate repasDate = new RepasDate();
		try {
			repasDate = repasDateDAO.findByIdWithListPlat(id);
			
			
			return repasDate;
		} catch (PersistenceException e) {
			logger.error("Error RepasDateService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public RepasDate create(RepasDate repasDate) throws ServiceException {
		logger.info("--CREATE RepasDateService --");
		logger.debug("repasDate : " + repasDate);
		return repasDateDAO.save(repasDate);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public RepasDate update(RepasDate repasDate) throws ServiceException {
		logger.info("--UPDATE RepasDateService -- repasDate " + repasDate);
		logger.debug("repasDate : " + repasDate);
		try {

			repasDate = repasDateDAO.save(repasDate);
		} catch (PersistenceException e) {
			logger.error("Error RepasDateService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return repasDate;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Long id) throws ServiceException {
		logger.info("--DELETE RepasService -- repasId : " + id);
		try {
			repasDateDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error RepasService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<RepasDate> findAll() throws ServiceException {
		logger.info("--FINDALL RepasService --");
		List<RepasDate> listRepasDate;
		try {
			listRepasDate = repasDateDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error repasService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listRepasDate;
	}

	@Override
	public List<RepasDate> findAllWithListPlat() throws ServiceException {
		logger.info("--FINDALL RepasService --");
		List<RepasDate> listRepasDate;
		try {
			listRepasDate = repasDateDAO.findAllWithListPlat();
		} catch (PersistenceException e) {
			logger.error("Error repasService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listRepasDate;
	}

	@Override
	public List<BilanRepas> createBilanByDate() throws ServiceException {
		List<BilanRepas> listBilanRepas = new ArrayList<BilanRepas>();
		List<RepasDate> allRepasDate = findAll();
		List<Date> listDatesOfRepas = listDatesOfRepas();

		for (Date date : listDatesOfRepas) {
			BilanRepas bilanRepas = new BilanRepas();
			bilanRepas.setDate(date);
			Double sommeCalories = 0D;
			Double sommeProteines = 0D;
			Double sommeGlucides = 0D;
			Double sommeLipides = 0D;

			for (RepasDate repasDate : allRepasDate) {
				if (date.equals(repasDate.getDate())) {
					NutritionBilan nutritionBilan = repasDate.getRepas()
							.getNutritionBilan();
					sommeCalories += nutritionBilan.getCalories();
					sommeProteines += nutritionBilan.getProteine();
					sommeGlucides += nutritionBilan.getGlucide();
					sommeLipides += nutritionBilan.getLipide();

				}
			}

			bilanRepas.setSommeCalories(sommeCalories);
			bilanRepas.setSommeProteines(sommeProteines);
			bilanRepas.setSommeGlucides(sommeGlucides);
			bilanRepas.setSommeLipides(sommeLipides);
			listBilanRepas.add(bilanRepas);
		}

		return listBilanRepas;
	}

	private List<Date> listDatesOfRepas() {
		return repasDateDAO.listAllDatesOfRepas();
	}

}
