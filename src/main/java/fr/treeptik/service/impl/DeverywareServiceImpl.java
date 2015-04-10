package fr.treeptik.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.inject.Inject;

import fr.treeptik.model.assembler.EnregistreurAssembler;
import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Capteur;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.TypeCaptAlerteMesure;
import fr.treeptik.model.deveryware.DeviceState;
import fr.treeptik.service.CapteurService;
import fr.treeptik.service.DeverywareService;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.TrameDWService;
import fr.treeptik.service.TypeCaptAlerteMesureService;
import fr.treeptik.util.DateUnixConverter;
import fr.treeptik.util.XMLRPCUtils;

@Service
public class DeverywareServiceImpl implements DeverywareService {

	private Logger logger = Logger.getLogger(DeverywareServiceImpl.class);

	@Inject
	private Environment env;
	@Inject
	private XMLRPCUtils xmlRPCUtils;
	@Inject
	private EnregistreurService enregistreurService;
	@Inject
	private TrameDWService trameDWService;

	@Inject
	private TypeCaptAlerteMesureService typeCaptAlerteMesureService;

	@Inject
	private CapteurService capteurService;

	@Inject
	private MesureService mesureService;

    private EnregistreurAssembler enregistreurAssembler = new EnregistreurAssembler();

	public String openSession() throws Exception {

		String sessionKey = xmlRPCUtils.openSession();
		return sessionKey;
	}

	/**
	 * la (ou l'une) des méthodes pour récupérer l'ampérage renvoyé par tous les
	 * enregistreurs
	 * 
	 * @throws ServiceException
	 */
	@Scheduled(fixedRate = 1800000)
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


			logger.debug(history.length + " trame(s) History récupérée(s)");

