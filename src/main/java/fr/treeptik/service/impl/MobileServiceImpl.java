package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.MobileDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.deveryware.Mobile;
import fr.treeptik.service.MobileService;

@Service
public class MobileServiceImpl implements MobileService {

	@Inject
	private MobileDAO mobileDAO;

	private Logger logger = Logger.getLogger(MobileServiceImpl.class);

	@Override
	public Mobile findById(Integer id) throws ServiceException {
		return mobileDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Mobile create(Mobile mobile) throws ServiceException {
		logger.info("--CREATE mobile --");
		logger.debug("mobile : " + mobile);
		return mobileDAO.save(mobile);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Mobile update(Mobile mobile) throws ServiceException {
		logger.info("--UPDATE mobile --");
		logger.debug("mobile : " + mobile);
		return mobileDAO.saveAndFlush(mobile);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Mobile mobile) throws ServiceException {
		logger.info("--DELETE mobile --");
		logger.debug("mobile : " + mobile);
		mobileDAO.delete(mobile);
	}

	@Override
	public List<Mobile> findAll() throws ServiceException {
		logger.info("--FINDALL mobile --");
		return mobileDAO.findAll();
	}

	@Override
	public Mobile findByMid(String mid) throws ServiceException {
		logger.info("--findByMid mobile --");
		return mobileDAO.findByMid(mid);
	}



}
