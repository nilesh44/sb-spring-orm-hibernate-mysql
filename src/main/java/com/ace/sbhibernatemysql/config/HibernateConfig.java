package com.ace.sbhibernatemysql.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@ComponentScan
@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	// here we can set the order of creation of bean
	@Order(value = 1)
	// here we indicate to spring context create spring bean with bean name
	// "DbProperties"
	@Bean("DbProperties")
	// here we can define scope of bean
	@Scope("singleton")
	public DbProperties getDbProperties() {
		return new DbProperties();
	}
	
	// we also need to create bean of HikariConfig which contain all the
		// configuration related to our Data base
		// we also configure the pool size

		@Bean("HikariConfig")
		@DependsOn("DbProperties")
		@Scope("singleton")
		public HikariConfig getHikariConfig(DbProperties dbProperties) {
			HikariConfig config = new HikariConfig();
			config.setDriverClassName(getDbProperties().getDriverClass());
			config.setJdbcUrl(dbProperties.getUrl());
			config.setUsername(dbProperties.getUser());
			config.setPassword(dbProperties.getPassword());
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			return config;
		}

	// for configuring hikariDataSource we have to create bean of HikariDataSource
	// syntex @Bean(name="name of bean")
	@Bean(name = "HikariDataSourceBean")
	// here we say to spring container that create this bean only when depondsOn
	// bean is created
	// i.e do not create "HikariDataSourceBean" bean until and unless "HikariConfig"
	// bean is created
	// there is a condition where on bean is depends on multiple bean
	@DependsOn(value = { "HikariConfig" })
	@Scope("singleton")
	public HikariDataSource getHikariDataSource(HikariConfig hikariConfig) {
		return new HikariDataSource(hikariConfig);

	}

	@Autowired
	@DependsOn(value = { "HikariDataSourceBean" })
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		// we can add @entity anoted class
		// sessionBuilder.addAnnotatedClasses(User.class);
		// we can add scan package
		sessionBuilder.scanPackages("com.ace.sbhibernateMysqlApplication.entity");
		sessionBuilder.addProperties(getHibernateProperties());
		return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	@DependsOn(value = {"sessionFactory" })
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
	HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return properties;
	}
}