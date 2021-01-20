package com.analyticserver.analyticsServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(scanBasePackages={"com.analyticserver.analyticsServer"})
public class AnalyticsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsServerApplication.class, args);
	}

}
