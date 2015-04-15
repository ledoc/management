package fr.treeptik.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.TypeCaptAlerteMesure;
import fr.treeptik.shared.dto.graph.PointGraphDTO;

public interface MesureService {

	Mesure findById(Integer id) throws ServiceException;

	Mesure create(Mesure mesure) throws ServiceException;

	Mesure update(Mesure mesure) throws ServiceException;

	List<Mesure> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;


	List<Mesure> findAllDetails() throws ServiceException;

	PointGraphDTO transformMesureInPoint(Mesure item) throws ServiceException;

	Mesure findByIdWithFetch(Integer id) throws ServiceException;

	void affectNewNiveauManuel(Integer mesureId) throws ServiceException;

	HashMap<TypeCaptAlerteMesure, Mesure> conversionSignal_NiveauEauDeSurface(
			HashMap<TypeCaptAlerteMesure, Mesure> hashMapCalcul)
			throws ServiceException;

	HashMap<TypeCaptAlerteMesure, Mesure> conversionSignal_NiveauEauNappeSouterraine(
			HashMap<TypeCaptAlerteMesure, Mesure> hashMapCalcul)
			throws ServiceException;

	HashMap<TypeCaptAlerteMesure, Mesure> conversionSignal_Conductivite(
			HashMap<TypeCaptAlerteMesure, Mesure> hashMapCalcul)
					throws ServiceException;

	List<Mesure> findByCapteurIdBetweenDates(Integer id, Date dateDebut,
			Date dateFin) throws ServiceException;

	List<Mesure> findByCapteurIdWithFetch(Integer id) throws ServiceException;

	HashMap<TypeCaptAlerteMesure, Mesure> conversionSignal_Temperature(
			HashMap<TypeCaptAlerteMesure, Mesure> hashMapCalcul)
			throws ServiceException;

    void convertForDisplay(Mesure mesure);
}
