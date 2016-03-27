package fr.treeptik.service;

import java.util.List;

import fr.treeptik.dto.PointCamenbertDTO;
import fr.treeptik.dto.PointGraphDTO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Bilan;
import fr.treeptik.model.Finance;

public interface FinanceService {

	Finance findById(Integer id) throws ServiceException;

	Finance create(Finance finance) throws ServiceException;

	Finance update(Finance finance) throws ServiceException;

	List<Finance> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

	Double countMonthSum() throws ServiceException;

	Double countMonthAverage() throws ServiceException;

	List<String> listAllCategories() throws ServiceException;

	List<Finance> listAllByCategorie(String categorie) throws ServiceException;
	
	Double sumByCategorie(String categorie) throws ServiceException;

	List<Bilan> listAllBilans() throws ServiceException;

	PointGraphDTO transformFinanceInPoint(Finance item)
			throws ServiceException;
	
	PointCamenbertDTO transformBilanInCamenbertPoint(Bilan item)
			throws ServiceException;
	
	PointGraphDTO transformBilanInGraphPoint(Bilan item)
			throws ServiceException;
	
	
	Double countBeforeMonthAverage() throws ServiceException;

	Double countBeforeMonthSum() throws ServiceException;

	Double selectLastTotal() throws ServiceException;
	
}
