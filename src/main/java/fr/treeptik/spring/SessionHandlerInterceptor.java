package fr.treeptik.spring;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import fr.treeptik.model.Administrateur;
import fr.treeptik.model.Client;
import fr.treeptik.service.AdministrateurService;
import fr.treeptik.service.ClientService;

@Component
public class SessionHandlerInterceptor  extends HandlerInterceptorAdapter {

	private Logger logger = Logger.getLogger(SessionHandlerInterceptor.class);
	
	@Inject
	private ClientService clientService;
	
	@Inject
	private AdministrateurService administrateurService;
	
	
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	            Object handler) throws Exception {
		 
		
		if (request.getSession().getAttribute("userSession")==null && SecurityContextHolder.getContext()
				.getAuthentication() != null) {
			Client client = null;
			Administrateur admininstrateur = null ;

			Boolean isAdmin = request.isUserInRole("ADMIN");
			logger.debug("USER ROLE ADMIN : " + isAdmin);

			String userLogin = SecurityContextHolder.getContext()
					.getAuthentication().getName();

			logger.debug("USER LOGIN : " + userLogin);

			if (isAdmin) {
				admininstrateur = administrateurService.findByLogin(userLogin);
				request.getSession().setAttribute("userSession", admininstrateur);
			} else {
				client = clientService.findByLogin(userLogin);
				request.getSession().setAttribute("userSession", client);
			}
		}
	

		return true;
		 
	 }
}
