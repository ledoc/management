package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.AlerteEmiseDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Administrateur;
import fr.treeptik.model.AlerteEmise;
import fr.treeptik.service.AdministrateurService;
import fr.treeptik.service.AlerteEmiseService;
import fr.treeptik.util.EmailUtils;

@Service
public class AlerteEmiseServiceImpl implements AlerteEmiseService {

	@Inject
	private AlerteEmiseDAO alerteEmiseDAO;

	@Inject
	private AdministrateurService administrateurService;

	@Inject
	private EmailUtils emailUtils;

	private Logger logger = Logger.getLogger(AlerteEmiseServiceImpl.class);

	@Override
	public AlerteEmise findById(Integer id) throws ServiceException {
		return alerteEmiseDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public AlerteEmise create(AlerteEmise alerteEmise) throws ServiceException {
		logger.info("--create AlerteEmiseServiceImpl -- alerte : "
				+ alerteEmise);
		return alerteEmiseDAO.save(alerteEmise);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public AlerteEmise update(AlerteEmise alerteEmise) throws ServiceException {
		logger.info("--update AlerteEmiseServiceImpl -- alerteEmise : "
				+ alerteEmise);
		return alerteEmiseDAO.saveAndFlush(alerteEmise);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--remove AlerteEmiseServiceImpl -- alerteId : " + id);
		alerteEmiseDAO.delete(id);
	}

	@Override
	public List<AlerteEmise> findAll() throws ServiceException {
		logger.info("--findAll AlerteEmiseServiceImpl --");
		return alerteEmiseDAO.findAll();
	}

	@Override
	public List<AlerteEmise> findAllByClientLogin(String userLogin)
			throws ServiceException {
		logger.info("--findAllByClientLogin AlerteServiceImpl -- userLogin : "
				+ userLogin);

		return alerteEmiseDAO.findAllByClientLogin(userLogin);
	}

	@Override
	public AlerteEmise findLastAlerteEmiseByCodeAlerte(String codeAlerte)
			throws ServiceException {
		logger.info("--findLastAlerteEmiseByCodeAlerte AlerteServiceImpl -- codeAlerte : "
				+ codeAlerte);
		return alerteEmiseDAO.findLastAlerteEmiseByCodeAlerte(codeAlerte);
	}

	@Override
	public void acquittementAlerte(Integer id) throws ServiceException {
		logger.info("--acquittementAlerte AlerteServiceImpl -- alerteEmiseId : "
				+ id);
		AlerteEmise alerteEmise = this.findById(id);
		alerteEmise.setAcquittement(true);
		alerteEmise.setCompteurCheckAcquittement(0);
		this.update(alerteEmise);
	}

	public List<AlerteEmise> findAlertesActivesByEnregistreurId(Integer id)
			throws ServiceException {
		logger.info("--findAlertesActivesByEnregistreurId AlerteServiceImpl -- alerteId : "
				+ id);

		List<AlerteEmise> alerteEmises = alerteEmiseDAO
				.findAlertesActivesByEnregistreurId(id);

		return alerteEmises;
	}

	public List<AlerteEmise> findAllNonAcquittees() throws ServiceException {
		logger.info("--findAllNonAcquittees AlerteServiceImpl --");

		List<AlerteEmise> alerteEmises = alerteEmiseDAO.findAllNonAcquittees();

		return alerteEmises;
	}

	@Scheduled(fixedRate = 650000)
	@Override
	public void scheduledAllAlerteAcquittement() throws ServiceException {
		logger.info("--scheduledAlerteAcquittement AlerteServiceImpl --");

		List<AlerteEmise> listAlerteEmisesNonAcquittees = this
				.findAllNonAcquittees();

		for (AlerteEmise alerteEmise : listAlerteEmisesNonAcquittees) {

			alerteEmise.setCompteurCheckAcquittement(alerteEmise
					.getCompteurCheckAcquittement() + 1);

			if (alerteEmise.getCompteurCheckAcquittement() >= 3) {

				StringBuilder strbld = new StringBuilder();

				List<Administrateur> administrateurs = administrateurService
						.findAll();
				administrateurs.forEach(a -> strbld.append(a.getMail1() + ","));

				String destinataireEmails = strbld.toString();
				destinataireEmails = destinataireEmails.substring(0,
						destinataireEmails.lastIndexOf(","));

				try {
					emailUtils.sendAlerteAcquittementTimeout(alerteEmise,
							destinataireEmails);
				} catch (MessagingException e) {
					logger.error("Error OuvrageService : " + e);
					throw new ServiceException(e.getLocalizedMessage(), e);
				}
			}
			
			this.update(alerteEmise);

			logger.debug("alerte non acquittée : " + alerteEmise);
		}
	}

}
