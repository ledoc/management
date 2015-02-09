package fr.treeptik.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.EtablissementDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;
import fr.treeptik.service.EtablissementService;
import fr.treeptik.service.SiteService;

@Service
public class EtablissementServiceImpl implements EtablissementService {

	@Inject
	private EtablissementDAO etablissementDAO;
	@Inject
	SiteService siteService;

	private Logger logger = Logger.getLogger(EtablissementServiceImpl.class);

	@Override
	public Etablissement findById(Integer id) throws ServiceException {
		return etablissementDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Etablissement create(Etablissement etablissement)
			throws ServiceException {
		logger.info("--CREATE EtablissementService --");
		logger.debug("etablissement : " + etablissement);
		return etablissementDAO.save(etablissement);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Etablissement update(Etablissement etablissement)
			throws ServiceException {
		logger.info("--UPDATE EtablissementService --");
		logger.debug("etablissement : " + etablissement);
		try {

			etablissement = etablissementDAO.save(etablissement);
		} catch (PersistenceException e) {
			logger.error("Error EtablissementService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		return etablissement;
	}

	
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE EtablissementService -- etablissementId : " + id);
		try {
			
			Etablissement etablissement = etablissementDAO.findOne(id);
			etablissement.getClients().forEach(c -> c.getEtablissements().remove(etablissement));
			
			etablissementDAO.delete(id);
			
		} catch (PersistenceException e) {
			logger.error("Error EtablissementService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

	}

	@Override
	public List<Etablissement> findByClientLogin(String login)
			throws ServiceException {
		logger.info("--findByClientLogin EtablissementService -- login : " + login);
		List<Etablissement> etablissements;
		try {
			etablissements = etablissementDAO.findByClientLogin(login);
		} catch (PersistenceException e) {
			logger.error("Error EtablissementService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return etablissements;
	}

	@Override
	public List<Etablissement> findAll() throws ServiceException {
		logger.info("--FINDALL EtablissementService --");
		List<Etablissement> etablissements;
		try {
			etablissements = etablissementDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error EtablissementService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return etablissements;
	}

	@Override
	public List<Etablissement> findFreeEtablissements() throws ServiceException {
		logger.info("--findFreeEtablissements EtablissementService --");
		List<Etablissement> etablissements;
		try {
			etablissements = etablissementDAO.findFreeEtablissements();
		} catch (PersistenceException e) {
			logger.error("Error EtablissementService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return etablissements;
	}

	/**
	 * Méthode spécifique pour récupérer les sites associées à un etablissement
	 * dû au FetchType.Lazy
	 */
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Etablissement findByIdWithJoinFetchSites(Integer id)
			throws ServiceException {
		logger.info("--findByIdWithJoinFetchSites EtablissementService -- id : " + id);

		Etablissement etablissement;
		try {
			etablissement = etablissementDAO.findByIdWithJoinFetchSites(id);
		} catch (PersistenceException e) {
			logger.error("Error EtablissementService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return etablissement;
	}

	@Override
	public List<Ouvrage> findAllOuvragesOfEtablissement(Integer id)
			throws ServiceException {
		logger.info("--findAllOuvragesOfEtablissement EtablissementService --");
		List<Ouvrage> ouvrages = new ArrayList<Ouvrage>();
		List<Site> sites = new ArrayList<Site>();
		Etablissement etablissement = this.findByIdWithJoinFetchSites(id);
		try {
			sites = etablissement.getSites();
			for (Site site : sites) {
				site = siteService.findByIdWithJoinFetchOuvrages(site.getId());
				ouvrages.addAll(site.getOuvrages());
			}

		} catch (PersistenceException e) {
			logger.error("Error EtablissementService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return ouvrages;
	}
	
	/**
	 * Méthode spécifique pour récupérer les sites associées à un etablissement
	 * dû au FetchType.Lazy
	 */
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Etablissement findByIdWithJoinFetchClients(Integer id)
			throws ServiceException {
		logger.info("--findByIdWithJoinFetchClients EtablissementService -- id : " + id);

		Etablissement etablissement;
		try {
			etablissement = etablissementDAO.findByIdWithJoinFetchClients(id);
		} catch (PersistenceException e) {
			logger.error("Error EtablissementService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return etablissement;
	}
}
