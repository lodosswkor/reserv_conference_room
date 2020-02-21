package com.eland.meetingRoom.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeetingRoomDto {

	public static class Request{
		
		@JsonProperty("id")
		@NotNull
		int id;
		
		@JsonProperty("name")
		@NotNull
		String name;
		
		@JsonProperty("user")
		@NotNull
		String user;
		
		@JsonProperty("depart")
		String depart;
		
		@JsonProperty("in_use")
		boolean in_use;
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getDepart() {
			return depart;
		}

		public void setDepart(String depart) {
			this.depart = depart;
		}

		public boolean isIn_use() {
			return in_use;
		}

		public void setIn_use(boolean in_use) {
			this.in_use = in_use;
		}
		
	}
	
	public static class Response {

		@JsonProperty("id")
		int id;
		
		@JsonProperty("name")
		String name;
		
		@JsonProperty("user")
		String user;
		
		@JsonProperty("depart")
		String depart;
		
		@JsonProperty("in_use")
		boolean in_use;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getDepart() {
			return depart;
		}

		public void setDepart(String depart) {
			this.depart = depart;
		}

		public boolean isIn_use() {
			return in_use;
		}

		public void setIn_use(boolean in_use) {
			this.in_use = in_use;
		}		
		
	}
}
