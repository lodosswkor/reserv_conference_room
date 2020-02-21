package com.eland.meetingRoom.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationDto {

	public static class Request{
		@JsonProperty("id")
		int id;
		
		@JsonProperty("roomName")
		@NotNull
		String roomName;
		
		@JsonProperty("title")
		@Size(min=1,max=9)
		@NotNull
		String title;
		
		@JsonProperty("userName")
		@Size(min=1,max=9)
		@NotNull
		String userName;
		
		@JsonProperty("sTime")
		@NotNull
		String sTime;
		
		@JsonProperty("eTime")
		@NotNull
		String eTime;

		@JsonProperty("date")
		@NotNull
		String date;
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getRoomName() {
			return roomName;
		}

		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getsTime() {
			return sTime;
		}

		public void setsTime(String sTime) {
			this.sTime = sTime;
		}

		public String geteTime() {
			return eTime;
		}

		public void seteTime(String eTime) {
			this.eTime = eTime;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}
		
	}
	
	public static class Response{

		@JsonProperty("id")
		int id;
		
		@JsonProperty("roomName")
		String roomName;
		
		@JsonProperty("title")
		String title;
		
		@JsonProperty("userName")
		String userName;
		
		@JsonProperty("sTime")
		String sTime;
		
		@JsonProperty("eTime")
		String eTime;
		
		@JsonProperty("date")
		String date;
		
		@JsonProperty("roomId")
		int roomId;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getRoomName() {
			return roomName;
		}

		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		
		
		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getsTime() {
			return sTime;
		}

		public void setsTime(String sTime) {
			this.sTime = sTime;
		}

		public String geteTime() {
			return eTime;
		}

		public void seteTime(String eTime) {
			this.eTime = eTime;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public int getRoomId() {
			return roomId;
		}

		public void setRoomId(int roomId) {
			this.roomId = roomId;
		}
		
	}
}
