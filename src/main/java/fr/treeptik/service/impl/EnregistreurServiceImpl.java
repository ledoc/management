package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.EnregistreurDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.OuvrageService;

@Service
public class EnregistreurServiceImpl implements EnregistreurService {

	@Inject
	private EnregistreurDAO enregistreurDAO;
	@Inject
	private OuvrageService ouvrageService;

	private Logger logger = Logger.getLogger(EnregistreurServiceImpl.class);

	@Override
	public Enregistreur findById(Integer id) throws ServiceException {
		return enregistreurDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Enregistreur create(Enregistreur enregistreur) throws ServiceException {
		logger.info("--CREATE EnregistreurServiceImpl --");
		logger.debug("enregistreur : " + enregistreur);
		return enregistreurDAO.save(enregistreur);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Enregistreur update(Enregistreur enregistreur) throws ServiceException {
		logger.info("--update EnregistreurServiceImpl --");
		logger.debug("enregistreur : " + enregistreur);
		return enregistreurDAO.saveAndFlush(enregistreur);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Enregistreur enregistreur) throws ServiceException {
		logger.info("--remove EnregistreurServiceImpl --");
		logger.debug("enregistreur : " + enregistreur);
		
		Ouvrage ouvrage = ouvrageService.findByIdWithJoinFetchEnregistreurs(enregistreur.getOuvrage().getId());
		boolean success = ouvrage.getEnregistreurs().remove(enregistreur);
		
		logger.debug("remove enregistreur from ouvrage succes ? : " + success);
		ouvrageService.update(ouvrage);
		enregistreurDAO.delete(enregistreur);
	}
	
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE EnregistreurServiceImpl --enregistreurId : " + id);
		
		Enregistreur enregistreur = this.findById(id);
		Ouvrage ouvrage = ouvrageService.findByIdWithJoinFetchEnregistreurs(enregistreur.getOuvrage().getId());
		boolean success = ouvrage.getEnregistreurs().remove(enregistreur);
		
		logger.debug("remove enregistreur from ouvrage succes ? : " + success);
		ouvrageService.update(ouvrage);
		enregistreurDAO.delete(id);
	}

	@Override
	public List<Enregistreur> findAll() throws ServiceException {
		logger.info("--FINDALL EnregistreurServiceImpl --");
		return enregistreurDAO.findAll();
	}
	
	@Override
	public List<Enregistreur> findFreeEnregistreurs() throws ServiceException {
		logger.info("--findFreeSites SiteServiceImpl --");
		List<Enregistreur> enregistreurs;
		try {
			enregistreurs = enregistreurDAO.findFreeEnregistreurs();
		} catch (PersistenceException e) {
			logger.error("Error EnregistreurService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return enregistreurs;
	}

	@Override
	public Enregistreur findByMid(String mid) throws ServiceException {
		logger.info("--findByMid EnregistreurServiceImpl --");
		logger.debug("mid : " + mid);
		return enregistreurDAO.findByMid(mid);
	}

	/**
	 * Méthode spécifique pour récupérer les trameDWs associées au enregistreur dû au
	 * FetchType.Lazy
	 */
	@Override
	public Enregistreur findByMidWithJoinFetchTrameDWs(String mid) throws ServiceException {
		logger.info("--findByMidWithJoinFechTrameDWs enregistreur --");
		logger.info("mid : " + mid);
		Enregistreur enregistreur = enregistreurDAO.findByMidWithJoinFechTrameDWs(mid);
		System.out.println(enregistreur);
		return enregistreur;
	}
	
	/**
	 * Méthode spécifique pour récupérer les trameDWs associées au enregistreur dû au
	 * FetchType.Lazy
	 */
	@Override
	public Enregistreur findByIdWithJoinFetchAlertesActives(Integer id) throws ServiceException {
		logger.info("--findByIdWithJoinFechAlertesActives enregistreur --");
		logger.info("id : " + id);
		Enregistreur enregistreur = enregistreurDAO.findByIdWithJoinFetchAlertesActives(id);
		System.out.println(enregistreur);
		return enregistreur;
	}
}
