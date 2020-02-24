package com.eland.meetingRoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class MeetingRoomApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MeetingRoomApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MeetingRoomApplication.class);
	}

}
