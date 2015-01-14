package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;

public interface EnregistreurService {

		Enregistreur findById(Integer id) throws ServiceException;
		
		Enregistreur create(Enregistreur enregistreur)
				throws ServiceException;

		Enregistreur update(Enregistreur enregistreur) throws ServiceException;
		
		Enregistreur remove(Enregistreur enregistreur)
				throws ServiceException;

		List<Enregistreur> findAll() throws ServiceException;

}
