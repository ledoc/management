package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.ClientDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Client;
import fr.treeptik.model.Role;
import fr.treeptik.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Inject
	private ClientDAO clientDAO;

	private Logger logger = Logger.getLogger(ClientServiceImpl.class);

	@Override
	public Client findById(Integer id) throws ServiceException {
		return clientDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Client create(Client client) throws ServiceException {
		logger.info("--CREATE ClientServiceImpl --");

		client.setRole(Role.CLIENT);
		client = clientDAO.save(client);
		client.setIdentifiant("ID-"+client.getId());
		logger.debug("client : " + client);
		return client;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Client update(Client client) throws ServiceException {
		logger.info("--UPDATE ClientServiceImpl -- client : " + client);
		return clientDAO.saveAndFlush(client);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE ClientServiceImpl -- clientId : " + id);
		clientDAO.delete(id);
	}

	@Override
	public List<Client> findAll() throws ServiceException {
		logger.info("--FINDALL ClientServiceImpl --");
		return clientDAO.findAll();
	}

	/**
	 * Méthode spécifique pour réupérer les mesures associées à l'ouvrage dû au
	 * FetchType.Lazy
	 */
	@Override
	public Client findByIdWithJoinFetchEtablissements(Integer id)
			throws ServiceException {
		logger.info("--findByIdWithJoinFetchEtablissements ClientServiceImpl -- id : " + id);
		Client client = clientDAO.findByIdWithJoinFetchEtablissements(id);
		return client;
	}

	@Override
	public Client findClientByOuvrageId(Integer ouvrageId)
			throws ServiceException {
		logger.info("--findClientByOuvrageId ClientServiceImpl -- ouvrageId " + ouvrageId);
		return clientDAO.findClientByOuvrageId(ouvrageId);
	}

	
	/**
	 * Méthode spécifique pour récupérer les documents associées à l'ouvrage dû au
	 * FetchType.Lazy
	 */
	@Override
	public Client findByIdWithJoinFetchDocuments(Integer id) throws ServiceException {
		logger.info("--findByIdWithJoinFetchDocuments ClientServiceImpl -- id : " + id);
		Client client = clientDAO.findByIdWithJoinFetchDocuments(id);
		return client;
	}
	
	@Override
	public Client findByLogin(String userLogin) throws ServiceException {
		logger.info("--findByLogin ClientServiceImpl --");
		return clientDAO.findByLogin(userLogin);
	}
}