package fr.treeptik.conf;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.lightadmin.api.config.LightAdmin;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

@Order(HIGHEST_PRECEDENCE)
public class ApplicationInitializer implements WebApplicationInitializer {

	private Logger logger = Logger.getLogger(ApplicationInitializer.class);
	
	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {

		LightAdmin.configure(servletContext).baseUrl("/admin")
				.backToSiteUrl("http://lightadmin.org")
				.basePackage("fr.treeptik.lightadmin");

		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.register(ApplicationConfiguration.class);
		webApplicationContext.setServletContext(servletContext);
		webApplicationContext.refresh();
	
		servletContext.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				webApplicationContext);

		EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST,
				DispatcherType.FORWARD, DispatcherType.ASYNC);
		

		this.initSpring(servletContext, webApplicationContext);
		
		this.initSpringSecurity(servletContext, disps);
		
	}

	/**
	 * Initializes Spring and Spring MVC.
	 */
	private ServletRegistration.Dynamic initSpring(
			ServletContext servletContext,
			AnnotationConfigWebApplicationContext rootContext) {
		logger.debug("Configuring Spring Web application context");
		AnnotationConfigWebApplicationContext dispatcherServletConfiguration = new AnnotationConfigWebApplicationContext();
		dispatcherServletConfiguration.setParent(rootContext);
		dispatcherServletConfiguration
				.register(DispatcherServletConfiguration.class);

		logger.debug("Registering Spring MVC Servlet");
		ServletRegistration.Dynamic dispatcherServlet = servletContext
				.addServlet("dispatcher", new DispatcherServlet(
						dispatcherServletConfiguration));
		
		/**
		 * Catch tout ce qui n'a pas de suffixe (.jsp, .do , .html, .json)
		 * autrement dit toutes les requêtes REST mais pas api-docs et
		 * api-docs/module server, user et application donc il faut les ignorer
		 * au niveau de la SecurityConfiguration
		 */
		dispatcherServlet.addMapping("/");
		dispatcherServlet.setLoadOnStartup(1);
		dispatcherServlet.setAsyncSupported(true);
		
		return dispatcherServlet;
	}

	/**
	 * Initializes Spring Security.
	 */
	private void initSpringSecurity(ServletContext servletContext,
			EnumSet<DispatcherType> disps) {
		logger.debug("Registering Spring Security Filter");
		FilterRegistration.Dynamic springSecurityFilter = servletContext
				.addFilter("springSecurityFilterChain",
						new DelegatingFilterProxy());
		springSecurityFilter.addMappingForUrlPatterns(disps, false, "/client/*");

		springSecurityFilter.setAsyncSupported(true);
	}
}