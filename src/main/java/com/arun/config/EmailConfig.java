package com.arun.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan(basePackages="com.arun")
public class EmailConfig {
	
	@Bean
	public JavaMailSender javaMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
//		mailSender.setHost("smtp.gmail.com"); // for gmail
		mailSender.setHost("smtp.live.com"); // for hotmail
		mailSender.setPort(587);
		mailSender.setUsername("arun.jk@hotmail.com");
		mailSender.setPassword("password for your mail");
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true"); 
//		javaMailProperties.put("mail.smtp.startssl.enable", "true"); 
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");
		
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
	}
	
}
