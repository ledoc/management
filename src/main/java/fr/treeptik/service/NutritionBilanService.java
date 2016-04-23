package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.NutritionBilan;

public interface NutritionBilanService {

	NutritionBilan findById(Integer id) throws ServiceException;

	NutritionBilan create(NutritionBilan nutritionBilan) throws ServiceException;

	NutritionBilan update(NutritionBilan nutritionBilan) throws ServiceException;

	List<NutritionBilan> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

}
