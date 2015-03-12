package fr.treeptik.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Capteur;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.TypeMesureOrTrame;
import fr.treeptik.model.deveryware.DeviceState;
import fr.treeptik.service.CapteurService;
import fr.treeptik.service.DeverywareService;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.TrameDWService;
import fr.treeptik.util.DateUnixConverter;
import fr.treeptik.util.XMLRPCUtils;

@Service
public class DeverywareServiceImpl implements DeverywareService {

	private Logger logger = Logger.getLogger(DeverywareServiceImpl.class);
	@Inject
	private XMLRPCUtils xmlRPCUtils;
	@Inject
	private EnregistreurService enregistreurService;
	@Inject
	private TrameDWService trameDWService;
	@Inject
	private CapteurService capteurService;

	@Inject
	private MesureService mesureService;

	public String openSession() throws Exception {

		String sessionKey = xmlRPCUtils.openSession();
		return sessionKey;
	}

	/**
	 * la (ou l'une) des méthodes pour récupérer l'ampérage renvoyé par tous les
	 * enregistreurs
	 * 
	 * @param mid
	 * @throws ServiceException
	 */
	@Scheduled(fixedRate = 180000)
	@SuppressWarnings("unchecked")
	public void getHistory() throws ServiceException {
		logger.info("--getHistory DeverywareServiceImpl --");

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		List<Enregistreur> enregistreurList = enregistreurService.findAll();

		for (Enregistreur enregistreur : enregistreurList) {
			enregistreur = enregistreurService
					.findByMidWithJoinFetchTrameDWs(enregistreur.getMid());
			Object[] history = xmlRPCUtils.getHistory(enregistreur.getMid(),
					sessionKey);

			/**
			 * gps://ORANGE/+33781916177 gps://ORANGE/+33687575529
			 */

			// Enregistreur enregistreur = enregistreurService
			// .findByMidWithJoinFetchTrameDWs("gps://ORANGE/+33687575529");
			// Object[] history =
			// xmlRPCUtils.getHistory("gps://ORANGE/+33687575529",
			// sessionKey);

			logger.debug(history.length + " trame(s) History récupérée(s)");

			for (Object historyXmlRpc : history) {
				HashMap<String, Object> hashMapHistoryXmlRpc = (HashMap<String, Object>) historyXmlRpc;

				logger.debug(hashMapHistoryXmlRpc);

				TrameDW trameDW = this.transfertHistoryToTrameDW(enregistreur,
						hashMapHistoryXmlRpc);

				if (trameDW.getConcatenationValeurs() != null) {

					if (enregistreur.getTrameDWs() != null) {

						if (!this.trameAlreadyExists(enregistreur.getTrameDWs(),
								trameDW)) {

							trameDW.setEnregistreur(enregistreur);
							trameDW = trameDWService.create(trameDW);
							trameDW = trameDWService.findById(trameDW.getId());
							enregistreur.getTrameDWs().add(trameDW);

							this.transfertTrameToMesures(trameDW);

						} else {

							logger.debug("Pas de nouvelle trameDW a enregistrée");
						}

					} else {
						trameDW.setEnregistreur(enregistreur);
						trameDW = trameDWService.create(trameDW);
						trameDW = trameDWService.findById(trameDW.getId());

						this.transfertTrameToMesures(trameDW);

						List<TrameDW> trameDWs = new ArrayList<TrameDW>();
						trameDWs.add(trameDW);
						enregistreur.setTrameDWs(trameDWs);

					}
				}
			}
		}
	}

	// Deveryflow.mobileList
	public List<Enregistreur> enregistreurList(String sessionKey)
			throws ServiceException {
		logger.info("--enregistreurList DeverywareServiceImpl --");

		Object[] listEnregistreursXMLRPC = xmlRPCUtils
				.enregistreurList(sessionKey);

		List<Enregistreur> enregistreursFromDW = new ArrayList<Enregistreur>();

		for (Object enregistreurXmlRpc : listEnregistreursXMLRPC) {

			@SuppressWarnings("unchecked")
			HashMap<String, Object> enregistreurHashMap = (HashMap<String, Object>) enregistreurXmlRpc;
			Enregistreur enregistreur = new Enregistreur(enregistreurHashMap);
			enregistreursFromDW.add(enregistreur);

		}
		return enregistreursFromDW;
	}

