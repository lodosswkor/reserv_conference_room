package com.eland.meetingRoom.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eland.meetingRoom.dto.ReservationDto;
import com.eland.meetingRoom.dto.ReservationDto.Response;
import com.eland.meetingRoom.entity.MeetingRoom;
import com.eland.meetingRoom.entity.Reservation;
import com.eland.meetingRoom.repository.MeetingRoomRepository;
import com.eland.meetingRoom.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private MeetingRoomRepository meetingRoomRepository;

	public boolean errorCheck(ReservationDto.Request resRequest){

		MeetingRoom meetingRoom = meetingRoomRepository.findByName(resRequest.getRoomName());
		List<Reservation> reservations = reservationRepository.findByMeetingRoomIdAndDate(meetingRoom.getId(),resRequest.getDate());

		String _sTime = resRequest.getsTime();
		String _eTime = resRequest.geteTime();

		boolean temp = true;
		for(Reservation resList:reservations){

			if(resList.getId() == resRequest.getId()){
				continue;
			}
			
			// sTime<=resList.getsTime()<=eTime
			if((_sTime.compareTo(resList.getsTime()) < 0 && _eTime.compareTo(resList.getsTime()) <=0) || 
			   (_sTime.compareTo(resList.geteTime()) >= 0 && _eTime.compareTo(resList.geteTime()) > 0)){
				// 예약 가능
				temp = true;
			}

			else{
				return temp = false;
			}
		}
		return temp;
	}

	@Override
	public Reservation saveOrUpdateReservation(ReservationDto.Request resRequest) {

		// id check
		Reservation res = reservationRepository.findById(resRequest.getId());  

		// create new one
		if(null == res){
			Reservation reservation = new Reservation();

			reservation.setUserName(resRequest.getUserName());
			MeetingRoom mt= meetingRoomRepository.findByName(resRequest.getRoomName());
			reservation.setMeetingRoom(mt);
			reservation.setTitle(resRequest.getTitle());
			reservation.setsTime(resRequest.getsTime());
			reservation.seteTime(resRequest.geteTime());
			reservation.setDate(resRequest.getDate());
			reservation.setCreate_date(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()));
			reservation.setCreator(resRequest.getUserName());

			return reservationRepository.save(reservation);
		}

		// revise res information
		else{
			Reservation reservation = new Reservation();

			reservation.setId(res.getId());
			reservation.setUserName(resRequest.getUserName());
			reservation.setMeetingRoom(meetingRoomRepository.findByName(resRequest.getRoomName()));
			reservation.setTitle(resRequest.getTitle());
			reservation.setsTime(resRequest.getsTime());
			reservation.seteTime(resRequest.geteTime());
			reservation.setDate(resRequest.getDate());
			reservation.setCreate_date(res.getCreate_date());
			reservation.setUpdate_date(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()));
			reservation.setCreator(res.getCreator());
			reservation.setUpdater(resRequest.getUserName());

			return reservationRepository.save(reservation);
		}
	}


	@Override
	public List<ReservationDto.Response> findAllRes() {

		List<Reservation> reservations = reservationRepository.findAll();

		List<Response> resultRes = saveAsList(reservations);

		return resultRes;
	}

	@Override
	public List<ReservationDto.Response> findInUseRes() {

		List<Reservation> reservations = reservationRepository.findResInUse();

		List<Response> resultRes = saveAsList(reservations);

		return resultRes;
	}

	@Override
	public List<ReservationDto.Response> findNotInUseRes() {

		List<Reservation> reservations = reservationRepository.findResNotInUse();

		List<Response> resultRes = saveAsList(reservations);

		return resultRes;
	}

	@Override
	public List<ReservationDto.Response> findByDate(String date) {
		List<Reservation> reservations = reservationRepository.findByDate(date);

		List<Response> resultRes = saveAsList(reservations);

		return resultRes;
	}


	@Override
	public List<ReservationDto.Response> findByUserName(String userName) {
		List<Reservation> reservations = reservationRepository.findByUserName(userName);

		List<Response> resultRes = saveAsList(reservations);

		return resultRes;
	}

	@Override
	public ReservationDto.Response convertReservationToDtoResponse(Reservation reservation){
		ReservationDto.Response resDto = new Response();
		resDto.setId(reservation.getId());
		resDto.setUserName(reservation.getUserName());
		resDto.setRoomName(reservation.getMeetingRoom().getName());
		resDto.setRoomId(reservation.getMeetingRoom().getId());
		resDto.setTitle(reservation.getTitle());
		resDto.setsTime(reservation.getsTime());
		resDto.seteTime(reservation.geteTime());
		resDto.setDate(reservation.getDate());
		return resDto;
	}

	private List<ReservationDto.Response> saveAsList(List<Reservation> reservations){
		List<ReservationDto.Response> resultRes = new ArrayList<ReservationDto.Response>();

		for(Reservation res:reservations){
			ReservationDto.Response resDto = new Response();
			resDto = convertReservationToDtoResponse(res);

			resultRes.add(resDto);
		}
		return resultRes;
	}


	@Override
	public String deleteReservation(int id) {
		return reservationRepository.deleteById(id);
	}

	@Override
	public Reservation findById(int id) {
		return reservationRepository.findById(id);
	}

}
