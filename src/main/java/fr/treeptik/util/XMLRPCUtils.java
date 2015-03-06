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

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;

@Component
public class XMLRPCUtils {

	private Logger logger = Logger.getLogger(XMLRPCUtils.class);

	@Inject
	private Environment env;

	public XmlRpcClient getXMLRPCClient() {
		logger.info("--getXMLRPCClient --");

		String url = env.getProperty("deveryware.url");

		logger.debug("url : " + url);
		XmlRpcClient xmlRpcClient = new XmlRpcClient();
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		config.setEnabledForExtensions(true);
		xmlRpcClient.setConfig(config);
		return xmlRpcClient;
	}

	public String openSession() {
		logger.info("--openSession --");
		String username = env.getProperty("deveryware.username");
		String password = env.getProperty("deveryware.password");
		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

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

	/**
	 * <int> Deveryflow.mobileAdd (<string> sessionKey, <Mobile> mobile)
	 */
	public Integer addMobile(Enregistreur enregistreur) throws ServiceException {
		logger.info("-- addMobile --");

		String sessionKey = this.openSession();
		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

		HashMap<String, String> hashMobile = new HashMap<String, String>();
		hashMobile.put("mid", enregistreur.getMid());
		hashMobile.put("comment", enregistreur.getNom());

		Integer result = null;

		try {
			Object[] params = new Object[] { sessionKey, hashMobile };
			result = (Integer) xmlRpcClient.execute("Deveryflow.mobileAdd",
					params);

			if (result == 1) {
				logger.debug("-- Résultat de l'ajout d'enregistreur = "
						+ result + " - accepté et validé");
			}

			else if (result == 2) {
				logger.debug("-- Résultat de l'ajout d'enregistreur = "
						+ result + " - accepté mais en attente de validation");
			} else if (result == 3) {
				logger.debug("-- Résultat de l'ajout d'enregistreur = "
						+ result
						+ " - accepté mais en attente de validation par l'utilisateur du mobile");
			} else {
				logger.error("EnregistreurServiceImpl addMobile ERROR - " + result + " - REFUSE - reportez vous à la doc pour des renseignements sur le code erreur");
				throw new ServiceException(
						"EnregistreurServiceImpl addMobile ERROR - " + result + " - REFUSE - reportez vous à la documentation pour des renseignements sur le code erreur");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * <int> Deveryflow.mobileDel (<string> sessionKey, <string> mid)
	 */
	public Integer removeMobile(Enregistreur enregistreur) throws ServiceException {
		logger.info("--removeMobile --");

		String sessionKey = this.openSession();
		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

		String mid = enregistreur.getMid();

		Integer result = null;

		try {
			Object[] params = new Object[] { sessionKey, mid };
			result = (Integer) xmlRpcClient.execute("Deveryflow.mobileDel",
					params);

			if (result == 1) {
				logger.debug("-- Résultat du retrait de l'enregistreur = "
						+ result + " - accepté et validé");
			}

			else if (result == 2) {
				logger.debug("-- Résultat du retrait de l'enregistreur = "
						+ result + " - accepté mais en attente de validation");
			} else if (result == 3) {
				logger.debug("-- Résultat du retrait de l'enregistreur = "
						+ result
						+ " - accepté mais en attente de validation par l'utilisateur du mobile");
			} else {
				logger.error("EnregistreurServiceImpl removeMobile ERROR - " + result + " - REFUSE - reportez vous à la doc pour des renseignements sur le code erreur");
				throw new ServiceException(
						"EnregistreurServiceImpl removeMobile ERROR - " + result + " - REFUSE - reportez vous à la documentation pour des renseignements sur le code erreur");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Liste les mobiles du compte correspondant au couple clientName/userName.
	 * 
	 * @param sessionKey
	 * @return Object[]
	 */
	public Object[] enregistreurList(String sessionKey) {
		logger.info("--enregistreurList --");

		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

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

	public Object[] getDataHistory(String mid, String sessionKey) {
		logger.info("--getDataHistory --");

		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey + " - mid : " + mid);

		Object[] result = null;
		int startDate = 0;
		int endDate = 0;
		int port = 1;
		boolean compress = true;
		try {
			startDate = DateUnixConverter.stringToInt("05/03/2015 07:12:12");
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

	public Object[] getHistory(String mid, String sessionKey) {
		logger.info("--getHistory -- sessionKey :" + sessionKey + " - mid : "
				+ mid);

		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

		Object[] result = null;
		int nbPos = 1;
		int state = -1;
		int content = 17;
		int port = 0;
		boolean compress = false;
		try {
			Object[] params = new Object[] { sessionKey, mid, nbPos, state,
					content, port, compress };

			logger.info("XMLRPC PARAMS = sessionKey : " + sessionKey
					+ " - mid : " + mid + " - nbPos : " + nbPos + " - state : "
					+ state + " - content : " + content + " - port : " + port
					+ " - compress : " + compress);

			result = (Object[]) xmlRpcClient.execute("Deveryflow.getHistory",
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public Object[] getUnifyHistory(String mid, String sessionKey) {
		logger.info("--getUnifiedHistory --");

		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey + " - mid : " + mid);

		Object[] result = null;
		int findWay = 2;
		int startDate = 0;
		int endDate = 0;
		int nbPos = 2;
		int state = -1;
		int content = 17;
		int dataPort = 0;
		boolean compress = false;
		try {
			startDate = DateUnixConverter.stringToInt("05/03/2015 07:12:12");
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

	public Object[] getEventHistory(String mid, String sessionKey) {
		logger.info("--getEventHistory --");

		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey + " - mid : " + mid);

		Object[] result = null;
		int startDate = 0;
		int endDate = 0;
		try {
			startDate = DateUnixConverter.stringToInt("05/03/2015 07:12:12");
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
	public HashMap<String, Object> waitForMessage(String sessionKey) {
		logger.info("--waitForMessage --");

		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

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

	public Object[] waitForMessages(String sessionKey) {
		logger.info("--waitForMessages --");

		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

		logger.debug("xmlRpcClient : " + xmlRpcClient + " - sessionKey :"
				+ sessionKey);

		Object[] result = null;
		int timeout = 30;
		int maxMessages = 50;
		try {
			Object[] params = new Object[] { sessionKey, timeout, maxMessages };

			logger.debug("--XMLRPC PARAMS--");
			logger.debug(" = sessionKey : " + sessionKey + " - timeout : "
					+ timeout + " - maxMessages : " + maxMessages);

			result =  (Object[]) xmlRpcClient.execute(
					"Deveryflow.waitForMessages", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getInfo(String sessionKey) {
		logger.info("--getInfo --");

		XmlRpcClient xmlRpcClient = this.getXMLRPCClient();

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

	public float extractAmperage(HashMap<String, Object> hashMapHistoryXmlRpc) {
		byte[] arrayIntensite = (byte[]) hashMapHistoryXmlRpc.get("stream4");
		String intensiteString = new String(arrayIntensite);
		float intensite = Float.parseFloat(intensiteString.substring(0,
				intensiteString.indexOf("m")));
		return intensite;
	}

	// float intensite = Float.parseFloat(intensiteString.substring(0,
	// intensiteString.indexOf("m")));
}
