package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.AlerteDescription;

public interface AlerteDescriptionService {

	AlerteDescription findById(Integer id) throws ServiceException;

	AlerteDescription create(AlerteDescription alerteDescription) throws ServiceException;

	AlerteDescription update(AlerteDescription alerteDescription) throws ServiceException;

	List<AlerteDescription> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

	Long countAll() throws ServiceException;

	Long countAllActives() throws ServiceException;

	Long countAllByClientLogin(String userLogin) throws ServiceException;

	Long countAllActivesByClientLogin(String userLogin)
			throws ServiceException;

	List<AlerteDescription> findAllAlertesActivesByClientLogin(String userLogin)
			throws ServiceException;

	List<AlerteDescription> findAllByClientLogin(String userLogin)
			throws ServiceException;

	List<AlerteDescription> findAlertesActivesByCapteurId(Integer capteurId)
			throws ServiceException;

}
