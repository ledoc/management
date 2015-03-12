package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Capteur;
import fr.treeptik.model.TypeMesureOrTrame;

public interface CapteurService {

	Capteur findById(Integer id) throws ServiceException;

	Capteur create(Capteur capteur) throws ServiceException;

	Capteur update(Capteur capteur) throws ServiceException;

	List<Capteur> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;
	
	Capteur findByIdWithJoinFetchAlertesActives(Integer id)
			throws ServiceException;

	Capteur findByEnregistreurAndTypeMesureOrTrame(
			TypeMesureOrTrame typeMesureOrTrame, Integer enregistreurId)
			throws ServiceException;

	List<Capteur> findAllByEnregistreurId(Integer enregistreurId)
			throws ServiceException;

}
