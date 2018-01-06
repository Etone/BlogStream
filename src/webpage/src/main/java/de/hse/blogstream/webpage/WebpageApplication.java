package de.hse.blogstream.webpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WebpageApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebpageApplication.class, args);
	}
}
