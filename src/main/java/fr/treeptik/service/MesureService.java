package fr.treeptik.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.Point;
import fr.treeptik.model.TypeMesureOrTrame;

public interface MesureService {

	Mesure findById(Integer id) throws ServiceException;

	Mesure create(Mesure mesure) throws ServiceException;

	Mesure update(Mesure mesure) throws ServiceException;

	List<Mesure> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;


	List<Mesure> findAllDetails() throws ServiceException;

	Point transformMesureInPoint(Mesure item) throws ServiceException;

	Mesure findByIdWithFetch(Integer id) throws ServiceException;

	void affectNewNiveauManuel(Integer mesureId) throws ServiceException;

	Mesure conversionSignal_Temperature(Mesure mesureTemp)
			throws ServiceException;

	Mesure conversionSignal_NiveauEauDeSurface(
			HashMap<TypeMesureOrTrame, Mesure> hashMapCalcul)
			throws ServiceException;

	Mesure conversionSignal_NiveauEauNappeSouterraine(
			HashMap<TypeMesureOrTrame, Mesure> hashMapCalcul)
			throws ServiceException;

	Mesure conversionSignal_Conductivite(
			HashMap<TypeMesureOrTrame, Mesure> hashMapCalcul)
					throws ServiceException;

	List<Mesure> findByCapteurIdBetweenDates(Integer id, Date dateDebut,
			Date dateFin) throws ServiceException;

	List<Mesure> findByCapteurIdWithFetch(Integer id) throws ServiceException;

}
