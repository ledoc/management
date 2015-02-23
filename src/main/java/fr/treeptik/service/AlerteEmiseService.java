package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.AlerteEmise;

public interface AlerteEmiseService {

	AlerteEmise findById(Integer id) throws ServiceException;

	AlerteEmise create(AlerteEmise alerteEmise) throws ServiceException;

	AlerteEmise update(AlerteEmise alerteEmise) throws ServiceException;

	void remove(Integer id) throws ServiceException;

	List<AlerteEmise> findAll() throws ServiceException;
	
	List<AlerteEmise> findAllByClientLogin(String userLogin)
			throws ServiceException;

	void acquittementAlerte(Integer id) throws ServiceException;

	AlerteEmise findLastAlerteEmiseByCodeAlerte(String codeAlerte)
			throws ServiceException;

	void scheduledAllAlerteAcquittement() throws ServiceException;

}
