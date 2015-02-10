package fr.treeptik.conf;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@PropertySource({ "classpath:config.properties" })
@ComponentScan(basePackages = { "fr.treeptik" })
@EnableAspectJAutoProxy
@EnableWebMvc
public class ApplicationConfiguration {

	private Logger logger = Logger.getLogger(ApplicationConfiguration.class);

	@Inject
	private Environment env;

	@PostConstruct
	public void initApplication() throws IOException {
		logger.debug("Looking for Spring profiles...");
		if (env.getActiveProfiles().length == 0) {
			logger.debug("No Spring profile configured, running with default configuration");
		} else {
			for (String profile : env.getActiveProfiles()) {
				logger.debug("Detected Spring profile :" + profile);
			}
		}
	}
}
