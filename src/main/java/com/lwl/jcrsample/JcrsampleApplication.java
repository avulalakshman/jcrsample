package com.lwl.jcrsample;

import org.apache.jackrabbit.commons.JcrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import javax.jcr.GuestCredentials;
import javax.jcr.Repository;
import javax.jcr.Session;

@SpringBootApplication
public class JcrsampleApplication implements CommandLineRunner {

	@Autowired
	private DocumentService documentService;
	public static void main(String[] args) {
		SpringApplication.run(JcrsampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		documentService.login();
		documentService.createAndDeleteNode();
		documentService.saveFile("MITE");
	}
}
