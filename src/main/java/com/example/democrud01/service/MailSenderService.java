package com.example.democrud01.service;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.EmailAdmin;
import com.example.democrud01.repository.EmailAdminRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailSenderService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private final JavaMailSender mailSender;
	
	@Autowired
	private EmailAdminRepository mailRepository;

	public MailSenderService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sender(String para, String titulo, String conteudo) {
		log.info("Enviando email para " + para);
		

		String from = env.getProperty("spring.mail.username");
		
		JavaMailSenderImpl mailSenderForTable = mailSenderForTable();
		
		var message = new SimpleMailMessage();
		
		message.setTo(para);
		message.setFrom(from);
		message.setSubject(titulo);
		message.setText(conteudo);
		if(mailSenderForTable!=null) {
			message.setFrom(mailSenderForTable.getUsername());
			mailSenderForTable.send(message);
		}else {
			mailSender.send(message);
		}
		log.info("Email enviado com sucesso!!!");
	}
	
	public JavaMailSenderImpl mailSenderForTable() {
		
		List<EmailAdmin> lista = mailRepository.findBySmtpHostNotNull();
		
		for (EmailAdmin emailData : lista) {
			JavaMailSenderImpl mailSenderTable = new JavaMailSenderImpl();
			mailSenderTable.setHost(emailData.getSmtpHost());
			mailSenderTable.setPort(emailData.getPorta());
			mailSenderTable.setUsername(emailData.getUserName());
			mailSenderTable.setPassword(emailData.getPassword());
			
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.starttls.enable", "true");
			 
			mailSenderTable.setJavaMailProperties(properties);
			
			return mailSenderTable;
		}
		
		return null;
		
		
	}
	

}
