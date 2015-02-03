package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.OuvrageDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.service.OuvrageService;

@Service
public class OuvrageServiceImpl implements OuvrageService {

	@Inject
	private OuvrageDAO ouvrageDAO;

	private Logger logger = Logger.getLogger(OuvrageServiceImpl.class);

	@Override
	public Ouvrage findById(Integer id) throws ServiceException {
		return ouvrageDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Ouvrage create(Ouvrage ouvrage) throws ServiceException {
		logger.info("--CREATE OuvrageServiceImpl --");
		logger.debug("ouvrage : " + ouvrage);
		return ouvrageDAO.save(ouvrage);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Ouvrage update(Ouvrage ouvrage) throws ServiceException {
		logger.info("--UPDATE OuvrageServiceImpl --");
		logger.debug("ouvrage : " + ouvrage);
		return ouvrageDAO.saveAndFlush(ouvrage);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Ouvrage ouvrage) throws ServiceException {
		logger.info("--DELETE OuvrageServiceImpl --");
		logger.debug("ouvrage : " + ouvrage);
		ouvrageDAO.delete(ouvrage);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE OuvrageServiceImpl --");
		logger.debug("ouvrageId : " + id);
		ouvrageDAO.delete(id);
	}

	@Override
	public List<Ouvrage> findAll() throws ServiceException {
		logger.info("--FINDALL OuvrageServiceImpl --");
		return ouvrageDAO.findAll();
	}

	/**
	 * Méthode spécifique pour récupérer les mesures associées à l'ouvrage dû au
	 * FetchType.Lazy
	 */
	@Override
	public Ouvrage findByIdWithJoinFetchMesures(Integer id) throws ServiceException {
		logger.info("--findByIdWithJoinFetchMesures OuvrageServiceImpl --");
		logger.info("id : " + id);
		Ouvrage ouvrage = ouvrageDAO.findByIdWithJoinFetchMesures(id);
		return ouvrage;
	}

	/**
	 * Méthode spécifique pour récupérer les mesures associées à l'ouvrage dû au
	 * FetchType.Lazy
	 */
	@Override
	public Ouvrage findByIdWithJoinFetchDocuments(Integer id) throws ServiceException {
		logger.info("--findByIdWithJoinFetchDocuments OuvrageServiceImpl --");
		logger.info("id : " + id);
		Ouvrage ouvrage = ouvrageDAO.findByIdWithJoinFetchDocuments(id);
		return ouvrage;
	}

	@Override
	public Ouvrage findByIdWithJoinFetchEnregistreurs(Integer id) throws ServiceException {
		logger.info("--findByIdWithJoinFetchEnregistreurs OuvrageServiceImpl --");
		logger.info("id : " + id);Ouvrage ouvrage = ouvrageDAO.findByIdWithJoinFetchEnregistreurs(id);
		return ouvrage;
	}
	
	@Override
	public List<Ouvrage> findByClientLogin(String userLogin) throws ServiceException{
		logger.info("--findByClient userLogin OuvrageServiceImpl--");
		logger.debug("userLogin : " + userLogin);
		return ouvrageDAO.findByClientLogin(userLogin);
	}

}
