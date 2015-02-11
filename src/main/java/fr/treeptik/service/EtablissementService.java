package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Ouvrage;

public interface EtablissementService {

	Etablissement findById(Integer id) throws ServiceException;

	Etablissement create(Etablissement etablissement) throws ServiceException;

	Etablissement update(Etablissement etablissement) throws ServiceException;

	List<Etablissement> findAll() throws ServiceException;

	List<Etablissement> findFreeEtablissements() throws ServiceException;

	void remove(Integer id) throws ServiceException;

	Etablissement findByIdWithJoinFetchClients(Integer id) throws ServiceException;
	
	Etablissement findByIdWithJoinFetchSites(Integer id)
			throws ServiceException;	
	
//	Etablissement findByIdWithJoinFetchClientsAndSites(Integer id)
//			throws ServiceException;

	List<Ouvrage> findAllOuvragesOfEtablissement(Integer id)
			throws ServiceException;

	List<Etablissement> findByClientLogin(String login) throws ServiceException;
}
