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
		logger.info("--CREATE ouvrage --");
		logger.debug("ouvrage : " + ouvrage);
		return ouvrageDAO.save(ouvrage);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Ouvrage update(Ouvrage ouvrage) throws ServiceException {
		logger.info("--UPDATE ouvrage --");
		logger.debug("ouvrage : " + ouvrage);
		return ouvrageDAO.saveAndFlush(ouvrage);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Ouvrage ouvrage) throws ServiceException {
		logger.info("--DELETE ouvrage --");
		logger.debug("ouvrage : " + ouvrage);
		ouvrageDAO.delete(ouvrage);
	}

	@Override
	public List<Ouvrage> findAll() throws ServiceException {
		logger.info("--FINDALL ouvrage --");
		return ouvrageDAO.findAll();
	}

	/**
	 * Méthode spécifique pour récupérer les mesures associées à l'ouvrage dû au
	 * FetchType.Lazy
	 */
	@Override
	public Ouvrage findByIdWithJoinFechMesures(Integer id) throws ServiceException {
		logger.info("--findByIdWithJoinFechMesures mesure --");
		logger.info("id : " + id);
		Ouvrage ouvrage = ouvrageDAO.findByIdWithJoinFechMesures(id);
		System.out.println(ouvrage);
		return ouvrage;
	}

}
