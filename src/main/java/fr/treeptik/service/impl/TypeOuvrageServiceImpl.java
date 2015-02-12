package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.TypeOuvrageDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.TypeOuvrage;
import fr.treeptik.service.TypeOuvrageService;

@Service
public class TypeOuvrageServiceImpl implements TypeOuvrageService {

	@Inject
	private TypeOuvrageDAO typeOuvrageDAO;

	private Logger logger = Logger.getLogger(TypeOuvrageServiceImpl.class);

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public TypeOuvrage findById(Integer id) throws ServiceException {
		logger.info("--findById TypeOuvrageServiceImpl -- id : " + id);
		return typeOuvrageDAO.findOne(id);
	}

	@Override
	public TypeOuvrage create(TypeOuvrage typeOuvrage) throws ServiceException {
		logger.info("--create TypeOuvrageServiceImpl -- typeOuvrage : "
				+ typeOuvrage);
		return typeOuvrageDAO.save(typeOuvrage);
	}

	@Override
	public TypeOuvrage update(TypeOuvrage typeOuvrage) throws ServiceException {
		logger.info("--update TypeOuvrageServiceImpl -- typeOuvrage : "
				+ typeOuvrage);
		return typeOuvrageDAO.saveAndFlush(typeOuvrage);
	}

	@Override
	public void remove(Integer id) throws ServiceException {
		logger.info("--remove TypeOuvrageServiceImpl -- typeOuvrageId : " + id);
		typeOuvrageDAO.delete(id);
	}

	@Override
	public List<TypeOuvrage> findAll() throws ServiceException {
		logger.info("--findAll TypeOuvrageServiceImpl --");
		return typeOuvrageDAO.findAll();
	}

	@Override
	public TypeOuvrage findByNom(String nom) throws ServiceException {
		logger.info("--findByNom TypeOuvrageServiceImpl -- nom : " + nom);
		return typeOuvrageDAO.findByNom(nom);
	}

}