			for (Object historyXmlRpc : history) {
				HashMap<String, Object> hashMapHistoryXmlRpc = (HashMap<String, Object>) historyXmlRpc;

				logger.debug(hashMapHistoryXmlRpc);

				TrameDW trameDW = this.transfertHistoryToTrameDW(enregistreur,
						hashMapHistoryXmlRpc);

				if (trameDW.getConcatenationValeurs() != null) {

					if (enregistreur.getTrameDWs() != null) {

						if (!this.trameAlreadyExists(
								enregistreur.getTrameDWs(), trameDW)) {

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
			//Enregistreur enregistreur = new Enregistreur(enregistreurHashMap);
			enregistreursFromDW.add(enregistreurAssembler.fromXmlrpcHashMap(enregistreurHashMap));

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

		String analogicTemp = env.getProperty("analogic.temp");
		String analogicCond = env.getProperty("analogic.cond");
		String analogicHauteur = env.getProperty("analogic.hauteur");
		String numTemp = env.getProperty("num.temp");
		String numCondMicroSiemensCm = env
				.getProperty("num.cond.micro.siemens.cm");
		String numCondMilliSiemensCm = env
				.getProperty("num.cond.milli.siemens.cm");
		String numHauteurMm = env.getProperty("num.hauteur.mm");
		String numHauteurCm = env.getProperty("num.hauteur.cm");
		String numHauteurM = env.getProperty("num.hauteur.m");

		TypeCaptAlerteMesure typeCaptAlerteMesureNiveauEau = typeCaptAlerteMesureService
				.findByNom("NIVEAUDEAU");
		TypeCaptAlerteMesure typeCaptAlerteMesureConductivite = typeCaptAlerteMesureService
				.findByNom("CONDUCTIVITE");
		TypeCaptAlerteMesure typeCaptAlerteMesureTemperature = typeCaptAlerteMesureService
				.findByNom("TEMPERATURE");

		ArrayList<Mesure> listMesures = new ArrayList<Mesure>();
		HashMap<TypeCaptAlerteMesure, Mesure> hashMapCalcul = new HashMap<TypeCaptAlerteMesure, Mesure>();

		String valeurTrameDW = trameDW.getConcatenationValeurs();
		Enregistreur enregistreur = trameDW.getEnregistreur();

		enregistreur = enregistreurService
				.findByIdWithJoinCapteurs(enregistreur.getId());
		List<Capteur> capteurs = enregistreur.getCapteurs();
		List<TypeCaptAlerteMesure> listTypeCapteurs = new ArrayList<TypeCaptAlerteMesure>();

		for (Capteur capteur : capteurs) {
			listTypeCapteurs.add(capteur.getTypeCaptAlerteMesure());
		}

		if (trameDW.getConcatenationValeurs().endsWith("mA")) {
			Mesure mesure = new Mesure();
			Capteur capteur = enregistreur.getCapteurs().get(0);

			logger.info("trame de type ancien : " + valeurTrameDW);
			mesure.setSignalBrut(Float.parseFloat(valeurTrameDW.substring(0,
					valeurTrameDW.indexOf("m"))));
			mesure.setTypeCaptAlerteMesure(capteur.getTypeCaptAlerteMesure());
			mesure.setDate(trameDW.getDate());
			mesure.setCapteur(capteur);
			hashMapCalcul.put(capteur.getTypeCaptAlerteMesure(), mesure);

			this.redirectMesuresToCalcul(hashMapCalcul, trameDW);

		} else {
			logger.info("trame de type photospace : " + valeurTrameDW);

			List<String> split = new ArrayList<String>();

			StringTokenizer stringTokenizer = new StringTokenizer(
					valeurTrameDW, "=_");
			while (stringTokenizer.hasMoreTokens()) {
				split.add(stringTokenizer.nextToken());
			}

			Map<String, String> mapMetricAndValue = new HashMap<>();

			if (split.size() > 1) {

				for (int i = 0; i < split.size(); i++) {
					if (i % 2 == 0) {
						mapMetricAndValue.put(split.get(i), split.get(i + 1));
					}
				}

				// 103
				if (listTypeCapteurs.contains(typeCaptAlerteMesureNiveauEau)) {
					if (mapMetricAndValue.containsKey(analogicHauteur)) {

						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get(analogicHauteur)));
						mesure.setUnite("m");
						mesure.setTypeCaptAlerteMesure(typeCaptAlerteMesureNiveauEau);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeCaptAlerteMesure(
										typeCaptAlerteMesureNiveauEau, trameDW
												.getEnregistreur().getId()));

						hashMapCalcul
								.put(typeCaptAlerteMesureNiveauEau, mesure);

						// 107
					} else if (mapMetricAndValue.containsKey(numHauteurMm)) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get(numHauteurMm)));
						mesure.setUnite("mm");
						mesure.setTypeCaptAlerteMesure(typeCaptAlerteMesureNiveauEau);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeCaptAlerteMesure(
										typeCaptAlerteMesureNiveauEau, trameDW
												.getEnregistreur().getId()));
						hashMapCalcul
								.put(typeCaptAlerteMesureNiveauEau, mesure);

						// 108
					} else if (mapMetricAndValue.containsKey(numHauteurCm)) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get(numHauteurCm)));
						mesure.setUnite("cm");
						mesure.setTypeCaptAlerteMesure(typeCaptAlerteMesureNiveauEau);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeCaptAlerteMesure(
										typeCaptAlerteMesureNiveauEau, trameDW
												.getEnregistreur().getId()));
						hashMapCalcul
								.put(typeCaptAlerteMesureNiveauEau, mesure);

						// 109
					} else if (mapMetricAndValue.containsKey(numHauteurM)) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get(numHauteurM)));
						mesure.setUnite("m");
						mesure.setTypeCaptAlerteMesure(typeCaptAlerteMesureNiveauEau);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeCaptAlerteMesure(
										typeCaptAlerteMesureNiveauEau, trameDW
												.getEnregistreur().getId()));
						hashMapCalcul
								.put(typeCaptAlerteMesureNiveauEau, mesure);

					}
				}
				// 102
				if (listTypeCapteurs.contains(typeCaptAlerteMesureConductivite)) {

					if (mapMetricAndValue.containsKey(analogicCond)) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get(analogicCond)));
						mesure.setUnite("µs/cm");
						mesure.setTypeCaptAlerteMesure(typeCaptAlerteMesureConductivite);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeCaptAlerteMesure(
										typeCaptAlerteMesureConductivite,
										trameDW.getEnregistreur().getId()));
						hashMapCalcul.put(typeCaptAlerteMesureConductivite,
								mesure);

						// 105
					} else if (mapMetricAndValue
							.containsKey(numCondMicroSiemensCm)) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get(numCondMicroSiemensCm)));
						mesure.setUnite("µs/cm");
						mesure.setTypeCaptAlerteMesure(typeCaptAlerteMesureConductivite);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeCaptAlerteMesure(
										typeCaptAlerteMesureConductivite,
										trameDW.getEnregistreur().getId()));
						hashMapCalcul.put(typeCaptAlerteMesureConductivite,
								mesure);
						mesureService
								.conversionSignal_Conductivite(hashMapCalcul);

						// 106
					} else if (mapMetricAndValue
							.containsKey(numCondMilliSiemensCm)) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get(numCondMilliSiemensCm)));
						mesure.setUnite("ms/cm");
						mesure.setTypeCaptAlerteMesure(typeCaptAlerteMesureConductivite);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeCaptAlerteMesure(
										typeCaptAlerteMesureConductivite,
										trameDW.getEnregistreur().getId()));
						hashMapCalcul.put(typeCaptAlerteMesureConductivite,
								mesure);
						mesureService
								.conversionSignal_Conductivite(hashMapCalcul);
					}

				}

				if (listTypeCapteurs.contains(typeCaptAlerteMesureTemperature)) {

					// 101
					if (mapMetricAndValue.containsKey(analogicTemp)) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get(analogicTemp)));
						mesure.setUnite("°C");
						mesure.setTypeCaptAlerteMesure(typeCaptAlerteMesureTemperature);
						mesure.setDate(trameDW.getDate());
						mesure.setCapteur(capteurService
								.findByEnregistreurAndTypeCaptAlerteMesure(
										typeCaptAlerteMesureTemperature,
										trameDW.getEnregistreur().getId()));

						hashMapCalcul.put(typeCaptAlerteMesureTemperature,
								mesure);

						// 104
					} else if (mapMetricAndValue.containsKey(numTemp)) {
						Mesure mesure = new Mesure();
						mesure.setSignalBrut(Float.parseFloat(mapMetricAndValue
								.get(numTemp)));
						mesure.setUnite("°C");
						mesure.setTypeCaptAlerteMesure(typeCaptAlerteMesureTemperature);
						mesure.setDate(trameDW.getDate());
						hashMapCalcul.put(typeCaptAlerteMesureTemperature,
								mesure);
					}
				}

				this.redirectMesuresToCalcul(hashMapCalcul, trameDW);
			}
		}

		return listMesures;
	}

	private void redirectMesuresToCalcul(
			HashMap<TypeCaptAlerteMesure, Mesure> hashMapCalcul, TrameDW trameDW)
			throws ServiceException {
		logger.info("--redirectMesuresToCalcul DeverywareServiceImpl -- trameDW : "
				+ trameDW + "types de mesure : " + hashMapCalcul.keySet());

		TypeCaptAlerteMesure typeCaptAlerteMesureNiveauEau = typeCaptAlerteMesureService
				.findByNom("NIVEAUDEAU");
		TypeCaptAlerteMesure typeCaptAlerteMesureConductivite = typeCaptAlerteMesureService
				.findByNom("CONDUCTIVITE");

		try {
			if (hashMapCalcul.containsKey(typeCaptAlerteMesureNiveauEau)
					&& trameDW.getEnregistreur().getOuvrage().getTypeOuvrage()
							.getNom().equalsIgnoreCase("EAUDESURFACE")) {
				mesureService
						.conversionSignal_NiveauEauDeSurface(hashMapCalcul);
			}
			if (hashMapCalcul.containsKey(typeCaptAlerteMesureNiveauEau)
					&& trameDW.getEnregistreur().getOuvrage().getTypeOuvrage()
							.getNom().equalsIgnoreCase("NAPPESOUTERRAINE")) {

				mesureService
						.conversionSignal_NiveauEauNappeSouterraine(hashMapCalcul);
			}
			if (hashMapCalcul.containsKey(typeCaptAlerteMesureConductivite)) {
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
	 * @param trameDWs
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
