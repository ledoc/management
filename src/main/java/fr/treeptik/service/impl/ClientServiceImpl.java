package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
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
		logger.info("--CREATE client --");

		client.setRole(Role.CLIENT);
		client = this.setIdentifiantWithCheck(client);

		logger.debug("client : " + client);
		return clientDAO.save(client);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Client update(Client client) throws ServiceException {
		logger.info("--UPDATE client --");
		logger.debug("client : " + client);
		return clientDAO.saveAndFlush(client);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Client client) throws ServiceException {
		logger.info("--DELETE client --");
		logger.debug("client : " + client);
		clientDAO.delete(client);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE client --");
		logger.debug("clientId : " + id);
		clientDAO.delete(id);
	}

	@Override
	public List<Client> findAll() throws ServiceException {
		logger.info("--FINDALL client --");
		return clientDAO.findAll();
	}

	/**
	 * Méthode spécifique pour récupérer les mesures associées à l'ouvrage dû au
	 * FetchType.Lazy
	 */
	@Override
	public Client findByIdWithJoinFetchEtablissements(Integer id)
			throws ServiceException {
		logger.info("--findByIdWithJoinFetchEtablissements client --");
		logger.info("id : " + id);
		Client client = clientDAO.findByIdWithJoinFetchEtablissements(id);
		return client;
	}

	@Override
	public Client setIdentifiantWithCheck(Client client)
			throws ServiceException {
		logger.info("--setIdentifiantWithCheck ClientServiceImpl --");

		int i = 1;
		List<Client> allClients = this.findAll();
		client.setIdentifiant(client.getNom() + i);
		for (Client client2 : allClients) {
			if (client.getNom().equalsIgnoreCase(client2.getNom())) {
				client.setIdentifiant(client.getNom() + i);
				if (client.getIdentifiant().equalsIgnoreCase(
						client2.getIdentifiant())) {
					i++;
					client.setIdentifiant(client.getNom() + i);
					logger.info(client.getIdentifiant());
				}
			}
		}
		logger.debug("Client : " + client);
		return client;
	}

	@Override
	public Client findClientByOuvrageId(Integer ouvrageId)
			throws ServiceException {
		logger.info("--findClientByOuvrageId ClientServiceImpl -- ouvrageId " + ouvrageId);
		return clientDAO.findClientByOuvrageId(ouvrageId);
	}

}
