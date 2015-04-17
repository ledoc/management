package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Repas;

public interface RepasService {

	Repas findById(Integer id) throws ServiceException;

	Repas create(Repas repas) throws ServiceException;

	Repas update(Repas repas) throws ServiceException;

	List<Repas> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

}
