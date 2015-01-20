package fr.teeptik.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.treeptik.conf.ApplicationConfiguration;
import fr.treeptik.conf.ApplicationInitializer;
import fr.treeptik.conf.DispatcherServletConfiguration;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.deveryware.Mobile;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.MobileService;
import fr.treeptik.service.TrameDWService;
import fr.treeptik.util.DateUnixConverter;
import fr.treeptik.util.XMLRPCUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationInitializer.class,
		ApplicationConfiguration.class, DispatcherServletConfiguration.class })
public class TestCRUD {

	private Logger logger = Logger.getLogger(XMLRPCUtils.class);
	@Inject
	private MesureService mesureService;

	@Inject
	private MobileService mobileService;
	
	@Inject
	private TrameDWService trameDWService;

	@Inject
	private XMLRPCUtils xmlRPCUtils;

	// @Test
	public void testCRUDMesure() throws Exception {
		logger.info("--testCRUDMesure --");

		Random random = new Random();
		Integer random1 = random.nextInt(10);
		Integer random2 = random1 + 1;
		Integer random3 = random1 + 2;

		Mesure mesure1 = new Mesure();
		mesure1.setType("type" + random1);
		mesure1.setDate(new Date());
		mesure1.setValeur("valeur" + random1);

		mesureService.create(mesure1);
		Integer id = mesure1.getId();

		mesure1 = mesureService.findById(id);
		assertNotNull("L'objet doit exister", mesure1);

		List<Mesure> firstFindAll = mesureService.findAll();

		Mesure mesure2 = new Mesure();

		mesure2.setType("type" + random2);
		mesure2.setDate(new Date());
		mesure2.setValeur("valeur" + random2);

		mesureService.create(mesure2);

		mesure2.setType("type" + random3);
		mesure2.setDate(new Date());
		mesure2.setValeur("valeur" + random3);

		mesureService.update(mesure2);

		mesure2 = mesureService.findById(id);
		assertNotNull("Object should exist", mesure2);

		List<Mesure> secondFindAll = mesureService.findAll();

		if (firstFindAll.size() + 1 != secondFindAll.size())
			fail("La collection doit être augmenter de 1");

		mesureService.remove(mesure2);

		mesure2 = mesureService.findById(id);
		assertNull("L'objet ne doit pas exister", mesure2);

		List<Mesure> thirdFindAll = mesureService.findAll();

		if (firstFindAll.size() != thirdFindAll.size())
			fail("La collection doit avoir la même taille qu'à l'origine");
	}

	// @Test
	// public void testDownLoadDocument() throws Exception {
	// fileUploadUtils.downloadFileHandler("/home/herve/Documents/solices/deverygo-1.3.7.jar",
	// response)
	// }

