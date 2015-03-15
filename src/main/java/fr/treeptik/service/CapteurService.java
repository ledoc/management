package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Capteur;
import fr.treeptik.model.TypeCaptAlerteMesure;

public interface CapteurService {

	Capteur findById(Integer id) throws ServiceException;

	Capteur create(Capteur capteur) throws ServiceException;

	Capteur update(Capteur capteur) throws ServiceException;

	List<Capteur> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;
	
	Capteur findByIdWithJoinFetchAlertesActives(Integer id)
			throws ServiceException;

	Capteur findByEnregistreurAndTypeCaptAlerteMesure(
			TypeCaptAlerteMesure typeCaptAlerteMesure, Integer enregistreurId)
			throws ServiceException;

	List<Capteur> findAllByEnregistreurId(Integer enregistreurId)
			throws ServiceException;

}
