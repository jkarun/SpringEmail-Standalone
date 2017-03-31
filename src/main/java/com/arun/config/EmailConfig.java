package com.arun.config;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

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
	public JavaMailSender javaMailSender() throws MessagingException{
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(env.getProperty("email.host")); 
		mailSender.setPort(Integer.parseInt(env.getProperty("email.port")));
		mailSender.setUsername(env.getProperty("email.user"));
		mailSender.setPassword(env.getProperty("email.password"));
		mailSender.setJavaMailProperties(this.getEmailProperties());
		/*Session session = Session.getInstance(getEmailProperties(),new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(null, null);
			}
		});*/
		//Store store = session.getStore("smtp");
		//store.connect("smtp.gmail.com", env.getProperty("email.user"), "1028668030975-24nnjkhlmudvpmkicnocmhvc3i7vh2s5.apps.googleusercontent.com");
		//mailSender.setSession(session);
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
		//prop.put("mail.imap.ssl.enable", "true");// required for Gmail
		prop.put("mail.transport.protocol", env.getProperty("mail.transport.protocol")); // Use SMTP transport protocol
		prop.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));// Use SMTP-AUTH to authenticate to SMTP server
		//prop.put("mail.smtp.auth.mechanisms",env.getProperty("mail.smtp.auth.mechanisms")); //GMail requires OAuth to not be considered "low-security"
		prop.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable")); // Use TLS to encrypt communication with SMTP server
		prop.put("mail.debug", env.getProperty("mail.debug"));
		return prop;
	}
	
}