	@Test
	public void testXMLRPC() throws Exception {
		logger.info("--testXMLRPC --");

		XmlRpcClient xmlRpcClient = xmlRPCUtils.getXMLRPCClient();
		String sessionKey = xmlRPCUtils.openSession(xmlRpcClient);
		/**
		 * 
		 */
		Object[] listMobiles = xmlRPCUtils.mobileList(xmlRpcClient, sessionKey);
		Mobile mobile = null;
		for (Object mobileXmlRpc : listMobiles) {
System.out.println(mobileXmlRpc);
			@SuppressWarnings("unchecked")
			HashMap<String, Object> mobileHashMap = (HashMap<String, Object>) mobileXmlRpc;
			Object seamless = mobileHashMap.get("seamless");
			Object[] listSeamless = (Object[]) seamless;
				for (Object object : listSeamless) {
					System.out.println(object);
				}

			mobile = new Mobile(mobileHashMap);
				}
//			mobile = mobileService.create(mobile);
		/**
		 * 
		 */
		// Object[] dataArray = xmlRPCUtils.getDataHistory(xmlRpcClient,
		// mobile.getMid(), sessionKey);
		//
		// System.out.println(dataArray.length + " DeviceData retournée");
		// DeviceState deviceState;
		// for (Object dataXmlRpc : dataArray) {
		//
		// @SuppressWarnings("unchecked")
		// HashMap<String, Object> hashMapDataXmlRpc = (HashMap<String, Object>)
		// dataXmlRpc;
		// deviceState = new DeviceState(hashMapDataXmlRpc);
		// System.out.println(deviceState);
		// }
		/**
		 * 
		 */
		Object[] history = xmlRPCUtils.getHistory(xmlRpcClient,
				mobile.getMid(), sessionKey);

		logger.info(history.length + " history retournée");
		System.out.println(history);
		TrameDW trameDW = new TrameDW();
		for (Object dataXmlRpc : history) {
			System.out.println(dataXmlRpc);
			@SuppressWarnings("unchecked")
			HashMap<String, Object> hashMapHistoryXmlRpc = (HashMap<String, Object>) dataXmlRpc;
			for (Entry<String, Object> object2 : hashMapHistoryXmlRpc
					.entrySet()) {
				System.out.println("key : " + object2.getKey());
				if (object2.getKey().equals("date")) {
					int dateInt = (int) object2.getValue();
					String dateString = DateUnixConverter.intToString(dateInt);
					System.out.println("date  ==== " + dateString);

					Date date = DateUnixConverter.intToDate(dateInt);
					LocalDate dateTime = new LocalDate(date);
					LocalTime localTime = new LocalTime(date);
					trameDW.setDate(dateTime.toDate());
					trameDW.setHeure(localTime.toDateTimeToday().toDate());
				} else {

					System.out.println(" value : " + object2.getValue());
					mobile= mobileService.findByMid(mobile.getMid());
					trameDW.setMobile(mobileService.findById(mobile.getId()));
					if (object2.getKey().contains("stream4")) {
						byte[] objet3 = (byte[]) object2.getValue();
						System.out.println("valeur Byte[] : "
								+ new String(objet3));
						trameDW.setSignalBrut(new String(objet3));
						
//						trameDWService.create(trameDW);
					}
				}
			}

		}

		/**
		 * 
		 */
		// Object[] unifyHistoryArray =
		// xmlRPCUtils.getUnifyHistory(xmlRpcClient,
		// mobile.getMid(), sessionKey);
		//
		// System.out.println(unifyHistoryArray.length +
		// " unifyHistory retournée");
		// System.out.println(unifyHistoryArray);
		// for (Object unifyHistoryXmlRpc : unifyHistoryArray) {
		// System.out.println(unifyHistoryXmlRpc);
		// @SuppressWarnings("unchecked")
		// HashMap<String, Object> hashMapunifyHistoryXmlRpc = (HashMap<String,
		// Object>) unifyHistoryXmlRpc;
		// for (Entry<String, Object> object3 :
		// hashMapunifyHistoryXmlRpc.entrySet()) {
		// System.out.println("key : " + object3.getKey());
		// if(object3.getKey().equals("alertList")){
		// @SuppressWarnings("unchecked")
		// Object[] alertList = (Object[]) object3.getValue();
		// for (Object object : alertList) {
		// System.out.println("le beau array alertlist : " + object);
		// }
		// }
		// System.out.println("value : " + object3.getValue());
		// }
		//
		// @SuppressWarnings("unchecked")
		// Object[] deviceDataList = (Object[]) hashMapunifyHistoryXmlRpc
		// .get("deviceDataList");
		// for (Object object : deviceDataList) {
		// System.out.println(object);
		// }
		// }
		/**
		 * 
		 */
		// Object[] eventHistoryArray =
		// xmlRPCUtils.getEventHistory(xmlRpcClient,
		// mobile.getMid(), sessionKey);
		//
		// System.out.println(eventHistoryArray.length
		// + " eventHistoryArray retournée");
		// System.out.println(eventHistoryArray);
		// for (Object eventHistoryXmlRpc : eventHistoryArray) {
		// System.out.println(eventHistoryXmlRpc);
		// @SuppressWarnings("unchecked")
		// HashMap<String, Object> hashMapEventHistoryXmlRpc = (HashMap<String,
		// Object>) eventHistoryXmlRpc;
		// for (Entry<String, Object> object3 : hashMapEventHistoryXmlRpc
		// .entrySet()) {
		// System.out.println(object3.getKey());
		// System.out.println(object3.getValue());
		// }
		//
		// }

		/**
		 * 
		 */
		// HashMap<String, Object> waitForMessage = xmlRPCUtils.waitForMessage(
		// xmlRpcClient, sessionKey);
		//
		// System.out.println(waitForMessage.size() +
		// " waitForMessage retournée");
		// System.out.println(waitForMessage);
		// for (Entry<String, Object> object3 : waitForMessage.entrySet()) {
		// System.out.println("key : " + object3.getKey());
		// if(object3.getKey().equals("date")){
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// int dateInt = (int) object3.getValue();
		// Long dateLong = (long) dateInt;
		// String dateString = dateFormat.format(new Date ( dateLong *1000));
		// System.out.println("date  ==== " + dateString);
		// }
		// System.out.println("value : " + object3.getValue());
		// }
		/**
			 * 
			 */
		// HashMap<String, Object> waitForMessages = xmlRPCUtils.waitForMessage(
		// xmlRpcClient, sessionKey);
		//
		// System.out.println(waitForMessages.size() +
		// " waitForMessages retournée");
		// System.out.println(waitForMessages);
		// for (Entry<String, Object> object3 : waitForMessages.entrySet()) {
		// System.out.println("key : " + object3.getKey());
		// if(object3.getKey().contains("ate")){
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// int dateInt = (int) object3.getValue();
		// Long dateLong = (long) dateInt;
		// String dateString = dateFormat.format(new Date ( dateLong *1000));
		// System.out.println("date  ==== " + dateString);
		// }
		// System.out.println("value : " + object3.getValue());
		// }

		/**
		 * Deveryflow.getInfo
		 */
		// HashMap<String, Object> getInfo = xmlRPCUtils.getInfo(xmlRpcClient,
		// sessionKey);
		//
		// System.out.println("taille du resultat: " + getInfo.size()
		// + " waitForMessage retournée");
		// System.out.println(getInfo);
		// for (Entry<String, Object> object3 : getInfo.entrySet()) {
		// System.out.println("key : " + object3.getKey());
		// System.out.println("value : " + object3.getValue());
		// }

	}
}