	public void getDataHistory() throws ServiceException {
		logger.info("--getDataHistory DeverywareServiceImpl --");

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		List<Enregistreur> enregistreurList = enregistreurService.findAll();

		for (Enregistreur enregistreur : enregistreurList) {
			Object[] dataArray = xmlRPCUtils.getDataHistory(
					enregistreur.getMid(), sessionKey);
			logger.debug(dataArray.length + " DeviceData retournée");
			DeviceState deviceState;
			for (Object dataXmlRpc : dataArray) {

				logger.debug("dataXmlRpc : " + dataXmlRpc);
				@SuppressWarnings("unchecked")
				HashMap<String, Object> hashMapDataXmlRpc = (HashMap<String, Object>) dataXmlRpc;
				deviceState = new DeviceState(hashMapDataXmlRpc);
				logger.debug(deviceState);
			}
		}
	}

	public void getUnifyHistory() throws ServiceException {
		logger.info("--getUnifyHistory DeverywareServiceImpl --");

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		List<Enregistreur> enregistreurList = enregistreurService.findAll();

		for (Enregistreur enregistreur : enregistreurList) {

			Object[] unifyHistoryArray = xmlRPCUtils.getUnifyHistory(
					enregistreur.getMid(), sessionKey);

			logger.debug(unifyHistoryArray.length + " unifyHistory retournée");
			logger.debug(unifyHistoryArray);
			for (Object unifyHistoryXmlRpc : unifyHistoryArray) {
				logger.debug(unifyHistoryXmlRpc);
				@SuppressWarnings("unchecked")
				HashMap<String, Object> hashMapunifyHistoryXmlRpc = (HashMap<String, Object>) unifyHistoryXmlRpc;

				for (Entry<String, Object> object3 : hashMapunifyHistoryXmlRpc
						.entrySet()) {
					logger.debug("key : " + object3.getKey());

					if (object3.getKey().equals("alertList")) {
						Object[] alertList = (Object[]) object3.getValue();
						for (Object object : alertList) {
							logger.debug("le beau array alertlist : " + object);
						}
					}
					logger.debug("value : " + object3.getValue());
				}

				Object[] deviceDataList = (Object[]) hashMapunifyHistoryXmlRpc
						.get("deviceDataList");
				for (Object object : deviceDataList) {
					logger.debug(object);
				}
			}

		}
	}

	public void getEventHistory() throws ServiceException {
		logger.info("--getEventHistory DeverywareServiceImpl --");

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		List<Enregistreur> enregistreurList = enregistreurService.findAll();

		for (Enregistreur enregistreur : enregistreurList) {

			Object[] eventHistoryArray = xmlRPCUtils.getEventHistory(
					enregistreur.getMid(), sessionKey);

			logger.debug(eventHistoryArray.length
					+ " eventHistoryArray retournée");
			logger.debug(eventHistoryArray);
			for (Object eventHistoryXmlRpc : eventHistoryArray) {
				logger.debug(eventHistoryXmlRpc);
				@SuppressWarnings("unchecked")
				HashMap<String, Object> hashMapEventHistoryXmlRpc = (HashMap<String, Object>) eventHistoryXmlRpc;
				for (Entry<String, Object> object3 : hashMapEventHistoryXmlRpc
						.entrySet()) {
					logger.debug(object3.getKey());
					logger.debug(object3.getValue());
				}
			}

		}
	}

	public String waitForMessage() throws ServiceException {
		logger.info("--waitForMessage DeverywareServiceImpl--");

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		HashMap<String, Object> waitForMessage = xmlRPCUtils
				.waitForMessage(sessionKey);

		logger.debug(waitForMessage.size() + " waitForMessage retournée");
		logger.debug(waitForMessage);
		for (Entry<String, Object> object3 : waitForMessage.entrySet()) {
			logger.debug("key : " + object3.getKey());
			if (object3.getKey().equals("date")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				int dateInt = (int) object3.getValue();
				Long dateLong = (long) dateInt;
				String dateString = dateFormat
						.format(new Date(dateLong * 1000));
				logger.debug("date  ==== " + dateString);
			}
			logger.debug("value : " + object3.getValue());
		}
		return waitForMessage.toString();
	}

