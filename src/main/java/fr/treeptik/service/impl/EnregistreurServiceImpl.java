package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.EnregistreurDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.service.EnregistreurService;

@Service
public class EnregistreurServiceImpl implements EnregistreurService {

	@Inject
	private EnregistreurDAO enregistreurDAO;

	private Logger logger = Logger.getLogger(EnregistreurServiceImpl.class);

	@Override
	public Enregistreur findById(Integer id) throws ServiceException {
		return enregistreurDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Enregistreur create(Enregistreur enregistreur) throws ServiceException {
		logger.info("--CREATE enregistreur --");
		logger.debug("enregistreur : " + enregistreur);
		return enregistreurDAO.save(enregistreur);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Enregistreur update(Enregistreur enregistreur) throws ServiceException {
		logger.info("--UPDATE enregistreur --");
		logger.debug("enregistreur : " + enregistreur);
		return enregistreurDAO.saveAndFlush(enregistreur);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Enregistreur enregistreur) throws ServiceException {
		logger.info("--DELETE enregistreur --");
		logger.debug("enregistreur : " + enregistreur);
		enregistreurDAO.delete(enregistreur);
	}
	
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE enregistreur --");
		logger.debug("enregistreurId : " + id);
		enregistreurDAO.delete(id);
	}

	@Override
	public List<Enregistreur> findAll() throws ServiceException {
		logger.info("--FINDALL enregistreur --");
		return enregistreurDAO.findAll();
	}

	@Override
	public Enregistreur findByMid(String mid) throws ServiceException {
		logger.info("--findByMid enregistreur --");
		logger.debug("mid : " + mid);
		return enregistreurDAO.findByMid(mid);
	}

	/**
	 * Méthode spécifique pour récupérer les trameDWs associées au enregistreur dû au
	 * FetchType.Lazy
	 */
	@Override
	public Enregistreur findByMidWithJoinFechTrameDWs(String mid) throws ServiceException {
		logger.info("--findByMidWithJoinFechTrameDWs enregistreur --");
		logger.info("mid : " + mid);
		Enregistreur enregistreur = enregistreurDAO.findByMidWithJoinFechTrameDWs(mid);
		System.out.println(enregistreur);
		return enregistreur;
	}

}
