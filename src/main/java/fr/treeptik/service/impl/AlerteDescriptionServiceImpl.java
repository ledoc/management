package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.AlerteDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Alerte;
import fr.treeptik.service.AlerteService;

@Service
public class AlerteServiceImpl implements AlerteService {

	@Inject
	private AlerteDAO alerteDAO;

	private Logger logger = Logger.getLogger(AlerteServiceImpl.class);

	@Override
	public Alerte findById(Integer id) throws ServiceException {
		return alerteDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Alerte create(Alerte alerte) throws ServiceException {
		logger.info("--CREATE AlerteServiceImpl --");
		logger.debug("alerte : " + alerte);
		return alerteDAO.save(alerte);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Alerte update(Alerte alerte) throws ServiceException {
		logger.info("--UPDATE AlerteServiceImpl --");
		logger.debug("alerte : " + alerte);
		return alerteDAO.saveAndFlush(alerte);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--remove AlerteServiceImpl -- alerteId : " + id);
		alerteDAO.delete(id);
	}

	@Override
	public List<Alerte> findAll() throws ServiceException {
		logger.info("--FINDALL AlerteServiceImpl --");
		return alerteDAO.findAll();
	}

	@Override
	public List<Alerte> findByClientLogin(String userLogin)
			throws ServiceException {
		logger.info("--findByClient userLogin AlerteServiceImpl -- userLogin : "
				+ userLogin);

		return alerteDAO.findByClientLogin(userLogin);
	}

	@Override
	public Long countAllAlertes() throws ServiceException {
		return alerteDAO.countAllAlertes();
	}
	
	@Override
	public Long countAlertesActives() throws ServiceException {
		return alerteDAO.countAlertesActives();
	}

	@Override
	public Long countAllAlertesByClientLogin(String userLogin) throws ServiceException {
		logger.info("--findByClient userId AlerteServiceImpl -- userLogin : "
				+ userLogin);
		return alerteDAO.countAllAlertesByClientLogin(userLogin);
	}
	
	@Override
	public Long countAlertesActivesByClientLogin(String userLogin) throws ServiceException {
		logger.info("--countAlertesActivesByClientLogin userId AlerteServiceImpl -- userLogin : "
				+ userLogin);
		return alerteDAO.countAlertesActivesByClientLogin(userLogin);
	}
	
	@Override
	public List<Alerte> findAlertesActivesByEnregistreurId(Integer enregistreurId) throws ServiceException {
		logger.info("--findAlertesActivesByEnregistreurId AlerteServiceImpl -- enregistreurId : "
				+ enregistreurId);
		return alerteDAO.findAlertesActivesByEnregistreurId(enregistreurId);
	}
	
	@Override
	public List<Alerte> findAllAlertesEmises() throws ServiceException {
		logger.info("--findAllAlertesEmises AlerteServiceImpl");
		return alerteDAO.findAllAlertesEmises();
	}
	
	@Override
	public List<Alerte> findAlertesEmisesByClientLogin(String userLogin) throws ServiceException {
		logger.info("--findAlertesEmisesByClientLogin AlerteServiceImpl -- userLogin : "
				+ userLogin);
		return alerteDAO.findAlertesEmisesByClientLogin(userLogin);
	}
	
	
}
