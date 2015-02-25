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
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.TypeEnregistreur;
import fr.treeptik.model.TypeMesureOrTrame;
import fr.treeptik.model.deveryware.DeviceState;
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
	private MesureService mesureService;

	public String openSession() throws Exception {
		logger.info("-- openSession --");

		String sessionKey = xmlRPCUtils.openSession();
		logger.info("sessionKey : " + sessionKey);
		return sessionKey;
	}

	/**
	 * la (ou l'une) des méthodes pour récupérer l'ampérage renvoyé par un
	 * enregsitreur analogique
	 * 
	 * @param mid
	 * @throws ServiceException
	 */
	@Scheduled(fixedRate = 600000)
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

		/**
		 * ON LISTE TOUS LES ENREGISTREURS REPERTORIES
		 */

		List<Enregistreur> enregistreurList = this.enregistreurList(sessionKey);

		for (Enregistreur enregistreur : enregistreurList) {
			enregistreur = enregistreurService
					.findByMidWithJoinFetchTrameDWs(enregistreur.getMid());
			Object[] history = xmlRPCUtils.getHistory(enregistreur.getMid(),
					sessionKey);

			// Enregistreur enregistreur = enregistreurService
			// .findByMidWithJoinFetchTrameDWs("gps://ORANGE/+33781916177");
			// Object[] history =
			// xmlRPCUtils.getHistory("gps://ORANGE/+33781916177",
			// sessionKey);
			logger.debug(history.length + " trame History récupérée");

			TrameDW trameDW = new TrameDW();

			for (Object historyXmlRpc : history) {
				HashMap<String, Object> hashMapHistoryXmlRpc = (HashMap<String, Object>) historyXmlRpc;

				logger.debug(hashMapHistoryXmlRpc);

				trameDW = this.transfertHistoryToTrameDW(enregistreur, trameDW,
						hashMapHistoryXmlRpc);

				if (enregistreur.getTrameDWs() != null) {

					if (!this.containsSameDate(enregistreur.getTrameDWs(),
							trameDW)) {

						trameDW.setEnregistreur(enregistreur);
						trameDW.setTypeTrameDW(enregistreur
								.getTypeMesureOrTrame());
						trameDW = trameDWService.create(trameDW);
						trameDW = trameDWService.findById(trameDW.getId());
						enregistreur.getTrameDWs().add(trameDW);

						if (trameDW.getTypeTrameDW() == TypeMesureOrTrame.CONDUCTIVITE) {
							mesureService
									.conversionSignalElectrique_Conductivite(trameDW);
						} else if (trameDW.getTypeTrameDW() == TypeMesureOrTrame.NIVEAUDEAU) {
							mesureService
									.conversionSignalElectrique_CoteAltimetrique(trameDW);

						} else {
							logger.error("Error DeverywareServiceImpl : ");
							throw new ServiceException(
									"ERROR DeverywareServiceImpl -- Le type de trame n'est pas reconnu par l'application");
						}

					} else {

						logger.debug("Pas de nouvelle trameDW a enregistrée");
					}

				} else {
					trameDW.setEnregistreur(enregistreur);
					trameDW.setTypeTrameDW(enregistreur.getTypeMesureOrTrame());
					trameDW = trameDWService.create(trameDW);
					trameDW = trameDWService.findById(trameDW.getId());

					List<TrameDW> trameDWs = new ArrayList<TrameDW>();
					trameDWs.add(trameDW);
					enregistreur.setTrameDWs(trameDWs);

					if (trameDW.getTypeTrameDW() == TypeMesureOrTrame.CONDUCTIVITE) {
						mesureService
								.conversionSignalElectrique_Conductivite(trameDW);
					} else if (trameDW.getTypeTrameDW() == TypeMesureOrTrame.NIVEAUDEAU) {
						mesureService
								.conversionSignalElectrique_CoteAltimetrique(trameDW);

					} else {
						logger.error("Error DeverywareServiceImpl : ");
						throw new ServiceException(
								"ERROR DeverywareServiceImpl -- Le type de trame n'est pas reconnu par l'application");
					}

				}
			}
		}
	}

	// Deveryflow.mobileList
	public List<Enregistreur> enregistreurList(String sessionKey)
			throws ServiceException {
		logger.info("--enregistreurList DeverywareServiceImpl --");

		Enregistreur enregistreur = null;

		// String sessionKey;
		// try {
		// sessionKey = this.openSession();
		// } catch (Exception e) {
		// logger.error("Error DeverywareServiceImpl : " + e);
		// throw new ServiceException(e.getLocalizedMessage(), e);
		// }

		Object[] listEnregistreursXMLRPC = xmlRPCUtils
				.enregistreurList(sessionKey);

		List<Enregistreur> enregistreursFromDW = new ArrayList<Enregistreur>();

		for (Object enregistreurXmlRpc : listEnregistreursXMLRPC) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> enregistreurHashMap = (HashMap<String, Object>) enregistreurXmlRpc;
			// Object seamless = enregistreurHashMap.get("seamless");
			// Object[] listSeamless = (Object[]) seamless;
			// for (Object object : listSeamless) {
			// logger.debug("seamlessClass : " + object);
			// }

			enregistreur = new Enregistreur(enregistreurHashMap);

			/**
			 * 
			 * TODO REMOVE §§§§§§§§§§§§§§§!!!!!!!!!!!!§§§§§§§§§§§§§§
			 * 
			 */

			if (enregistreurService.findByMid(enregistreur.getMid()) != null) {
				logger.debug("l'enregistreur n° " + enregistreur.getMid()
						+ " est déjà répertorié");

			} else {

				enregistreur.setTypeEnregistreur(TypeEnregistreur.ANALOGIQUE);
				enregistreur
						.setTypeMesureOrTrame(TypeMesureOrTrame.CONDUCTIVITE);

				enregistreurService.create(enregistreur);

				logger.debug(" nouvel enregistreur répertorié : "
						+ enregistreur);
			}
			enregistreursFromDW.add(enregistreur);

			/**
			 * 
			 * TODO REMOVE §§§§§§§§§§§§§§§!!!!!!!!!!!!§§§§§§§§§§§§§§
			 * 
			 */

		}
		return enregistreursFromDW;

	}

	public void getDataHistory(String mid) throws ServiceException {
		logger.info("--getDataHistory DeverywareServiceImpl -- mid : " + mid);

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		Object[] dataArray = xmlRPCUtils.getDataHistory(mid, sessionKey);

		logger.debug(dataArray.length + " DeviceData retournée");
		DeviceState deviceState;
		for (Object dataXmlRpc : dataArray) {

			@SuppressWarnings("unchecked")
			HashMap<String, Object> hashMapDataXmlRpc = (HashMap<String, Object>) dataXmlRpc;
			deviceState = new DeviceState(hashMapDataXmlRpc);
			logger.debug(deviceState);
		}
	}

	public void getUnifyHistory(String mid) throws ServiceException {
		logger.info("--getUnifyHistory DeverywareServiceImpl -- mid : " + mid);

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		Object[] unifyHistoryArray = xmlRPCUtils.getUnifyHistory(mid,
				sessionKey);

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

	public void getEventHistory(String mid) throws ServiceException {
		logger.info("--getEventHistory DeverywareServiceImpl -- mid : " + mid);

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		Object[] eventHistoryArray = xmlRPCUtils.getEventHistory(mid,
				sessionKey);

		logger.debug(eventHistoryArray.length + " eventHistoryArray retournée");
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

	public void waitForMessages() throws Exception {
		logger.info("--waitForMessages DeverywareServiceImpl--");

		String sessionKey;
		try {
			sessionKey = this.openSession();
		} catch (Exception e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		HashMap<String, Object> waitForMessages = xmlRPCUtils
				.waitForMessages(sessionKey);

		logger.debug(waitForMessages.size() + " waitForMessages retournée");
		logger.debug(waitForMessages);
		for (Entry<String, Object> object3 : waitForMessages.entrySet()) {
			logger.debug("key : " + object3.getKey());
			if (object3.getKey().contains("ate")) {
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
			TrameDW trameDW, HashMap<String, Object> hashMapHistoryXmlRpc)
			throws ServiceException {
		logger.info("--transfertHistoryToTrameDW DeverywareServiceImpl--");

		byte[] arrayIntensite = (byte[]) hashMapHistoryXmlRpc.get("stream4");
		String intensiteString = new String(arrayIntensite);

		if (intensiteString.endsWith("mA")) {
			logger.info("trame de type ancien : " + intensiteString);
			trameDW.setSignalBrut(Float.parseFloat(intensiteString.substring(0,
					intensiteString.indexOf("m"))));

		} else {
			logger.info("trame de type photospace : " + intensiteString);

			String[] split = intensiteString.split(" ");
			Map<String, String> mapMetricAndValue = new HashMap<>();

			for (int i = 0; i < split.length; i++) {
				if (i % 2 == 0) {
					mapMetricAndValue.put(split[i], split[i + 1]);
				}

			}

			switch (enregistreur.getTypeMesureOrTrame().getDescription()) {
			case "niveau d'eau":

				if (mapMetricAndValue.containsKey("103Analogic-hauteur")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("103Analogic-hauteur")));
				}
				if (mapMetricAndValue.containsKey("107Num-Hauteur-mm")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("107Num-Hauteur-mm")));
				}
				if (mapMetricAndValue.containsKey("108Num-Hauteur-cm")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("108Num-Hauteur-cm")));
				}
				if (mapMetricAndValue.containsKey("109Num-Hauteur-m")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("109Num-Hauteur-m")));
				}

				break;
			case "conductivité":

				if (mapMetricAndValue.containsKey("102Analogic-cond")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("102Analogic-cond")));
				}
				if (mapMetricAndValue.containsKey("105Num-Cond-µs/cm")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("105Num-Cond-µs/cm")));
				}

				if (mapMetricAndValue.containsKey("106Num-Cond-ms/cm")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("106Num-Cond-ms/cm")));
				}

				break;
			case "pluviométrie":

				if (mapMetricAndValue.containsKey("110Pluvio-impuls/période")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("110Pluvio-impuls/période")));
				}
				if (mapMetricAndValue.containsKey("114Impuls/period-Q-10")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("114Impuls/period-Q-10")));
				}
				if (mapMetricAndValue.containsKey("115Impuls/period-Q-100")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("115Impuls/period-Q-100")));
				}
				if (mapMetricAndValue.containsKey("116Impuls/period-Q-1000")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("116Impuls/period-Q-1000")));
				}

				break;
			case "température":
				if (mapMetricAndValue.containsKey("101Analogic-temp")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("101Analogic-temp")));
				}
				if (mapMetricAndValue.containsKey("104Num-Temp-C")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("104Num-Temp-C")));
				}

				break;

			default:
				throw new ServiceException(
						"ERROR DeverywareServiceImpl -- la trame n'a pas pu être analysée correctement");
			}

		}
		int dateInt = (int) hashMapHistoryXmlRpc.get("date");
		try {
			trameDW.setDate(DateUnixConverter.intToDate(dateInt));
		} catch (ParseException e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return trameDW;
	}

	/**
	 * Check si une trameDW de l'enregistreur n'a pas la même date que celle
	 * parsée renvoie TRUE si c'est le cas
	 * 
	 * @param list
	 * @param trameDW
	 * @return
	 */
	public boolean containsSameDate(final List<TrameDW> trameDWs,
			TrameDW trameDW) {
		logger.info("--containsSameDate DeverywareServiceImpl -- trameDW : "
				+ trameDW);

		return trameDWs
				.stream()
				.filter(t -> t.getDate().getTime() == trameDW.getDate()
						.getTime()
						&& t.getSignalBrut().equals(trameDW.getSignalBrut()))
				.findFirst().isPresent();
	}

}
