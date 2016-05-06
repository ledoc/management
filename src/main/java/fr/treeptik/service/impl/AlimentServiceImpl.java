package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.AlimentDAO;
import fr.treeptik.dao.NutritionBilanDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Aliment;
import fr.treeptik.model.NutritionBilan;
import fr.treeptik.service.AlimentService;

@Service
public class AlimentServiceImpl implements AlimentService {

	@Inject
	private AlimentDAO alimentDAO;
	
	@Inject
	private NutritionBilanDAO nutritionBilanDAO;

	private Logger logger = Logger.getLogger(AlimentServiceImpl.class);

	@Override
	public Aliment findById(Long id) throws ServiceException {
		return alimentDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Aliment create(Aliment aliment) throws ServiceException {
		aliment = buildNutritionBilanForAliment(aliment);
System.out.println(aliment);
		logger.info("--CREATE AlimentService --");
		logger.debug("Aliment : " + aliment);
		aliment = alimentDAO.save(aliment);
		
		return aliment;
	}

	public Aliment buildNutritionBilanForAliment(Aliment aliment) {

		NutritionBilan nutritionBilan = new NutritionBilan(
				adjustToUnity(aliment, aliment.getProteine()),
				adjustToUnity(aliment, aliment.getLipide()),
				adjustToUnity(aliment, aliment.getGlucide()),
				adjustToUnity(aliment, aliment.getBcaa()),
				adjustToUnity(aliment, aliment.getCalories()));
		nutritionBilan = nutritionBilanDAO.save(nutritionBilan);
		aliment.setNutritionBilan(nutritionBilan);

		return aliment;
	}

	private Float adjustToUnity(Aliment aliment, Float value) {
		if(value == null) {
			value = 0F;
		}
		value = value / aliment.getQuantite();
		return value;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Aliment update(Aliment aliment) throws ServiceException {
		logger.info("--UPDATE AlimentService --");
		logger.debug("Aliment : " + aliment);
		try {

			aliment = alimentDAO.save(aliment);
		} catch (PersistenceException e) {
			logger.error("Error AlimentService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return aliment;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Long id) throws ServiceException {
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
