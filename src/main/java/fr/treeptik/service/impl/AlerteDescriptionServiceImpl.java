package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.AlerteDescriptionDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.AlerteDescription;
import fr.treeptik.service.AlerteDescriptionService;

@Service
public class AlerteDescriptionServiceImpl implements AlerteDescriptionService {

	@Inject
	private AlerteDescriptionDAO alerteDescriptionDAO;

	private Logger logger = Logger.getLogger(AlerteDescriptionServiceImpl.class);

	@Override
	public AlerteDescription findById(Integer id) throws ServiceException {
		return alerteDescriptionDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public AlerteDescription create(AlerteDescription alerteDescription) throws ServiceException {
		logger.info("--CREATE AlerteServiceImpl --");
		logger.debug("alerte : " + alerteDescription);
		return alerteDescriptionDAO.save(alerteDescription);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public AlerteDescription update(AlerteDescription alerteDescription) throws ServiceException {
		logger.info("--UPDATE AlerteServiceImpl --");
		logger.debug("alerte : " + alerteDescription);
		return alerteDescriptionDAO.saveAndFlush(alerteDescription);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--remove AlerteServiceImpl -- alerteId : " + id);
		alerteDescriptionDAO.delete(id);
	}

	@Override
	public List<AlerteDescription> findAll() throws ServiceException {
		logger.info("--FINDALL AlerteServiceImpl --");
		return alerteDescriptionDAO.findAll();
	}

	@Override
	public List<AlerteDescription> findAllByClientLogin(String userLogin)
			throws ServiceException {
		logger.info("--findByClient userLogin AlerteServiceImpl -- userLogin : "
				+ userLogin);

		return alerteDescriptionDAO.findAllByClientLogin(userLogin);
	}

	@Override
	public Long countAll() throws ServiceException {
		return alerteDescriptionDAO.count();
	}
	
	@Override
	public Long countAllActives() throws ServiceException {
		return alerteDescriptionDAO.countAllActives();
	}

	@Override
	public Long countAllByClientLogin(String userLogin) throws ServiceException {
		logger.info("--countAllByClientLogin AlerteServiceImpl -- userLogin : "
				+ userLogin);
		return alerteDescriptionDAO.countAllByClientLogin(userLogin);
	}
	
	@Override
	public Long countAllActivesByClientLogin(String userLogin) throws ServiceException {
		logger.info("--countAllActivesByClientLogin userId AlerteServiceImpl -- userLogin : "
				+ userLogin);
		return alerteDescriptionDAO.countAllActivesByClientLogin(userLogin);
	}
	
	@Override
	public List<AlerteDescription> findAlertesActivesByEnregistreurId(Integer enregistreurId) throws ServiceException {
		logger.info("--findAlertesActivesByEnregistreurId AlerteServiceImpl -- enregistreurId : "
				+ enregistreurId);
		return alerteDescriptionDAO.findAlertesActivesByEnregistreurId(enregistreurId);
	}
	
}