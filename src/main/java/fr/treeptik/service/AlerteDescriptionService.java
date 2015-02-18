package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Alerte;

public interface AlerteService {

	Alerte findById(Integer id) throws ServiceException;

	Alerte create(Alerte alerte) throws ServiceException;

	Alerte update(Alerte alerte) throws ServiceException;

	List<Alerte> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

	List<Alerte> findByClientLogin(String userLogin) throws ServiceException;
	
	Long countAlertesActives() throws ServiceException;

	Long countAllAlertesByClientLogin(String userLogin) throws ServiceException;

	Long countAlertesActivesByClientLogin(String userLogin)
			throws ServiceException;

	List<Alerte> findAlertesActivesByEnregistreurId(Integer enregistreurId)
			throws ServiceException;

	Long countAllAlertes() throws ServiceException;

	List<Alerte> findAllAlertesEmises() throws ServiceException;

	List<Alerte> findAlertesEmisesByClientLogin(String userLogin)
			throws ServiceException;

}
