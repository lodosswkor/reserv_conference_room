package com.eland.meetingRoom.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MEETINGROOM")
public class MeetingRoom extends Master {
		
	@Column(name="NAME")
	String name;
	
	@Column(name="USER")
	String user;
	
	@Column(name="DEPART")
	String depart;
	
	@Column(name="IN_USE")
	boolean in_use;

	@OneToMany(mappedBy="meetingRoom", cascade=CascadeType.REMOVE)
	private List<Reservation> reservations;
	
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

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void addReservations(Reservation res) {
		if(this.reservations == null){
			this.reservations = new ArrayList<>();
		}
		
		this.reservations.add(res);
	}

}
