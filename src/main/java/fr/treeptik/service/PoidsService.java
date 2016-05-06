package fr.treeptik.service;

import java.util.List;

import fr.treeptik.dto.PointCamenbertDTO;
import fr.treeptik.dto.PointGraphDTO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Poids;

public interface PoidsService {

	Poids findById(Long id) throws ServiceException;

	Poids create(Poids poids) throws ServiceException;

	Poids update(Poids poids) throws ServiceException;

	List<Poids> findAll() throws ServiceException;

	void remove(Long id) throws ServiceException;

	PointGraphDTO transformPoidsInPoint(Poids poids) throws ServiceException;

}
