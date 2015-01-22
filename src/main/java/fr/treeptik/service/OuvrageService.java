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

		Ouvrage findByIdWithJoinFechMesures(Integer id) throws ServiceException;

		void remove(Integer id) throws ServiceException;

}
