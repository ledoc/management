package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.AlerteEmiseDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.AlerteEmise;
import fr.treeptik.service.AlerteEmiseService;

@Service
public class AlerteEmiseServiceImpl implements AlerteEmiseService {

	@Inject
	private AlerteEmiseDAO alerteEmiseDAO;

	private Logger logger = Logger.getLogger(AlerteEmiseServiceImpl.class);

	@Override
	public AlerteEmise findById(Integer id) throws ServiceException {
		return alerteEmiseDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public AlerteEmise create(AlerteEmise alerteEmise) throws ServiceException {
		logger.info("--create AlerteEmiseServiceImpl --");
		logger.debug("alerte : " + alerteEmise);
		return alerteEmiseDAO.save(alerteEmise);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public AlerteEmise update(AlerteEmise alerteEmise) throws ServiceException {
		logger.info("--update AlerteEmiseServiceImpl -- alerteEmise : " + alerteEmise);
		return alerteEmiseDAO.saveAndFlush(alerteEmise);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--remove AlerteEmiseServiceImpl -- alerteId : " + id);
		alerteEmiseDAO.delete(id);
	}

	@Override
	public List<AlerteEmise> findAll() throws ServiceException {
		logger.info("--findAll AlerteEmiseServiceImpl --");
		return alerteEmiseDAO.findAll();
	}

	@Override
	public List<AlerteEmise> findAllByClientLogin(String userLogin)
			throws ServiceException {
		logger.info("--findAllByClientLogin AlerteServiceImpl -- userLogin : "
				+ userLogin);

		return alerteEmiseDAO.findAllByClientLogin(userLogin);
	}

	@Override
	public void acquittementAlerte(Integer id) throws ServiceException {
		logger.info("--acquittementAlerte AlerteServiceImpl -- alerteEmiseId : "
				+ id);
		AlerteEmise alerteEmise = this.findById(id);
		alerteEmise.setAcquittement(true);
		this.update(alerteEmise);
	}
}
