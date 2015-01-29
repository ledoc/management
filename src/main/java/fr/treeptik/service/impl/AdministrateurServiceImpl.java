package fr.treeptik.service.impl;

import java.util.List;

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
	public Administrateur create(Administrateur administrateur) throws ServiceException {
		logger.info("--CREATE administrateur --");
		
		administrateur.setRole(Role.ADMIN);
		
		logger.debug("administrateur : " + administrateur);
		return administrateurDAO.save(administrateur);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Administrateur update(Administrateur administrateur) throws ServiceException {
		logger.info("--UPDATE administrateur --");
		logger.debug("administrateur : " + administrateur);
		return administrateurDAO.saveAndFlush(administrateur);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Administrateur administrateur) throws ServiceException {
		logger.info("--DELETE administrateur --");
		logger.debug("administrateur : " + administrateur);
		administrateurDAO.delete(administrateur);
	}
	
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE administrateur --");
		logger.debug("administrateurId : " + id);
		administrateurDAO.delete(id);
	}

	@Override
	public List<Administrateur> findAll() throws ServiceException {
		logger.info("--FINDALL administrateur --");
		return administrateurDAO.findAll();
	}

}
