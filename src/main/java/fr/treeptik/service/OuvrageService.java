package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Ouvrage;

public interface OuvrageService {

		Ouvrage findById(Integer id) throws ServiceException;
		
		Ouvrage create(Ouvrage ouvrage)
				throws ServiceException;

		Ouvrage update(Ouvrage ouvrage) throws ServiceException;
		
		void remove(Ouvrage ouvrage)
				throws ServiceException;

		List<Ouvrage> findAll() throws ServiceException;

		void remove(Integer id) throws ServiceException;

		Ouvrage findByIdWithJoinFetchMesures(Integer id) throws ServiceException;

		Ouvrage findByIdWithJoinFetchDocuments(Integer id) throws ServiceException;

		Ouvrage findByIdWithJoinFetchEnregistreurs(Integer id) throws ServiceException;

		List<Ouvrage> findByClientLogin(String userLogin) throws ServiceException;
		
		List<Ouvrage> findByClientId(Integer userId) throws ServiceException;

		List<Ouvrage> findFreeOuvrages() throws ServiceException;

}
