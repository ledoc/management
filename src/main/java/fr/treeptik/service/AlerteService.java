package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Alerte;

public interface AlerteService {

		Alerte findById(Integer id) throws ServiceException;
		
		Alerte create(Alerte alerte)
				throws ServiceException;

		Alerte update(Alerte alerte) throws ServiceException;
		
		Alerte remove(Alerte alerte)
				throws ServiceException;

		List<Alerte> findAll() throws ServiceException;

}
