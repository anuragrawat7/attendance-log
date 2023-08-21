package com.latttice.attendancelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AttendancelogApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendancelogApplication.class, args);
	}

}
