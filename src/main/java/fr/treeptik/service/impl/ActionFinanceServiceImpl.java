package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.ActionFinanceDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.ActionFinance;
import fr.treeptik.service.ActionFinanceService;

@Service
public class ActionFinanceServiceImpl implements ActionFinanceService {

	@Inject
	private ActionFinanceDAO actionFinanceDAO;

	private Logger logger = Logger.getLogger(ActionFinanceServiceImpl.class);

	@Override
	public ActionFinance findById(Integer id) throws ServiceException {
		return actionFinanceDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public ActionFinance create(ActionFinance ActionFinance) throws ServiceException {
		logger.info("--CREATE ActionFinanceService --");
		logger.debug("ActionFinance : " + ActionFinance);
		return actionFinanceDAO.save(ActionFinance);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public ActionFinance update(ActionFinance ActionFinance) throws ServiceException {
		logger.info("--UPDATE ActionFinanceService --");
		logger.debug("ActionFinance : " + ActionFinance);
		try {

			ActionFinance = actionFinanceDAO.save(ActionFinance);
		} catch (PersistenceException e) {
			logger.error("Error ActionFinanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return ActionFinance;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE ActionFinanceService -- actionFinanceId : " + id);
		try {
			actionFinanceDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error ActionFinanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<ActionFinance> findAll() throws ServiceException {
		logger.info("--FINDALL ActionFinanceService --");
		List<ActionFinance> listActionFinance;
		try {
			listActionFinance = actionFinanceDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error ActionFinanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listActionFinance;
	}

}
