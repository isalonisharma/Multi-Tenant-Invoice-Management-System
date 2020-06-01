package com.caseStudy.configuration;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Class Name: Mail Configuration
 * 
 * Use: This configuration helps spring boot to send mail
 * 
 * -->Spring boot provides a starter project and auto configuration for
 * JavaMailSender API. To use spring email features, just added the starter
 * project spring-boot-starter-mail as a dependency.[spring-boot-starter-mail]
 * 
 * -->Then configure the required spring.mail properties in the
 * application.properties.
 * 
 * @author saloni.sharma
 */
@Configuration
public class MailConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(MailConfiguration.class);

	@Autowired
	private Environment environment;

	@Bean
	public JavaMailSender getMailSender() {

		logger.info("MailConfiguration--->>getMailSender--->>Start");

		/**
		 * JavaMailSender interface: It is the sub-interface of MailSender. It supports
		 * MIME messages. It is mostly used with MimeMessageHelper class for the
		 * creation of JavaMail MimeMessage, with attachment etc. The spring framework
		 * recommends MimeMessagePreparator mechanism for using this interface.
		 * 
		 * JavaMailSenderImpl class: It provides the implementation of JavaMailSender
		 * interface. It supports JavaMail MimeMessages and Spring SimpleMailMessages.
		 */
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(environment.getProperty("spring.mail.host"));
		javaMailSender.setPort(Integer.valueOf(environment.getProperty("spring.mail.port")));
		javaMailSender.setUsername(environment.getProperty("spring.mail.username"));
		javaMailSender.setPassword(environment.getProperty("spring.mail.password"));

		// Setting the java mail properties related to SMTP(Simple Mail Transfer
		// Protocol)
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");

		javaMailSender.setJavaMailProperties(javaMailProperties);

		logger.info("MailConfiguration--->>getMailSender--->>Ended");
		
		return javaMailSender;
	}
}