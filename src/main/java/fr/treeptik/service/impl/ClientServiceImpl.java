package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.ClientDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Client;
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

}
