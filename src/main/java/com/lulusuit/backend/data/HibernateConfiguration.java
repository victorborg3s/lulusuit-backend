package com.lulusuit.backend.data;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

	@Bean
	public EntityManager entityManager() {
		return entityManagerFactory().getObject().createEntityManager();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.lulusuit.adminbackend.entity" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(hibernateProperties());

		return em;
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://192.168.99.100:32768/lulusuit");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dataSource());
		transactionManager.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());
		// transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();

		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.format_sql", "true");

//		hibernateProperties.setProperty("logging.level.org.hibernate.tool.hbm2ddl", "DEBUG");

		hibernateProperties.setProperty("hibernate.c3p0.min_size", "5");
		hibernateProperties.setProperty("hibernate.c3p0.max_size", "20");
		hibernateProperties.setProperty("hibernate.c3p0.timeout", "1800");
		hibernateProperties.setProperty("hibernate.c3p0.max_statements", "50");

		return hibernateProperties;
	}
}
