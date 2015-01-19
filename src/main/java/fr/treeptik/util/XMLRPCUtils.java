package fr.treeptik.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class XMLRPCUtils {

	private Logger logger = Logger.getLogger(XMLRPCUtils.class);

	@Inject
	private Environment env;

	public XmlRpcClient getXMLRPCClient() {
		logger.debug("--getXMLRPCClient --");

		String url = env.getProperty("deveryware.url");

		XmlRpcClient server = new XmlRpcClient();
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		config.setEnabledForExtensions(true);

		server.setConfig(config);
		return server;
	}

	public String openSession(XmlRpcClient xmlRpcClient) {
		logger.info("--openSession --");
		logger.debug("xmlRpcClient : " + xmlRpcClient);

		String username = env.getProperty("deveryware.username");
		String password = env.getProperty("deveryware.password");
		String sessionKey = null;

		try {

			Object[] params = new Object[] { new String(username),
					new String(password), new String(username),
					new String(password) };

			sessionKey = (String) xmlRpcClient.execute(
					"Deveryflow.openSession", params);

			System.out.println("The key is: " + sessionKey);

		} catch (Exception exception) {
			System.err.println("JavaClient: " + exception);
			exception.printStackTrace();
		}
		return sessionKey;

	}

	public Object[] getMobiles(XmlRpcClient xmlRpcClient, String sessionKey) {
		logger.info("--getMobiles --");
		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey);

		Object[] result = null;

		try {
			Object[] params = new Object[] { sessionKey, "33", true };

			result = (Object[]) xmlRpcClient.execute("Deveryflow.mobileList",
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public Object[] getDataHistory(XmlRpcClient xmlRpcClient, String mid,
			String sessionKey) {
		logger.info("--getDataHistory --");
		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey + " - mid : " + mid);

		Object[] result = null;
		int startDate = 0;
		int endDate = 0;
		try {
			startDate = DateUnixConverter.stringToInt("16/01/2015 07:12:12");
			endDate = DateUnixConverter.dateToInt(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {

			Object[] params = new Object[] { sessionKey, mid, 1, startDate,
					endDate, true };

			result = (Object[]) xmlRpcClient.execute(
					"Deveryflow.getDataHistory", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public Object[] getHistory(XmlRpcClient xmlRpcClient, String mid,
			String sessionKey) {
		logger.info("--getHistory --");
		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey + " - mid : " + mid);

		Object[] result = null;
		int startDate = 0;
		int endDate = 0;
		try {
			startDate = DateUnixConverter.stringToInt("16/01/2015 07:12:12");
			endDate = DateUnixConverter.dateToInt(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {

			Object[] params = new Object[] { sessionKey, mid, 2, -1, 17, 0,
					true };

			result = (Object[]) xmlRpcClient.execute("Deveryflow.getHistory",
					params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public Object[] getUnifyHistory(XmlRpcClient xmlRpcClient, String mid,
			String sessionKey) {
		logger.info("--getUnifiedHistory --");
		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey + " - mid : " + mid);

		Object[] result = null;
		int startDate = 0;
		int endDate = 0;
		try {
			startDate = DateUnixConverter.stringToInt("17/01/2015 07:12:12");
			endDate = DateUnixConverter.dateToInt(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {

			Object[] params = new Object[] { sessionKey, mid, 2, startDate,
					endDate, 10, -1, 9, 0, true };

			result = (Object[]) xmlRpcClient.execute(
					"Deveryflow.getUnifyHistory", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public Object[] getEventHistory(XmlRpcClient xmlRpcClient, String mid,
			String sessionKey) {
		logger.info("--getEventHistory --");
		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey + " - mid : " + mid);

		Object[] result = null;
		int startDate = 0;
		int endDate = 0;
		try {
			startDate = DateUnixConverter.stringToInt("16/01/2015 07:12:12");
			endDate = DateUnixConverter.dateToInt(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {

			Object[] params = new Object[] { sessionKey, startDate, endDate };

			result = (Object[]) xmlRpcClient.execute(
					"Deveryflow.getEventHistory", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> waitForMessage(XmlRpcClient xmlRpcClient,
			String sessionKey) {
		logger.info("--waitForMessage --");
		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey);

		HashMap<String, Object> result = null;
		try {

			Object[] params = new Object[] { sessionKey, 30 };

			result = (HashMap<String, Object>) xmlRpcClient.execute(
					"Deveryflow.waitForMessage", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> waitForMessages(XmlRpcClient xmlRpcClient,
			String sessionKey) {
		logger.info("--waitForMessage --");
		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey);

		HashMap<String, Object> result = null;
		try {

			Object[] params = new Object[] { sessionKey, 30, 30 };

			result = (HashMap<String, Object>) xmlRpcClient.execute(
					"Deveryflow.waitForMessages", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getInfo(XmlRpcClient xmlRpcClient,
			String sessionKey) {
		logger.info("--getInfo --");
		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey);

		HashMap<String, Object> result = null;
		try {

			Object[] params = new Object[] { sessionKey, 3 };

			result = (HashMap<String, Object>) xmlRpcClient.execute(
					"Deveryflow.getInfo", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
