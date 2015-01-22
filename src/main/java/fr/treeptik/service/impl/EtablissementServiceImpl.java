package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.EtablissementDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Etablissement;
import fr.treeptik.service.EtablissementService;

@Service
public class EtablissementServiceImpl implements EtablissementService {

	@Inject
	private EtablissementDAO etablissementDAO;

	private Logger logger = Logger.getLogger(EtablissementServiceImpl.class);

	@Override
	public Etablissement findById(Integer id) throws ServiceException {
		return etablissementDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Etablissement create(Etablissement etablissement)
			throws ServiceException {
		logger.info("--CREATE etablissement --");
		logger.debug("etablissement : " + etablissement);
		return etablissementDAO.save(etablissement);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Etablissement update(Etablissement etablissement)
			throws ServiceException {
		logger.info("--UPDATE etablissement --");
		logger.debug("etablissement : " + etablissement);
		return etablissementDAO.saveAndFlush(etablissement);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Etablissement etablissement) throws ServiceException {
		logger.info("--DELETE etablissement --");
		logger.debug("etablissement : " + etablissement);
		etablissementDAO.delete(etablissement);
	}
	
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE etablissement --");
		logger.debug("etablissementId : " + id);
		etablissementDAO.delete(id);
	}

	@Override
	public List<Etablissement> findAll() throws ServiceException {
		logger.info("--FINDALL etablissement --");
		return etablissementDAO.findAll();
	}
	
	/**
	 * Méthode spécifique pour récupérer les mesures associées à l'ouvrage dû au
	 * FetchType.Lazy
	 */
	@Override
	public Etablissement findByIdWithJoinFechSites(Integer id) throws ServiceException {
		logger.info("--findByIdWithJoinFechSites etablissement --");
		logger.info("id : " + id);
		Etablissement etablissement = etablissementDAO.findByIdWithJoinFechSites(id);
		System.out.println(etablissement);
		return etablissement;
	}

}
