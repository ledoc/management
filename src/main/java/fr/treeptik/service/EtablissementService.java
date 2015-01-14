package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Etablissement;

public interface EtablissementService {

		Etablissement findById(Integer id) throws ServiceException;
		
		Etablissement create(Etablissement etablissement)
				throws ServiceException;

		Etablissement update(Etablissement etablissement) throws ServiceException;
		
		Etablissement remove(Etablissement etablissement)
				throws ServiceException;

		List<Etablissement> findAll() throws ServiceException;

}
