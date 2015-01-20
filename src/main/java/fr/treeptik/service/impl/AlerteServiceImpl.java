package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
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
		logger.info("--CREATE alerte --");
		logger.debug("alerte : " + alerte);
		return alerteDAO.save(alerte);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Alerte update(Alerte alerte) throws ServiceException {
		logger.info("--UPDATE alerte --");
		logger.debug("alerte : " + alerte);
		return alerteDAO.saveAndFlush(alerte);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Alerte alerte) throws ServiceException {
		logger.info("--DELETE alerte --");
		logger.debug("alerte : " + alerte);
		alerteDAO.delete(alerte);
	}

	@Override
	public List<Alerte> findAll() throws ServiceException {
		logger.info("--FINDALL alerte --");
		return alerteDAO.findAll();
	}

}
