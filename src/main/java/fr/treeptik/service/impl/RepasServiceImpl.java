package fr.treeptik.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.NutritionBilanDAO;
import fr.treeptik.dao.RepasDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.NutritionBilan;
import fr.treeptik.model.Plat;
import fr.treeptik.model.Repas;
import fr.treeptik.service.RepasService;

@Service
public class RepasServiceImpl implements RepasService {

	@Inject
	private RepasDAO repasDAO;
	@Inject
	private NutritionBilanDAO nutritionBilanDAO;

	private Logger logger = Logger.getLogger(RepasServiceImpl.class);

	@Override
	public Repas findById(Long id) throws ServiceException {
		return repasDAO.findOne(id);
	}

	@Override
	public Repas findByIdWithListPlat(Long id) throws ServiceException {
		return repasDAO.findByIdWithListPlat(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Repas create(Repas repas) throws ServiceException {
		logger.info("--CREATE RepasService --");
		logger.debug("repas : " + repas);
		if (repas.getListPlats() != null && !repas.getListPlats().isEmpty()) {
			repas = buildNutrionBilanForRepas(repas);
		}

		return repasDAO.save(repas);
	}

	private Repas buildNutrionBilanForRepas(Repas repas) {
		List<Plat> plats = repas.getListPlats();
		NutritionBilan nutritionBilan = new NutritionBilan();
		Float proteine = 0F;
		Float lipides = 0F;
		Float glucides = 0F;
		Float calories = 0F;
		Float bcaa = 0F;
		for (Plat plat : plats) {
			NutritionBilan nutritionBilanPlat = plat.getNutritionBilan();
			proteine += nutritionBilanPlat.getProteine();
			lipides += nutritionBilanPlat.getLipide();
			glucides += nutritionBilanPlat.getGlucide();
			calories += nutritionBilanPlat.getCalories();
			bcaa += nutritionBilanPlat.getBcaa();
		}
		nutritionBilan.setProteine(proteine);
		nutritionBilan.setLipide(lipides);
		nutritionBilan.setGlucide(glucides);
		nutritionBilan.setCalories(calories);
		nutritionBilan.setBcaa(bcaa);

		nutritionBilanDAO.save(nutritionBilan);
		repas.setNutritionBilan(nutritionBilan);
		return repas;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Repas update(Repas repas) throws ServiceException {
		logger.info("--UPDATE RepasService -- repas " + repas);
		logger.debug("repas : " + repas);
		try {
			if (repas.getListPlats() != null && !repas.getListPlats().isEmpty()) {
				repas = buildNutrionBilanForRepas(repas);
			}

			repas = repasDAO.save(repas);
		} catch (PersistenceException e) {
			logger.error("Error RepasService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return repas;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Long id) throws ServiceException {
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

	@Override
	public List<Repas> findAllWithListPlat() throws ServiceException {
		logger.info("--FINDALL RepasService --");
		List<Repas> listRepas;
		try {
			listRepas = repasDAO.findAllWithListPlat();
		} catch (PersistenceException e) {
			logger.error("Error repasService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listRepas;
	}

	@Override
	public Boolean checkRepasExist(List<Plat> listPlats)
			throws ServiceException {
		logger.info("--checkRepasExist RepasService -- listplats = "+ listPlats);
		
		List<Long> listPlatsId = new ArrayList<Long>();
		
		for (Plat plat : listPlats) {
		listPlatsId.add(plat.getId());	
		}
		Long count = repasDAO.countRepasWithListPlats(listPlatsId);
		if (count != 0L) {
			return true;
		} else {
			return false;

		}
	}

}
