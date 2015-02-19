package fr.treeptik.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.deveryware.DeviceState;
import fr.treeptik.service.DeverywareService;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.MesureService;
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
	private MesureService mesureService;

	private static String sessionKey;

	@PostConstruct
	public void openSession() throws Exception {
		logger.info("-- openSession --");

		sessionKey = xmlRPCUtils.openSession();
		logger.info("sessionKey : " + sessionKey);
	}

	/**
	 * la (ou l'une) des méthodes pour récupérer l'ampérage renvoyé par un
	 * enregsitreur analogique
	 * 
	 * @param mid
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public String getHistory(String mid) throws ServiceException {
		logger.info("--getHistory DeverywareServiceImpl -- mid : " + mid);

		Enregistreur enregistreur = enregistreurService
				.findByMidWithJoinFetchTrameDWs(mid);
		Object[] history = xmlRPCUtils.getHistory(mid, sessionKey);
		logger.debug(history.length + " trame History récupérée");

		TrameDW trameDW = new TrameDW();

		for (Object historyXmlRpc : history) {
			HashMap<String, Object> hashMapHistoryXmlRpc = (HashMap<String, Object>) historyXmlRpc;
			System.out.println(hashMapHistoryXmlRpc);
			trameDW = this.transfertHistoryToTrameDW(trameDW,
					hashMapHistoryXmlRpc);

			trameDW.setEnregistreur(enregistreur);

			if (enregistreur.getTrameDWs() != null) {
				enregistreur.getTrameDWs().add(trameDW);
			} else {
				List<TrameDW> trameDWs = new ArrayList<TrameDW>();
				trameDWs.add(trameDW);
				enregistreur.setTrameDWs(trameDWs);
			}

			enregistreurService.update(enregistreur);
			
			mesureService.conversionSignalElectrique_Valeur(trameDW);
		}

		return history.toString();
	}

	// Deveryflow.mobileList
	public void enregistreurList() throws ServiceException {
		logger.info("--enregistreurList DeverywareServiceImpl --");

		Enregistreur enregistreur = null;
		Object[] listEnregistreursXMLRPC = xmlRPCUtils
				.enregistreurList(sessionKey);

		for (Object enregistreurXmlRpc : listEnregistreursXMLRPC) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> enregistreurHashMap = (HashMap<String, Object>) enregistreurXmlRpc;
			Object seamless = enregistreurHashMap.get("seamless");
			Object[] listSeamless = (Object[]) seamless;
			for (Object object : listSeamless) {
				logger.debug("seamlessClass : " + object);
			}

			enregistreur = new Enregistreur(enregistreurHashMap);
			logger.debug(enregistreur);
			enregistreurService.create(enregistreur);

		}

	}

	public void getDataHistory(String mid) throws ServiceException {
		logger.info("--getDataHistory DeverywareServiceImpl -- mid : " + mid);

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

		HashMap<String, Object> getInfo = xmlRPCUtils.getInfo(sessionKey);

		logger.debug("taille du resultat: " + getInfo.size()
				+ " waitForMessage retournée");
		logger.debug(getInfo);
		for (Entry<String, Object> object3 : getInfo.entrySet()) {
			logger.debug("key : " + object3.getKey());
			logger.debug("value : " + object3.getValue());
		}

	}

	private TrameDW transfertHistoryToTrameDW(TrameDW trameDW,
			HashMap<String, Object> hashMapHistoryXmlRpc)
			throws ServiceException {
		logger.info("--transfertHistoryToTrameDW DeverywareServiceImpl--");

		int dateInt = (int) hashMapHistoryXmlRpc.get("date");
		trameDW.setSignalBrut(xmlRPCUtils.extractAmperage(hashMapHistoryXmlRpc));
		try {
			trameDW.setDate(DateUnixConverter.intToDate(dateInt));
			trameDW.setHeure(DateUnixConverter.intToTime(dateInt));
		} catch (ParseException e) {
			logger.error("Error DeverywareServiceImpl : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return trameDW;
	}
}
