package fr.treeptik.controller;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.treeptik.exception.ControllerException;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.service.DeverywareService;
import fr.treeptik.service.EnregistreurService;

@Controller
@RequestMapping("/deveryware")
public class DeverywareController {

	private Logger logger = Logger.getLogger(DeverywareController.class);

	@Inject
	private DeverywareService deverywareService;
	
	@Inject
	private EnregistreurService enregistreurService;

	// gps://ORANGE/+33781916177  gps://SFR/+33627872238
	@RequestMapping(headers = { "content-type=application/x-www-form-urlencoded" }, method = RequestMethod.POST, value = "/history")
	public @ResponseBody String getHistory(@RequestParam String mid)
			throws ControllerException {
		logger.info("--getHistory DeverywareController-- Enregistreur mid: "
				+ mid);
		try {
			deverywareService.getHistory();
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return "Super cool";
	}

	// gps://ORANGE/+33781916177
	@RequestMapping(method = RequestMethod.GET, value = "/wait/message")
	public @ResponseBody String waitForMessage() throws ControllerException {
		logger.info("--waitForMessage DeverywareController--");
		String response;
		try {
			response = deverywareService.waitForMessage();
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e.getMessage());
			throw new ControllerException(e.getMessage(), e);
		}
		return response;
	}
	
	
	// gps://ORANGE/+33781916177
		@RequestMapping(method = RequestMethod.GET, value = "/wait/messages")
		public void waitForMessages() throws ControllerException {
			logger.info("--waitForMessage DeverywareController--");
			try {
				deverywareService.waitForMessages();
			} catch (NumberFormatException | ServiceException e) {
				logger.error(e.getMessage());
				throw new ControllerException(e.getMessage(), e);
			}
		}
	
	
	// gps://ORANGE/+33781916177
//		@RequestMapping(method = RequestMethod.GET, value = "/add/mobile")
//		public void addMobile() throws ControllerException {
//			logger.info("--addMobile DeverywareController--");
//			
//			Enregistreur enregistreur = new Enregistreur();
//			try {
//				enregistreurService.addMobile(enregistreur);
//			} catch (NumberFormatException | ServiceException e) {
//				logger.error(e.getMessage());
//				throw new ControllerException(e.getMessage(), e);
//			}
//		}

}
