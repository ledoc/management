package fr.treeptik.spring;

import java.beans.PropertyEditorSupport;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Client;
import fr.treeptik.service.ClientService;

@Component
public class ClientCustomEditor extends PropertyEditorSupport {

	private Logger logger = Logger.getLogger(ClientCustomEditor.class);

	@Inject
	private ClientService clientService;

	/**
	 * Sets the property value by parsing a given String. May raise
	 * java.lang.IllegalArgumentException if either the String is badly
	 * formatted or if this kind of property can't be expressed as text.
	 *
	 * @param text
	 *            The string to be parsed.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			logger.debug("setAsText  " + text);
			Client client = null;
			try {
				client = clientService.findById(Integer
						.parseInt(text));
			} catch (ServiceException e) {
				logger.error("ClientCustomEditor error", e);
				e.printStackTrace();
			}
			setValue(client);
		} catch (NumberFormatException nfe) {
			throw new RuntimeException(nfe.getMessage());
		}
	}

	/**
	 * Gets the property value as a string suitable for presentation to a human
	 * to edit.
	 *
	 * @return The property value as a string suitable for presentation to a
	 *         human to edit.
	 *         <p>
	 *         Returns "null" is the value can't be expressed as a string.
	 *         <p>
	 *         If a non-null value is returned, then the PropertyEditor should
	 *         be prepared to parse that string back in setAsText().
	 */
	@Override
	public String getAsText() {
		logger.debug(" getAsText :" + getValue());
		Client client = (Client) getValue();
		return Integer.toString(client.getId());
	}
}