	@Override
	public void waitForMessages() throws ServiceException {
		logger.info("--waitForMessages DeverywareServiceImpl--");

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		Object[] waitForMessages = xmlRPCUtils.waitForMessages(sessionKey);

		logger.debug(waitForMessages.length + " waitForMessages retournée");
		logger.debug(waitForMessages);

		for (Object object3 : waitForMessages) {

			logger.debug(object3);
			// @SuppressWarnings("unchecked")
			// HashMap<String, Object> hashMapMessageyXmlRpc = (HashMap<String,
			// Object>) object3;
			// for (Entry<String, Object> object4 : hashMapMessageyXmlRpc
			// .entrySet()) {
			// logger.debug(object4.getKey());
			// logger.debug(object4.getValue());
			// }
		}
	}

	public void getInfo() throws Exception {
		logger.info("--getInfo DeverywareServiceImpl--");

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		HashMap<String, Object> getInfo = xmlRPCUtils.getInfo(sessionKey);

		logger.debug("taille du resultat: " + getInfo.size()
				+ " waitForMessage retournée");
		logger.debug(getInfo);
		for (Entry<String, Object> object3 : getInfo.entrySet()) {
			logger.debug("key : " + object3.getKey());
			logger.debug("value : " + object3.getValue());
		}

	}

