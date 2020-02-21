package com.eland.meetingRoom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="RESERVATION")
public class Reservation extends Master{
		
	@Column(name="TITLE")
	String title;
	
	@Column(name="USERNAME")
	String userName;
	
	@Column(name="STIME")
	String sTime;
	
	@Column(name="ETIME")
	String eTime;
	
	@Column(name="DATE")
	String date;
	

	@ManyToOne
	@JoinColumn(name="meetingroom_id")
	private MeetingRoom meetingRoom;
	
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

	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	

}
