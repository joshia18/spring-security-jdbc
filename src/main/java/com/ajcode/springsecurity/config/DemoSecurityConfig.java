package com.ajcode.springsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages="com.ajcode.springsecurity")
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//add reference to our security data source
	@Autowired
	private DataSource securityDataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			
		//use JDBC authentication
		auth.jdbcAuthentication().dataSource(securityDataSource);
		
	}
	
	//to configure custom login form

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests()
//			.anyRequest()
//			.authenticated()
			.antMatchers("/").hasRole("EMPLOYEE")
			.antMatchers("/leaders/**").hasRole("MANAGER")
			.antMatchers("/systems/**").hasRole("ADMIN")
			.and()
			.formLogin()
			.loginPage("/showMyLoginPage")
			//no controller mapping required for this request "/authenticateTheUser"
			//we get this for free from spring
			.loginProcessingUrl("/authenticateTheUser")
			.permitAll()
			.and()
			.logout()
			.permitAll()
			.and()
			.exceptionHandling()
			.accessDeniedPage("/access-denied");
	}
	
	
	
}