	private TrameDW transfertHistoryToTrameDW(Enregistreur enregistreur,
			HashMap<String, Object> hashMapHistoryXmlRpc)
			throws ServiceException {
		logger.info("--transfertHistoryToTrameDW DeverywareServiceImpl--");

		TrameDW trameDW = new TrameDW();

		byte[] arrayIntensite = (byte[]) hashMapHistoryXmlRpc.get("stream4");
		String intensiteString = new String(arrayIntensite);

		trameDW.setConcatenationValeurs(intensiteString);

		try {
			trameDW.setDate(DateUnixConverter
					.intToDate((int) hashMapHistoryXmlRpc.get("date")));
		} catch (ParseException e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return trameDW;

	}

	private List<Mesure> transfertTrameToMesures(TrameDW trameDW)
			throws ServiceException {

		logger.info("--transfertHistoryToTrameDW DeverywareServiceImpl--");

		ArrayList<Mesure> listMesures = new ArrayList<Mesure>();
		HashMap<TypeMesureOrTrame, Mesure> hashMapCalcul = new HashMap<TypeMesureOrTrame, Mesure>();

		String valeurTrameDW = trameDW.getConcatenationValeurs();
		Enregistreur enregistreur = trameDW.getEnregistreur();

		enregistreur = enregistreurService
				.findByIdWithJoinCapteurs(enregistreur.getId());
		List<Capteur> capteurs = enregistreur.getCapteurs();
		List<TypeMesureOrTrame> listTypeCapteurs = new ArrayList<TypeMesureOrTrame>();

		for (Capteur capteur : capteurs) {
			listTypeCapteurs.add(capteur.getTypeMesureOrTrame());

		}

		if (trameDW.getConcatenationValeurs().endsWith("mA")) {
			Mesure mesure = new Mesure();
			Capteur capteur = enregistreur.getCapteurs().get(0);

			logger.info("trame de type ancien : " + valeurTrameDW);
			mesure.setSignalBrut(Float.parseFloat(valeurTrameDW.substring(0,
					valeurTrameDW.indexOf("m"))));
			mesure.setTypeMesureOrTrame(capteur.getTypeMesureOrTrame());
			mesure.setDate(trameDW.getDate());
			mesure.setCapteur(capteur);
			hashMapCalcul.put(capteur.getTypeMesureOrTrame(), mesure);

			this.redirectMesuresToCalcul(hashMapCalcul, trameDW);

		} else {
			logger.info("trame de type photospace : " + valeurTrameDW);

			String[] split = valeurTrameDW.split("z");
			Map<String, String> mapMetricAndValue = new HashMap<>();

			if (split.length > 1) {

				for (int i = 0; i < split.length; i++) {
					if (i % 2 == 0) {
						mapMetricAndValue.put(split[i], split[i + 1]);
					}
				}

				if (listTypeCapteurs.contains(TypeMesureOrTrame.NIVEAUDEAU)) {
					if (mapMetricAndValue.containsKey("103Analogic-hauteur")) {
						Mesure mesure = new Mesure();

						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get("103Analogic-hauteur")));
						mesure.setTypeMesureOrTrame(TypeMesureOrTrame.NIVEAUDEAU);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeMesureOrTrame(
										TypeMesureOrTrame.NIVEAUDEAU, trameDW
												.getEnregistreur().getId()));

						hashMapCalcul.put(TypeMesureOrTrame.NIVEAUDEAU, mesure);

					} else if (mapMetricAndValue
							.containsKey("107Num-Hauteur-mm")) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get("107Num-Hauteur-mm")));
						mesure.setUnite("mm");
						mesure.setTypeMesureOrTrame(TypeMesureOrTrame.NIVEAUDEAU);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeMesureOrTrame(
										TypeMesureOrTrame.NIVEAUDEAU, trameDW
												.getEnregistreur().getId()));
						hashMapCalcul.put(TypeMesureOrTrame.NIVEAUDEAU, mesure);

					} else if (mapMetricAndValue
							.containsKey("108Num-Hauteur-cm")) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get("108Num-Hauteur-cm")));
						mesure.setUnite("cm");
						mesure.setTypeMesureOrTrame(TypeMesureOrTrame.NIVEAUDEAU);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeMesureOrTrame(
										TypeMesureOrTrame.NIVEAUDEAU, trameDW
												.getEnregistreur().getId()));
						hashMapCalcul.put(TypeMesureOrTrame.NIVEAUDEAU, mesure);

					} else if (mapMetricAndValue
							.containsKey("109Num-Hauteur-m")) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get("109Num-Hauteur-m")));
						mesure.setUnite("m");
						mesure.setTypeMesureOrTrame(TypeMesureOrTrame.NIVEAUDEAU);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeMesureOrTrame(
										TypeMesureOrTrame.NIVEAUDEAU, trameDW
												.getEnregistreur().getId()));
						hashMapCalcul.put(TypeMesureOrTrame.NIVEAUDEAU, mesure);

					}
				}

				if (listTypeCapteurs.contains(TypeMesureOrTrame.CONDUCTIVITE)) {

					if (mapMetricAndValue.containsKey("102Analogic-cond")) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get("102Analogic-cond")));
						mesure.setTypeMesureOrTrame(TypeMesureOrTrame.CONDUCTIVITE);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeMesureOrTrame(
										TypeMesureOrTrame.CONDUCTIVITE, trameDW
												.getEnregistreur().getId()));
						hashMapCalcul.put(TypeMesureOrTrame.CONDUCTIVITE,
								mesure);

					} else if (mapMetricAndValue
							.containsKey("105Num-Cond-µs/cm")) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get("105Num-Cond-µs/cm")));
						mesure.setUnite("µs/cm");
						mesure.setTypeMesureOrTrame(TypeMesureOrTrame.CONDUCTIVITE);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeMesureOrTrame(
										TypeMesureOrTrame.CONDUCTIVITE, trameDW
												.getEnregistreur().getId()));
						hashMapCalcul.put(TypeMesureOrTrame.CONDUCTIVITE,
								mesure);
						mesureService
								.conversionSignal_Conductivite(hashMapCalcul);

					} else if (mapMetricAndValue
							.containsKey("106Num-Cond-ms/cm")) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get("106Num-Cond-ms/cm")));
						mesure.setUnite("ms/cm");
						mesure.setTypeMesureOrTrame(TypeMesureOrTrame.CONDUCTIVITE);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeMesureOrTrame(
										TypeMesureOrTrame.CONDUCTIVITE, trameDW
												.getEnregistreur().getId()));
						hashMapCalcul.put(TypeMesureOrTrame.CONDUCTIVITE,
								mesure);
						mesureService
								.conversionSignal_Conductivite(hashMapCalcul);
					}

				}

				// if
				// (listTypeCapteurs.contains(TypeMesureOrTrame.PLUVIOMETRIE)) {
				//
				// if (mapMetricAndValue
				// .containsKey("110Pluvio-impuls/période")) {
				// Mesure mesure = new Mesure();
				// mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("110Pluvio-impuls/période")));
				// mesure.setUnite("impuls/periode");
				// mesure.setTypeMesureOrTrame(TypeMesureOrTrame.PLUVIOMETRIE);
				// mesure.setDate(trameDW.getDate());
				// mesure.setCapteur(capteurService
				// .findByEnregistreurAndTypeMesureOrTrame(
				// TypeMesureOrTrame.PLUVIOMETRIE, trameDW
				// .getEnregistreur().getId()));
				// hashMapCalcul.put(TypeMesureOrTrame.PLUVIOMETRIE,
				// mesure);
				//
				// } else if (mapMetricAndValue
				// .containsKey("114Impuls/period-Q-10")) {
				// Mesure mesure = new Mesure();
				// mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("114Impuls/period-Q-10")));
				// mesure.setUnite("period-Q-10");
				// mesure.setTypeMesureOrTrame(TypeMesureOrTrame.PLUVIOMETRIE);
				// mesure.setDate(trameDW.getDate());
				// mesure.setCapteur(capteurService
				// .findByEnregistreurAndTypeMesureOrTrame(
				// TypeMesureOrTrame.PLUVIOMETRIE, trameDW
				// .getEnregistreur().getId()));
				// hashMapCalcul.put(TypeMesureOrTrame.PLUVIOMETRIE,
				// mesure);
				//
				// } else if (mapMetricAndValue
				// .containsKey("115Impuls/period-Q-100")) {
				// Mesure mesure = new Mesure();
				// mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("115Impuls/period-Q-100")));
				// mesure.setUnite("period-Q-100");
				// mesure.setTypeMesureOrTrame(TypeMesureOrTrame.PLUVIOMETRIE);
				// mesure.setDate(trameDW.getDate());
				// mesure.setCapteur(capteurService
				// .findByEnregistreurAndTypeMesureOrTrame(
				// TypeMesureOrTrame.PLUVIOMETRIE, trameDW
				// .getEnregistreur().getId()));
				// hashMapCalcul.put(TypeMesureOrTrame.PLUVIOMETRIE,
				// mesure);
				//
				// } else if (mapMetricAndValue
				// .containsKey("116Impuls/period-Q-1000")) {
				// Mesure mesure = new Mesure();
				// mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("116Impuls/period-Q-1000")));
				// mesure.setUnite("period-Q-1000");
				// mesure.setTypeMesureOrTrame(TypeMesureOrTrame.PLUVIOMETRIE);
				// mesure.setDate(trameDW.getDate());
				// mesure.setCapteur(capteurService
				// .findByEnregistreurAndTypeMesureOrTrame(
				// TypeMesureOrTrame.PLUVIOMETRIE, trameDW
				// .getEnregistreur().getId()));
				//
				// hashMapCalcul.put(TypeMesureOrTrame.PLUVIOMETRIE,
				// mesure);
				//
				// }
				//
				// }

				if (listTypeCapteurs.contains(TypeMesureOrTrame.TEMPERATURE)) {

					if (mapMetricAndValue.containsKey("101Analogic-temp")) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get("101Analogic-temp")));
						mesure.setTypeMesureOrTrame(TypeMesureOrTrame.TEMPERATURE);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeMesureOrTrame(
										TypeMesureOrTrame.TEMPERATURE, trameDW
												.getEnregistreur().getId()));

						hashMapCalcul
								.put(TypeMesureOrTrame.TEMPERATURE, mesure);

					} else if (mapMetricAndValue.containsKey("104Num-Temp-C")) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get("104Num-Temp-C")));
						mesure.setUnite("°C");
						mesure.setTypeMesureOrTrame(TypeMesureOrTrame.TEMPERATURE);
						mesure.setDate(trameDW.getDate());
						hashMapCalcul
								.put(TypeMesureOrTrame.TEMPERATURE, mesure);

					}
				}
				// else if (mapMetricAndValue.containsKey("111Vent-m/s")) {
				// trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("111Vent-m/s")));
				// trameDW.setUnite("m/s");
				// trameDW.setTypeTrameDW(TypeMesureOrTrame.VENT);
				//
				// }

				// if (listTypeCapteurs.contains(TypeMesureOrTrame.DEBIT)) {
				//
				// if (mapMetricAndValue.containsKey("112Debit-l/min")) {
				// Mesure mesure = new Mesure();
				// mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("112Debit-l/min")));
				// mesure.setUnite("l/min");
				// mesure.setTypeMesureOrTrame(TypeMesureOrTrame.DEBIT);
				// mesure.setDate(trameDW.getDate());
				// mesure.setCapteur(capteurService
				// .findByEnregistreurAndTypeMesureOrTrame(
				// TypeMesureOrTrame.DEBIT, trameDW
				// .getEnregistreur().getId()));
				// hashMapCalcul.put(TypeMesureOrTrame.DEBIT, mesure);
				// }
				//
				// else if (mapMetricAndValue.containsKey("113Debit-m3/s")) {
				// Mesure mesure = new Mesure();
				// mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("113Debit-m3/s")));
				// mesure.setUnite("m3/s");
				// mesure.setTypeMesureOrTrame(TypeMesureOrTrame.DEBIT);
				// mesure.setDate(trameDW.getDate());
				// mesure.setCapteur(capteurService
				// .findByEnregistreurAndTypeMesureOrTrame(
				// TypeMesureOrTrame.DEBIT, trameDW
				// .getEnregistreur().getId()));
				// hashMapCalcul.put(TypeMesureOrTrame.DEBIT, mesure);
				//
				// }
				// }
				// else if (mapMetricAndValue
				// .containsKey("117Pression-air-hectopascal")) {
				// trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("117Pression-air-hectopascal")));
				// trameDW.setUnite("hectopascal");
				// trameDW.setTypeTrameDW(TypeMesureOrTrame.PRESSIONAIR);
				//
				// } else if (mapMetricAndValue
				// .containsKey("118Pression-air-pascal")) {
				// trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("118Pression-air-pascal")));
				// trameDW.setUnite("pascal");
				// trameDW.setTypeTrameDW(TypeMesureOrTrame.PRESSIONAIR);
				//
				// }

				else {
					throw new ServiceException(
							"ERROR DeverywareServiceImpl -- la trame n'a pas pu être analysée correctement");
				}

				this.redirectMesuresToCalcul(hashMapCalcul, trameDW);
			}
		}

		return listMesures;
	}

	private void redirectMesuresToCalcul(
			HashMap<TypeMesureOrTrame, Mesure> hashMapCalcul, TrameDW trameDW) {
		logger.info("--containsSameDate DeverywareServiceImpl -- trameDW : "
				+ trameDW);

		try {
			if (hashMapCalcul.containsKey(TypeMesureOrTrame.NIVEAUDEAU)
					&& trameDW.getEnregistreur().getOuvrage().getTypeOuvrage()
							.getNom().equalsIgnoreCase("EAUDESURFACE")) {

			}

			else if (hashMapCalcul.containsKey(TypeMesureOrTrame.NIVEAUDEAU)
					&& trameDW.getEnregistreur().getOuvrage().getTypeOuvrage()
							.getNom().equalsIgnoreCase("NAPPESOUTERRAINE")) {
				mesureService
						.conversionSignal_NiveauEauNappeSouterraine(hashMapCalcul);
			} else if (hashMapCalcul
					.containsKey(TypeMesureOrTrame.CONDUCTIVITE)) {
				mesureService.conversionSignal_Conductivite(hashMapCalcul);
			}

		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Check si une trameDW de l'enregistreur n'a pas la même date et valeur que
	 * celle parsée renvoie TRUE si c'est le cas
	 * 
	 * @param list
	 * @param trameDW
	 * @return
	 */
	private boolean trameAlreadyExists(final List<TrameDW> trameDWs,
			TrameDW trameDW) {
		logger.info("--containsSameDate DeverywareServiceImpl -- trameDW : "
				+ trameDW);

		return trameDWs
				.stream()
				.filter(t -> t.getDate().getTime() == trameDW.getDate()
						.getTime()
						&& t.getConcatenationValeurs().equals(
								trameDW.getConcatenationValeurs())).findFirst()
				.isPresent();
	}

}
