package com.kailash.springbootfileuploadapi;

import com.kailash.springbootfileuploadapi.service.FileUploadStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(FileUploadStorageProperties.class)
@EnableScheduling
public class SpringBootFileUploadApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFileUploadApiApplication.class, args);
	}
}
