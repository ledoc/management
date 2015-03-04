package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.EnregistreurDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.OuvrageService;
import fr.treeptik.util.XMLRPCUtils;

@Service
public class EnregistreurServiceImpl implements EnregistreurService {

	@Inject
	private EnregistreurDAO enregistreurDAO;
	@Inject
	private OuvrageService ouvrageService;
	@Inject
	private XMLRPCUtils xmlrpcUtils;

	private Logger logger = Logger.getLogger(EnregistreurServiceImpl.class);

	@Override
	public Enregistreur findById(Integer id) throws ServiceException {
		return enregistreurDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Enregistreur create(Enregistreur enregistreur)
			throws ServiceException {
		logger.info("--CREATE EnregistreurServiceImpl -- enregistreur : "
				+ enregistreur);

//		xmlrpcUtils.addMobile(enregistreur);

		return enregistreurDAO.save(enregistreur);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Enregistreur update(Enregistreur enregistreur)
			throws ServiceException {
		logger.info("--update EnregistreurServiceImpl -- enregistreur : "
				+ enregistreur);
		return enregistreurDAO.saveAndFlush(enregistreur);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE EnregistreurServiceImpl -- enregistreurId : "
				+ id);

		try {

			Enregistreur enregistreur = this.findById(id);
			xmlrpcUtils.removeMobile(enregistreur);
			
			
			Ouvrage ouvrage = ouvrageService
					.findByIdWithJoinFetchEnregistreurs(enregistreur
							.getOuvrage().getId());
			boolean success = ouvrage.getEnregistreurs().remove(enregistreur);

			logger.debug("remove enregistreur from ouvrage success ? : "
					+ success);
			ouvrageService.update(ouvrage);
			enregistreurDAO.delete(id);

		} catch (PersistenceException | ServiceException e) {
			logger.error("Error EnregistreurService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

	}

	@Override
	public List<Enregistreur> findAll() throws ServiceException {
		logger.info("--FINDALL EnregistreurServiceImpl --");
		return enregistreurDAO.findAll();
	}

	@Override
	public List<Enregistreur> findFreeEnregistreurs() throws ServiceException {
		logger.info("--findFreeEnregistreurs EnregistreurServiceImpl --");
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
		logger.info("--findByMid EnregistreurServiceImpl -- mid : " + mid);
		return enregistreurDAO.findByMid(mid);
	}

	/**
	 * Méthode spécifique pour récupérer les trameDWs associées au enregistreur
	 * dû au FetchType.Lazy
	 */
	@Override
	public Enregistreur findByMidWithJoinFetchTrameDWs(String mid)
			throws ServiceException {
		logger.info("--findByMidWithJoinFechTrameDWs EnregistreurServiceImpl -- mid : "
				+ mid);
		Enregistreur enregistreur = enregistreurDAO
				.findByMidWithJoinFechTrameDWs(mid);
		logger.debug(enregistreur);
		return enregistreur;
	}

	/**
	 * Méthode spécifique pour récupérer les trameDWs associées au enregistreur
	 * dû au FetchType.Lazy
	 */
	@Override
	public Enregistreur findByIdWithJoinFetchAlertesActives(Integer id)
			throws ServiceException {
		logger.info("--findByIdWithJoinFechAlertesActives EnregistreurServiceImpl -- id : "
				+ id);
		Enregistreur enregistreur = enregistreurDAO
				.findByIdWithJoinFetchAlertesActives(id);
		logger.debug(enregistreur);
		return enregistreur;
	}

	@Override
	public List<Enregistreur> findByClientLogin(String userLogin)
			throws ServiceException {
		logger.info("--findByClient EnregistreurServiceImpl-- userLogin : "
				+ userLogin);
		return enregistreurDAO.findByClientLogin(userLogin);
	}

	@Override
	public List<Enregistreur> findBySiteId(Integer id) throws ServiceException {
		logger.info("--findBySiteId EnregistreurServiceImpl-- id : " + id);
		return enregistreurDAO.findBySiteId(id);
	}

}
