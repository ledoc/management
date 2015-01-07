package fr.treeptik.conf;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource({ "classpath:/config.properties" })
@EnableJpaRepositories("fr.treeptik.dao")
@EnableTransactionManagement
public class DatabaseConfiguration {

	private Logger logger = Logger.getLogger(DatabaseConfiguration.class);

	@Inject
	private Environment env;

	@Bean
	public DataSource dataSource() {
		logger.info("Configuring Datasource");
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(env.getProperty("datasource.class.name"));
		if (env.getProperty("database.url") == null
				|| "".equals(env.getProperty("database.url"))) {
			config.addDataSourceProperty("databaseName",
					env.getProperty("solices"));
		} else {
			config.addDataSourceProperty("url", env.getProperty("database.url"));
		}
		config.addDataSourceProperty("user", env.getProperty("database.user"));
		config.addDataSourceProperty("password",
				env.getProperty("database.password"));
		return new HikariDataSource(config);
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(
			final DataSource dataSource) {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		return initializer;
	}


	@Bean
	public EntityManagerFactory entityManagerFactory() {
		logger.debug("Configuring EntityManager");
		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
		lcemfb.setPersistenceProvider(new HibernatePersistenceProvider());
		lcemfb.setPersistenceUnitName("persistenceUnit");
		lcemfb.setDataSource(dataSource());
		lcemfb.setJpaDialect(new HibernateJpaDialect());
		lcemfb.setJpaVendorAdapter(jpaVendorAdapter());
		// lcemfb.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		// Properties jpaProperties = new Properties();
		// jpaProperties.put("hibernate.cache.use_second_level_cache", true);
		// jpaProperties.put("hibernate.show_sql", false);
		// jpaProperties.put("hibernate.cache.use_query_cache", true);
		// jpaProperties.put("hibernate.generate_statistics", true);
		// jpaProperties.put("hibernate.cache.provider_class",
		// "org.hibernate.cache.SingletonEhCacheProvider");
		// jpaProperties.put("hibernate.cache.provider_configuration",
		// "ehcache.xml");
		// jpaProperties.put("hibernate.cache.region.factory_class",
		// "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		// lcemfb.setJpaProperties(jpaProperties);
		lcemfb.setPackagesToScan("fr.treeptik.model");
		lcemfb.afterPropertiesSet();
		return lcemfb.getObject();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(false);
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter
				.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		return jpaVendorAdapter;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
		return jpaTransactionManager;
	}

}
