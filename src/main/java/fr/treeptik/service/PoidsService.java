package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Poids;
import fr.treeptik.model.PointGraphDTO;

public interface PoidsService {

	Poids findById(Integer id) throws ServiceException;

	Poids create(Poids poids) throws ServiceException;

	Poids update(Poids poids) throws ServiceException;

	List<Poids> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

	PointGraphDTO transformPoidsInPoint(Poids poids) throws ServiceException;

}
