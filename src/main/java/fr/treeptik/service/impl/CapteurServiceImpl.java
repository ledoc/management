package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.CapteurDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Capteur;
import fr.treeptik.model.TypeMesureOrTrame;
import fr.treeptik.service.CapteurService;

@Service
public class CapteurServiceImpl implements CapteurService {

	@Inject
	private CapteurDAO capteurDAO;

	private Logger logger = Logger.getLogger(CapteurServiceImpl.class);

	@Override
	public Capteur findById(Integer id) throws ServiceException {
		return capteurDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Capteur create(Capteur capteur) throws ServiceException {
		logger.info("--create CapteurServiceImpl --");

		capteur = capteurDAO.save(capteur);
		logger.debug("capteur : " + capteur);
		return capteur;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Capteur update(Capteur capteur) throws ServiceException {
		logger.info("--update CapteurServiceImpl -- capteur : " + capteur);
		return capteurDAO.saveAndFlush(capteur);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--remove CapteurServiceImpl -- capteurId : " + id);
		capteurDAO.delete(id);
	}

	@Override
	public List<Capteur> findAll() throws ServiceException {
		logger.info("--findAll CapteurServiceImpl --");
		return capteurDAO.findAll();
	}

	/**
	 * Méthode spécifique pour récupérer les trameDWs associées au enregistreur
	 * dû au FetchType.Lazy
	 */
	@Override
	public Capteur findByIdWithJoinFetchAlertesActives(Integer id)
			throws ServiceException {
		logger.info("--findByIdWithJoinFechAlertesActives EnregistreurServiceImpl -- id : "
				+ id);
		Capteur capteur = capteurDAO.findByIdWithJoinFetchAlertesActives(id);
		logger.debug(capteur);
		return capteur;
	}

	@Override
	public Capteur findByEnregistreurAndTypeMesureOrTrame(
			TypeMesureOrTrame typeMesureOrTrame, Integer enregistreurId)
			throws ServiceException {
		logger.info("--findByEnregistreurAndTypeMesureOrTrame CapteurServiceImpl -- typeMesureOrTrame : "
				+ typeMesureOrTrame + " -- enregistreurId : " + enregistreurId);
		return capteurDAO.findByEnregistreurIdAndTypeMesureOrTrame(
				typeMesureOrTrame, enregistreurId);
	}

	@Override
	public List<Capteur> findAllByEnregistreurId(Integer enregistreurId)
			throws ServiceException {
		logger.info("--findByTypeMesureOrTrame CapteurServiceImpl --");
		return capteurDAO.findAllByEnregistreurId(enregistreurId);
	}

}