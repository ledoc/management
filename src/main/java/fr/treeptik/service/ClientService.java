package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Client;

public interface ClientService {

	Client findById(Integer id) throws ServiceException;

	Client create(Client client) throws ServiceException;

	Client update(Client client) throws ServiceException;

	List<Client> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

	Client findByIdWithJoinFetchEtablissements(Integer id)
			throws ServiceException;

	Client findClientByOuvrageId(Integer ouvrageId) throws ServiceException;

	Client findByIdWithJoinFetchDocuments(Integer id) throws ServiceException;

	Client findByLogin(String userLogin) throws ServiceException;

}
