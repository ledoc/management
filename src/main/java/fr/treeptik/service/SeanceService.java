package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Seance;

public interface SeanceService {

	Seance findById(Integer id) throws ServiceException;

	Seance create(Seance seance) throws ServiceException;

	Seance update(Seance seance) throws ServiceException;

	List<Seance> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

}
