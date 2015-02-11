package fr.treeptik.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.AdministrateurDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Administrateur;
import fr.treeptik.model.Role;
import fr.treeptik.service.AdministrateurService;

@Service
public class AdministrateurServiceImpl implements AdministrateurService {

	@Inject
	private AdministrateurDAO administrateurDAO;

	private Logger logger = Logger.getLogger(AdministrateurServiceImpl.class);

	@Override
	public Administrateur findById(Integer id) throws ServiceException {
		return administrateurDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Administrateur create(Administrateur administrateur)
			throws ServiceException {
		logger.info("--CREATE AdministrateurServiceImpl --");
		
		administrateur.setRole(Role.ADMIN);
		administrateur = administrateurDAO.save(administrateur);
		administrateur.setIdentifiant("ID-"+administrateur.getId());
		logger.debug("administrateur : " + administrateur);
		
		return administrateur;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Administrateur update(Administrateur administrateur)
			throws ServiceException {
		logger.info("--UPDATE AdministrateurServiceImpl --");
		logger.debug("administrateur : " + administrateur);
		return administrateurDAO.saveAndFlush(administrateur);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Administrateur administrateur) throws ServiceException {
		logger.info("--DELETE AdministrateurServiceImpl --");
		logger.debug("administrateur : " + administrateur);
		administrateurDAO.delete(administrateur);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE AdministrateurServiceImpl --");
		logger.debug("administrateurId : " + id);
		administrateurDAO.delete(id);
	}

	@Override
	public List<Administrateur> findAll() throws ServiceException {
		logger.info("--FINDALL AdministrateurServiceImpl --");
		return administrateurDAO.findAll();
	}

	@Override
	public List<String> getAuditLog() throws ServiceException {
	
		ArrayList<String> logs = new ArrayList<String>();
		Stream<String> lines = null;
		try {
			
			lines = Files.lines(Paths.get(System.getProperty("rootPath"), "..", "..", "logs", "audit-msg.log"));
			lines.forEach(s -> logs.add(s));
			lines.close();	

			// Reverse pour afficher les dernière actions en premier
			Collections.reverse(logs);
			 
		} catch (IOException e) {
			logger.error("Erreur read audit log " + e.getMessage());
			throw new ServiceException(e.getMessage(), e);
		}
		
		return logs;
		
	}

	@Override
	public Administrateur findByLogin(String userLogin) throws ServiceException {
		logger.info("--findByLogin AdministrateurServiceImpl --");
		return administrateurDAO.findByLogin(userLogin);
	}
}
