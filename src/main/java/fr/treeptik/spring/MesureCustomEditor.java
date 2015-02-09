package fr.treeptik.spring;

import java.beans.PropertyEditorSupport;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Mesure;
import fr.treeptik.service.MesureService;

@Component
public class MesureCustomEditor extends PropertyEditorSupport {

	private Logger logger = Logger.getLogger(MesureCustomEditor.class);

	@Inject
	private MesureService mesureService;

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
			Mesure mesure = null;
			try {
				mesure = mesureService.findById(Integer
						.parseInt(text));
			} catch (ServiceException e) {
				logger.error("MesureCustomEditor error", e);
				e.printStackTrace();
			}
			setValue(mesure);
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
		Mesure mesure = (Mesure) getValue();
		return Integer.toString(mesure.getId());
	}
}
