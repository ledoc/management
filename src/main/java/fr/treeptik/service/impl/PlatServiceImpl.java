package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.AlimentDAO;
import fr.treeptik.dao.NutritionBilanDAO;
import fr.treeptik.dao.PlatDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Aliment;
import fr.treeptik.model.NutritionBilan;
import fr.treeptik.model.Plat;
import fr.treeptik.service.PlatService;

@Service
public class PlatServiceImpl implements PlatService {

	@Inject
	private AlimentDAO alimentDAO;
	@Inject
	private PlatDAO platDAO;

	@Inject
	private NutritionBilanDAO nutritionBilanDAO;

	private Logger logger = Logger.getLogger(PlatServiceImpl.class);

	@Override
	public Plat findById(Long id) throws ServiceException {
		return platDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Plat create(Plat plat) throws ServiceException {
		logger.info("--CREATE PlatService --");
		logger.debug("plat : " + plat);

		plat = buildNutrionBilanForPlat(plat);

		return platDAO.save(plat);
	}

	private Plat buildNutrionBilanForPlat(Plat plat) {
		Aliment aliment = plat.getAliment();
		aliment = alimentDAO.findOne(aliment.getId());
		System.out.println(aliment);
		NutritionBilan nutritionBilanAliment = aliment.getNutritionBilan();
		nutritionBilanAliment = nutritionBilanDAO.findOne(nutritionBilanAliment
				.getId());
		NutritionBilan nutritionBilan = new NutritionBilan(adjustToQuantity(
				plat, nutritionBilanAliment.getProteine()), adjustToQuantity(
				plat, nutritionBilanAliment.getLipide()), adjustToQuantity(
				plat, nutritionBilanAliment.getGlucide()), adjustToQuantity(
				plat, nutritionBilanAliment.getBcaa()), adjustToQuantity(plat,
				nutritionBilanAliment.getCalories()));

		nutritionBilan = nutritionBilanDAO.save(nutritionBilan);
		plat.setNutritionBilan(nutritionBilan);

		nutritionBilanDAO.save(nutritionBilan);

		return plat;
	}

	private Float adjustToQuantity(Plat plat, Float value) {
		value = value * plat.getQuantite();
		return value;
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
	public void remove(Long id) throws ServiceException {
		logger.info("--DELETE PlatService -- platId : " + id);
		try {
			platDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error PlatService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

	}

	@Override
	@Cacheable("default")
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
