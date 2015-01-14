package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.TrameDW;

public interface TrameDWService {

		TrameDW findById(Integer id) throws ServiceException;
		
		TrameDW create(TrameDW trameDW)
				throws ServiceException;

		TrameDW update(TrameDW trameDW) throws ServiceException;
		
		TrameDW remove(TrameDW trameDW)
				throws ServiceException;

		List<TrameDW> findAll() throws ServiceException;

}
