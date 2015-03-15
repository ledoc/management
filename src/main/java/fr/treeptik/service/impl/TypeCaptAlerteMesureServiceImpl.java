package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.TypeCaptAlerteMesureDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.TypeCaptAlerteMesure;
import fr.treeptik.service.TypeCaptAlerteMesureService;

@Service
public class TypeCaptAlerteMesureServiceImpl implements TypeCaptAlerteMesureService {

	@Inject
	private TypeCaptAlerteMesureDAO typeCaptAlerteMesureDAO;

	private Logger logger = Logger.getLogger(TypeCaptAlerteMesureServiceImpl.class);

	@Override
	public TypeCaptAlerteMesure findById(Integer id) throws ServiceException {
		logger.info("--findById TypeCaptAlerteMesureServiceImpl -- id : " + id);
		return typeCaptAlerteMesureDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public TypeCaptAlerteMesure create(TypeCaptAlerteMesure typeCaptAlerteMesure) throws ServiceException {
		logger.info("--create TypeCaptAlerteMesureServiceImpl -- typeCaptAlerteMesure : "
				+ typeCaptAlerteMesure);
		return typeCaptAlerteMesureDAO.save(typeCaptAlerteMesure);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public TypeCaptAlerteMesure update(TypeCaptAlerteMesure typeCaptAlerteMesure) throws ServiceException {
		logger.info("--update TypeCaptAlerteMesureServiceImpl -- typeCaptAlerteMesure : "
				+ typeCaptAlerteMesure);
		return typeCaptAlerteMesureDAO.saveAndFlush(typeCaptAlerteMesure);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--remove TypeCaptAlerteMesureServiceImpl -- typeCaptAlerteMesureId : " + id);
		typeCaptAlerteMesureDAO.delete(id);
	}

	@Override
	public List<TypeCaptAlerteMesure> findAll() throws ServiceException {
		logger.info("--findAll TypeCaptAlerteMesureServiceImpl --");
		return typeCaptAlerteMesureDAO.findAll();
	}

	@Override
	public TypeCaptAlerteMesure findByNom(String nom) throws ServiceException {
		logger.info("--findByNom TypeCaptAlerteMesureServiceImpl -- nom : " + nom);
		return typeCaptAlerteMesureDAO.findByNom(nom);
	}

}
