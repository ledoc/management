package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Aliment;

public interface AlimentService {

	Aliment findById(Integer id) throws ServiceException;

	Aliment create(Aliment aliment) throws ServiceException;

	Aliment update(Aliment aliment) throws ServiceException;

	List<Aliment> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

}
