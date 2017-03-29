package com.arun.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@ComponentScan(basePackages="com.arun")
@PropertySource("classpath:emailconfig.properties")
public class EmailConfig {
	
	@Autowired
	Environment env;
	
	@Bean
	public JavaMailSender javaMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(env.getProperty("email.host")); 
		mailSender.setPort(Integer.parseInt(env.getProperty("email.port")));
		mailSender.setUsername(env.getProperty("email.user"));
		mailSender.setPassword(env.getProperty("email.password"));
		mailSender.setJavaMailProperties(this.getEmailProperties());
		return mailSender;
	}
	
	@Bean
	public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration(){
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("classpath:/templates/");
		return bean;
	} 
	
	private Properties getEmailProperties(){
		Properties prop = new Properties();
		prop.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable")); 
//		prop.put("mail.smtp.startssl.enable", env.getProperty("mail.smtp.startssl.enable")); 
		prop.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
		prop.put("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
		prop.put("mail.debug", env.getProperty("mail.debug"));
		return prop;
	}
	
}
