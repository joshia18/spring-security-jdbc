package com.ajcode.springsecurity.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.ajcode.springsecurity")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoAppConfig {
	
	//set up variable to hold the properties
	@Autowired
	private Environment env;
	
	//set up a logger for diagnostics
	private Logger logger = Logger.getLogger(getClass().getName());
	
	//define a bean for internal resource view resolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	//define a bean for security data source
	
	@Bean
	public DataSource securityDataSource() {
		//create connection pool
		//set JDBC driver class
		//log connection props
		//set database connection props
		//set connection pool props
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
		
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		
		//logging
		logger.info(">>> jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info(">>> jdbc.user=" + env.getProperty("jdbc.user"));
		
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));
		
		securityDataSource.setInitialPoolSize(getInt("connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getInt("connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getInt("connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getInt("connection.pool.maxIdleTime"));
		
		return securityDataSource;
	}
	
	//need a helper method
	//read environment property and convert to integer
	
	private int getInt(String prop) {
		String propVal = env.getProperty(prop);
		
		int intPropVal = Integer.parseInt(propVal);
		
		return intPropVal;
	}
}
