package fr.treeptik.conf;


import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;


@Configuration
public class WebConfigurer implements ServletContextListener {
	private Logger logger = Logger.getLogger(WebConfigurer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();

		logger.debug("Context root path : " + servletContext.getRealPath("/"));
		System.setProperty("rootPath", servletContext.getRealPath("/"));
		logger.info("Web application configuration");

		logger.debug("Configuring Spring root application context");
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfiguration.class);
		rootContext.setServletContext(servletContext);
		rootContext.refresh();

		servletContext.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				rootContext);

		EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST,
				DispatcherType.FORWARD, DispatcherType.ASYNC);

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		
		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(
				DispatcherType.REQUEST, DispatcherType.FORWARD);
		FilterRegistration.Dynamic characterEncoding = servletContext
				.addFilter("characterEncoding", characterEncodingFilter);
		characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

		
		initSpring(servletContext, rootContext);
		initSpringSecurity(servletContext, disps);

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
		 * au niveau de la SecurityConfiguratin
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
		springSecurityFilter.addMappingForUrlPatterns(disps, false, "/*");
		springSecurityFilter.setAsyncSupported(true);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Destroying Web application");
		WebApplicationContext ac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sce.getServletContext());
		AnnotationConfigWebApplicationContext gwac = (AnnotationConfigWebApplicationContext) ac;
		gwac.close();
		logger.debug("Web application destroyed");

	}

}