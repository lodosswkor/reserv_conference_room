package com.eland.meetingRoom.service;

import java.util.List;

import com.eland.meetingRoom.dto.ReservationDto;
import com.eland.meetingRoom.entity.Reservation;


public interface ReservationService {
	
	Reservation saveOrUpdateReservation(ReservationDto.Request resRequest);

	List<ReservationDto.Response> findAllRes();
	
	List<ReservationDto.Response> findInUseRes();
	
	List<ReservationDto.Response> findNotInUseRes();
	
	List<ReservationDto.Response> findByDate(String date);
	
	List<ReservationDto.Response> findByUserName(String userName);
	
	ReservationDto.Response convertReservationToDtoResponse(Reservation reservation);
	
	String deleteReservation(int id);

	Reservation findById(int parseInt);
	
	boolean errorCheck(ReservationDto.Request resRequest);
}
