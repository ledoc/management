package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
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
		logger.info("--CREATE OuvrageServiceImpl -- ouvrage : " + ouvrage);
		return ouvrageDAO.save(ouvrage);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Ouvrage update(Ouvrage ouvrage) throws ServiceException {
		logger.info("--UPDATE OuvrageServiceImpl -- ouvrage : " + ouvrage);
		return ouvrageDAO.saveAndFlush(ouvrage);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE OuvrageServiceImpl -- ouvrageId : " + id);
		
		// Pour la suppression des doc
		Ouvrage o = ouvrageDAO.findOne(id);
		o.getDocuments().size();
		
		// Pour la suppression des ouvrages fils
		o.getOuvrageFils().forEach(ou-> {
			ou.setOuvrageMaitre(null);
			ou.setRattachement(false);
		});
		
		ouvrageDAO.delete(id);
	}

	@Override
	public List<Ouvrage> findAll() throws ServiceException {
		logger.info("--FINDALL OuvrageServiceImpl --");
		return ouvrageDAO.findAll();
	}

	/**
	 * Méthode spécifique pour récupérer les documents associées à l'ouvrage dû
	 * au FetchType.Lazy
	 */
	@Override
	public Ouvrage findByIdWithJoinFetchDocuments(Integer id)
			throws ServiceException {
		logger.info("--findByIdWithJoinFetchDocuments OuvrageServiceImpl -- id : "
				+ id);
		Ouvrage ouvrage = ouvrageDAO.findByIdWithJoinFetchDocuments(id);
		return ouvrage;
	}

	@Override
	public Ouvrage findByIdWithJoinFetchEnregistreurs(Integer id)
			throws ServiceException {
		logger.info("--findByIdWithJoinFetchEnregistreurs OuvrageServiceImpl -- id : " + id);
		Ouvrage ouvrage = ouvrageDAO.findByIdWithJoinFetchEnregistreurs(id);
		return ouvrage;
	}

	@Override
	public List<Ouvrage> findByClientLogin(String userLogin)
			throws ServiceException {
		logger.info("--findByClient OuvrageServiceImpl-- userLogin : " + userLogin);
		return ouvrageDAO.findByClientLogin(userLogin);
	}

	@Override
	public List<Ouvrage> findByClientId(Integer userId) throws ServiceException {
		logger.info("--findByClient userId OuvrageServiceImpl-- userId : " + userId);

		return ouvrageDAO.findByClientId(userId);
	}

	@Override
	public List<Ouvrage> findFreeOuvrages() throws ServiceException {
		logger.info("--findFreeOuvrages OuvrageServiceImpl --");
		List<Ouvrage> ouvrages;
		try {
			ouvrages = ouvrageDAO.findFreeOuvrages();
		} catch (PersistenceException e) {
			logger.error("Error OuvrageService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return ouvrages;
	}

}
