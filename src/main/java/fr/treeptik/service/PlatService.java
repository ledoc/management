package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Plat;

public interface PlatService {

	Plat findById(Integer id) throws ServiceException;

	Plat create(Plat plat) throws ServiceException;

	Plat update(Plat plat) throws ServiceException;

	List<Plat> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

}
