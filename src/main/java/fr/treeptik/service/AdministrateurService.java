package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Administrateur;

public interface AdministrateurService {

		Administrateur findById(Integer id) throws ServiceException;
		
		Administrateur create(Administrateur administrateur)
				throws ServiceException;

		Administrateur update(Administrateur administrateur) throws ServiceException;
		
		void remove(Administrateur administrateur)
				throws ServiceException;

		List<Administrateur> findAll() throws ServiceException;

		void remove(Integer id) throws ServiceException;

		List<String> getAuditLog() throws ServiceException;

}
