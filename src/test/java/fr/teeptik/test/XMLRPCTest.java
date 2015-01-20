package fr.teeptik.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.treeptik.conf.ApplicationConfiguration;
import fr.treeptik.conf.ApplicationInitializer;
import fr.treeptik.conf.DispatcherServletConfiguration;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.deveryware.DeviceState;
import fr.treeptik.model.deveryware.Mobile;
import fr.treeptik.service.MobileService;
import fr.treeptik.service.TrameDWService;
import fr.treeptik.util.DateUnixConverter;
import fr.treeptik.util.XMLRPCUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class,
		ApplicationConfiguration.class, DispatcherServletConfiguration.class })
public class XMLRPCTest {

	private Logger logger = Logger.getLogger(XMLRPCTest.class);
	@Inject
	private XMLRPCUtils xmlRPCUtils;
	@Inject
	private MobileService mobileService;
	@Inject
	private TrameDWService trameDWService;

	private String sessionKey;
	private static final String MID = "gps://ORANGE/+33781916177";

	@Before
	public void openSession() {
		sessionKey = xmlRPCUtils.openSession();
	}

	// @Test
	public void openSessionTest() throws Exception {
		logger.info("--openSession --");

		String sessionKey = xmlRPCUtils.openSession();
		logger.info("sessionKey : " + sessionKey);
	}

	// @Test
	public void mobileListTest() throws Exception {

		Object[] listMobilesXMLRPC = xmlRPCUtils.mobileList(sessionKey);

		Mobile mobile = null;
		for (Object mobileXmlRpc : listMobilesXMLRPC) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> mobileHashMap = (HashMap<String, Object>) mobileXmlRpc;
			Object seamless = mobileHashMap.get("seamless");
			Object[] listSeamless = (Object[]) seamless;
			for (Object object : listSeamless) {
				System.out.println("seamlessClass : " + object);
			}

			mobile = new Mobile(mobileHashMap);
			logger.info(mobile);
		}
	}

	// @Test
	public void getDataHistoryTest() throws Exception {

		Object[] dataArray = xmlRPCUtils.getDataHistory(MID, sessionKey);

		System.out.println(dataArray.length + " DeviceData retournée");
		DeviceState deviceState;
		for (Object dataXmlRpc : dataArray) {

			@SuppressWarnings("unchecked")
			HashMap<String, Object> hashMapDataXmlRpc = (HashMap<String, Object>) dataXmlRpc;
			deviceState = new DeviceState(hashMapDataXmlRpc);
			System.out.println(deviceState);
		}
	}

	@Test
	public void getHistoryTest() throws Exception {
		Mobile mobile = new Mobile();
		mobile = mobileService.findByMid(MID);

		Object[] history = xmlRPCUtils.getHistory(MID, sessionKey);
System.out.println(history.length);
		TrameDW trameDW = new TrameDW();
		for (Object dataXmlRpc : history) {
System.out.println("coucou");
			@SuppressWarnings("unchecked")
			HashMap<String, Object> hashMapHistoryXmlRpc = (HashMap<String, Object>) dataXmlRpc;
			int dateInt = (int) hashMapHistoryXmlRpc.get("date");

			trameDW.setDate(DateUnixConverter.intToDate(dateInt));
			trameDW.setHeure(DateUnixConverter.intToTime(dateInt));
			trameDW.setMobile(mobile);
			trameDW.setSignalBrut(xmlRPCUtils
					.extractAmperage(hashMapHistoryXmlRpc));

			logger.info(trameDW.toString());
			trameDWService.create(trameDW);
		}
	}

	// @Test
	public void getUnifyHistoryTest() throws Exception {

		Object[] unifyHistoryArray = xmlRPCUtils.getUnifyHistory(MID,
				sessionKey);

		System.out
				.println(unifyHistoryArray.length + " unifyHistory retournée");
		System.out.println(unifyHistoryArray);
		for (Object unifyHistoryXmlRpc : unifyHistoryArray) {
			System.out.println(unifyHistoryXmlRpc);
			@SuppressWarnings("unchecked")
			HashMap<String, Object> hashMapunifyHistoryXmlRpc = (HashMap<String, Object>) unifyHistoryXmlRpc;
			for (Entry<String, Object> object3 : hashMapunifyHistoryXmlRpc
					.entrySet()) {
				System.out.println("key : " + object3.getKey());
				if (object3.getKey().equals("alertList")) {
					@SuppressWarnings("unchecked")
					Object[] alertList = (Object[]) object3.getValue();
					for (Object object : alertList) {
						System.out.println("le beau array alertlist : "
								+ object);
					}
				}
				System.out.println("value : " + object3.getValue());
			}

			@SuppressWarnings("unchecked")
			Object[] deviceDataList = (Object[]) hashMapunifyHistoryXmlRpc
					.get("deviceDataList");
			for (Object object : deviceDataList) {
				System.out.println(object);
			}
		}
	}

	// @Test
	public void getEventHistoryTest() throws Exception {

		Object[] eventHistoryArray = xmlRPCUtils.getEventHistory(MID,
				sessionKey);

		System.out.println(eventHistoryArray.length
				+ " eventHistoryArray retournée");
		System.out.println(eventHistoryArray);
		for (Object eventHistoryXmlRpc : eventHistoryArray) {
			System.out.println(eventHistoryXmlRpc);
			@SuppressWarnings("unchecked")
			HashMap<String, Object> hashMapEventHistoryXmlRpc = (HashMap<String, Object>) eventHistoryXmlRpc;
			for (Entry<String, Object> object3 : hashMapEventHistoryXmlRpc
					.entrySet()) {
				System.out.println(object3.getKey());
				System.out.println(object3.getValue());
			}

		}
	}

	// @Test
	public void waitForMessageTest() throws Exception {

		HashMap<String, Object> waitForMessage = xmlRPCUtils
				.waitForMessage(sessionKey);

		System.out.println(waitForMessage.size() + " waitForMessage retournée");
		System.out.println(waitForMessage);
		for (Entry<String, Object> object3 : waitForMessage.entrySet()) {
			System.out.println("key : " + object3.getKey());
			if (object3.getKey().equals("date")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				int dateInt = (int) object3.getValue();
				Long dateLong = (long) dateInt;
				String dateString = dateFormat
						.format(new Date(dateLong * 1000));
				System.out.println("date  ==== " + dateString);
			}
			System.out.println("value : " + object3.getValue());
		}
		/**
	 *
	 */
	}

	// @Test
	public void waitForMessagesTest() throws Exception {

		HashMap<String, Object> waitForMessages = xmlRPCUtils
				.waitForMessages(sessionKey);

		System.out.println(waitForMessages.size()
				+ " waitForMessages retournée");
		System.out.println(waitForMessages);
		for (Entry<String, Object> object3 : waitForMessages.entrySet()) {
			System.out.println("key : " + object3.getKey());
			if (object3.getKey().contains("ate")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				int dateInt = (int) object3.getValue();
				Long dateLong = (long) dateInt;
				String dateString = dateFormat
						.format(new Date(dateLong * 1000));
				System.out.println("date  ==== " + dateString);
			}
			System.out.println("value : " + object3.getValue());
		}
	}

	// @Test
	public void getInfoTest() throws Exception {

		HashMap<String, Object> getInfo = xmlRPCUtils.getInfo(sessionKey);

		System.out.println("taille du resultat: " + getInfo.size()
				+ " waitForMessage retournée");
		System.out.println(getInfo);
		for (Entry<String, Object> object3 : getInfo.entrySet()) {
			System.out.println("key : " + object3.getKey());
			System.out.println("value : " + object3.getValue());
		}

	}

}
