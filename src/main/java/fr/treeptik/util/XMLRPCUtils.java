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
		logger.info("--getXMLRPCClient --");

		String url = env.getProperty("deveryware.url");

		logger.debug("url : " + url);
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
		String username = env.getProperty("deveryware.username");
		String password = env.getProperty("deveryware.password");
		logger.debug("xmlRpcClient : " + xmlRpcClient + " username : "
				+ username + " password : " + password);

		String sessionKey = null;
		try {

			Object[] params = new Object[] { new String(username),
					new String(password), new String(username),
					new String(password) };

			sessionKey = (String) xmlRpcClient.execute(
					"Deveryflow.openSession", params);
			logger.info("The DW key is: " + sessionKey);
		} catch (Exception exception) {
			System.err.println("JavaClient: " + exception);
			exception.printStackTrace();
		}
		return sessionKey;

	}

	public Object[] mobileList(XmlRpcClient xmlRpcClient, String sessionKey) {
		logger.info("--mobileList --");
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
		int port = 1;
		boolean compress = true;
		try {
			startDate = DateUnixConverter.stringToInt("16/01/2015 07:12:12");
			endDate = DateUnixConverter.dateToInt(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {

			Object[] params = new Object[] { sessionKey, mid, port, startDate,
					endDate, compress };

			logger.debug("--XMLRPC PARAMS--");
			logger.debug(" = sessionKey : " + sessionKey + " - mid : " + mid
					+ " - port : " + port + " - startDate : "
					+ DateUnixConverter.intToString(startDate) + "- endDate : "
					+ DateUnixConverter.intToString(endDate) + " - compress : "
					+ compress);

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
		int nbPos = 2;
		int state = -1;
		int content = 17;
		int port = 1;
		boolean compress = true;
		try {
			Object[] params = new Object[] { sessionKey, mid, nbPos, state, 17,
					0, true };

			logger.debug("--XMLRPC PARAMS--");
			logger.debug(" = sessionKey : " + sessionKey + " - mid : " + mid
					+ " - nbPos : " + nbPos + " - state : " + state
					+ " - content : " + content + " - port : " + port
					+ " - compress : " + compress);

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
		int findWay = 2;
		int startDate = 0;
		int endDate = 0;
		int nbPos = 2;
		int state = -1;
		int content = 9;
		int dataPort = 0;
		boolean compress = true;
		try {
			startDate = DateUnixConverter.stringToInt("17/01/2015 07:12:12");
			endDate = DateUnixConverter.dateToInt(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {

			Object[] params = new Object[] { sessionKey, mid, findWay,
					startDate, endDate, nbPos, state, content, dataPort, true };

			logger.debug("--XMLRPC PARAMS--");
			logger.debug(" = sessionKey : " + sessionKey + " - mid : " + mid
					+ " - findWay : " + findWay + " - startDate : "
					+ DateUnixConverter.intToString(startDate) + "- endDate : "
					+ DateUnixConverter.intToString(endDate) + " - nbPos : "
					+ nbPos + " - state : " + state + " - content : " + content
					+ " - dataPort : " + dataPort + " - compress : " + compress);

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

			logger.debug("--XMLRPC PARAMS--");
			logger.debug(" = sessionKey : " + sessionKey + " - startDate : "
					+ DateUnixConverter.intToString(startDate) + "- endDate : "
					+ DateUnixConverter.intToString(endDate));

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
		int timeout = 30;
		try {
			Object[] params = new Object[] { sessionKey, timeout };

			logger.debug("--XMLRPC PARAMS--");
			logger.debug(" = sessionKey : " + sessionKey + " - timeout : "
					+ timeout);

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
		logger.info("--waitForMessages --");
		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey);

		HashMap<String, Object> result = null;
		int timeout = 30;
		int maxMessages = 10;
		try {
			Object[] params = new Object[] { sessionKey, timeout, maxMessages };

			logger.debug("--XMLRPC PARAMS--");
			logger.debug(" = sessionKey : " + sessionKey + " - timeout : "
					+ timeout + " - maxMessages : " + maxMessages);

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
		int type = 3;
		try {
			Object[] params = new Object[] { sessionKey, type };

			logger.debug("--XMLRPC PARAMS--");
			logger.debug(" = sessionKey : " + sessionKey + " - type : " + type);

			result = (HashMap<String, Object>) xmlRpcClient.execute(
					"Deveryflow.getInfo", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
