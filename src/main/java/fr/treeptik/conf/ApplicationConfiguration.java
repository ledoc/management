package fr.treeptik.conf;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({ "classpath:config.properties" })
@ComponentScan(basePackages = { "fr.treeptik" })
@Import(value = { SecurityConfiguration.class, DatabaseConfiguration.class })
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
