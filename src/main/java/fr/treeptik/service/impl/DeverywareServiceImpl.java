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
	@Scheduled(fixedRate = 3600000)
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

				if (trameDW.getSignalBrut() != null) {

					if (enregistreur.getTrameDWs() != null) {

						if (!this.containsSameDate(enregistreur.getTrameDWs(),
								trameDW)) {

							trameDW.setEnregistreur(enregistreur);
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

		// byte[] stream1 = (byte[]) hashMapHistoryXmlRpc.get("stream1");
		// String stream1String = new String(stream1);
		// System.out.println("stream1 : " + stream1String);
		// byte[] stream2 = (byte[]) hashMapHistoryXmlRpc.get("stream2");
		// String stream2String = new String(stream2);
		// System.out.println("stream2 : " + stream2String);
		// byte[] stream3 = (byte[]) hashMapHistoryXmlRpc.get("stream3");
		// String stream3String = new String(stream3);
		// System.out.println("stream3 : " + stream3String);

		if (intensiteString.endsWith("mA")) {
			logger.info("trame de type ancien : " + intensiteString);
			trameDW.setSignalBrut(Float.parseFloat(intensiteString.substring(0,
					intensiteString.indexOf("m"))));
			trameDW.setTypeTrameDW(enregistreur.getTypeMesureOrTrame());

		} else {
			logger.info("trame de type photospace : " + intensiteString);

			String[] split = intensiteString.split("_");
			Map<String, String> mapMetricAndValue = new HashMap<>();

			if (split.length > 1) {

				for (int i = 0; i < split.length; i++) {
					if (i % 2 == 0) {
						mapMetricAndValue.put(split[i], split[i + 1]);
					}
				}
				if (mapMetricAndValue.containsKey("103Analogic-hauteur")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("103Analogic-hauteur")));
					trameDW.setTypeTrameDW(TypeMesureOrTrame.NIVEAUDEAU);

				} else if (mapMetricAndValue.containsKey("107Num-Hauteur-mm")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("107Num-Hauteur-mm")));
					trameDW.setUnite("mm");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.NIVEAUDEAU);

				} else if (mapMetricAndValue.containsKey("108Num-Hauteur-cm")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("108Num-Hauteur-cm")));
					trameDW.setUnite("cm");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.NIVEAUDEAU);

				} else if (mapMetricAndValue.containsKey("109Num-Hauteur-m")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("109Num-Hauteur-m")));
					trameDW.setUnite("m");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.NIVEAUDEAU);

				} else if (mapMetricAndValue.containsKey("102Analogic-cond")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("102Analogic-cond")));
					trameDW.setTypeTrameDW(TypeMesureOrTrame.CONDUCTIVITE);

				} else if (mapMetricAndValue.containsKey("105Num-Cond-µs/cm")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("105Num-Cond-µs/cm")));
					trameDW.setUnite("µs/cm");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.CONDUCTIVITE);

				} else if (mapMetricAndValue.containsKey("106Num-Cond-ms/cm")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("106Num-Cond-ms/cm")));
					trameDW.setUnite("ms/cm");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.CONDUCTIVITE);

				} else if (mapMetricAndValue
						.containsKey("110Pluvio-impuls/période")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("110Pluvio-impuls/période")));
					trameDW.setUnite("impuls/periode");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.PLUVIOMETRIE);

				} else if (mapMetricAndValue
						.containsKey("114Impuls/period-Q-10")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("114Impuls/period-Q-10")));
					trameDW.setUnite("period-Q-10");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.PLUVIOMETRIE);

				} else if (mapMetricAndValue
						.containsKey("115Impuls/period-Q-100")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("115Impuls/period-Q-100")));
					trameDW.setUnite("period-Q-100");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.PLUVIOMETRIE);

				} else if (mapMetricAndValue
						.containsKey("116Impuls/period-Q-1000")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("116Impuls/period-Q-1000")));
					trameDW.setUnite("period-Q-1000");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.PLUVIOMETRIE);

				} else if (mapMetricAndValue.containsKey("101Analogic-temp")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("101Analogic-temp")));
					trameDW.setTypeTrameDW(TypeMesureOrTrame.TEMPERATURE);

				} else if (mapMetricAndValue.containsKey("104Num-Temp-C")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("104Num-Temp-C")));
					trameDW.setUnite("°C");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.TEMPERATURE);

				}
				// else if (mapMetricAndValue.containsKey("111Vent-m/s")) {
				// trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("111Vent-m/s")));
				// trameDW.setUnite("m/s");
				// trameDW.setTypeTrameDW(TypeMesureOrTrame.VENT);
				//
				// }
				// else if (mapMetricAndValue.containsKey("112Debit-l/min")) {
				// trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
				// .get("112Debit-l/min")));
				// trameDW.setUnite("l/min");
				// trameDW.setTypeTrameDW(TypeMesureOrTrame.DEBIT);
				//
				// }

				else if (mapMetricAndValue.containsKey("113Debit-m3/s")) {
					trameDW.setSignalBrut(Float.parseFloat(mapMetricAndValue
							.get("113Debit-m3/s")));
					trameDW.setUnite("m3/s");
					trameDW.setTypeTrameDW(TypeMesureOrTrame.TEMPERATURE);

				}
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
			}
		}
		try {
			trameDW.setDate(DateUnixConverter
					.intToDate((int) hashMapHistoryXmlRpc.get("date")));
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
