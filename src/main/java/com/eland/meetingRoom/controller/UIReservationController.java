package com.eland.meetingRoom.controller;


import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eland.meetingRoom.dto.MeetingRoomDto;
import com.eland.meetingRoom.dto.ReservationDto;
import com.eland.meetingRoom.entity.Reservation;
import com.eland.meetingRoom.service.MeetingRoomService;
import com.eland.meetingRoom.service.ReservationService;

@Controller
@RequestMapping(value="/res")
public class UIReservationController {	
	@Autowired
	private MeetingRoomService roomService;
	
	@Autowired
	private ReservationService resService;
	
	@ModelAttribute("rooms")
	public List<MeetingRoomDto.Response> findRoom(ModelMap model){
		List<MeetingRoomDto.Response> meetingRooms = roomService.findInUse();
		return meetingRooms;
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String findReservation(String date,ModelMap model) throws Exception{
		if("" == date || null==date){
			String _date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
			date = _date;
		}
		model.addAttribute("date_value",date);
		
		List<ReservationDto.Response> reservations = resService.findByDate(date);
		model.addAttribute("dateResResponses",reservations);
		return "reservation_table";
	}
	
	@RequestMapping(value="/insert/{date}",method=RequestMethod.GET)
	public String insert(@PathVariable String date,ModelMap model) throws Exception{
		System.out.println("insert date : "+ date);
		model.addAttribute("dateValue",date);
		return "reservation_form";
	}
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public ResponseEntity<String> saveReservation(@RequestBody ReservationDto.Request resRequest) throws Exception{
		if(resRequest.getTitle() == "" || resRequest.getTitle() == null){
			return new ResponseEntity<String>("titleNull", HttpStatus.BAD_REQUEST);
		}
		
		if(resRequest.getTitle().length()<1 || resRequest.getTitle().length()>9){
			return new ResponseEntity<String>("titleLong", HttpStatus.BAD_REQUEST);
		}
		
		if(resRequest.getUserName() == "" || resRequest.getUserName() == null){
			return new ResponseEntity<String>("userNull", HttpStatus.BAD_REQUEST);
		}
		
		if(resRequest.getUserName().length()<1 || resRequest.getUserName().length()>9){
			return new ResponseEntity<String>("userLong", HttpStatus.BAD_REQUEST);
		}
		
		// time reversion
		String _sTime = resRequest.getsTime();
		String _eTime = resRequest.geteTime();
		if(_sTime.compareTo(_eTime) >= 0){
			return new ResponseEntity<String>("timeReversal", HttpStatus.BAD_REQUEST);
		}
		
		// meeting room in_use false
		if(!roomService.findByName(resRequest.getRoomName()).isIn_use()){
			return new ResponseEntity<String>("roomFalse", HttpStatus.BAD_REQUEST);
		}
		
		// time overlap
		if(!resService.errorCheck(resRequest)){
			return new ResponseEntity<String>("overlap", HttpStatus.BAD_REQUEST);
		}
		
		Reservation reservation = resService.saveOrUpdateReservation(resRequest);
		return new ResponseEntity<String>(String.valueOf(reservation.getId()), HttpStatus.OK);
	}
	
	@RequestMapping(value="/revise/{id}",method=RequestMethod.GET)
	public String revise(@PathVariable int id, ModelMap model) throws Exception{
		Reservation reservation = resService.findById(id);
		resService.convertReservationToDtoResponse(reservation);
		model.addAttribute("resResponse",reservation);
		return "reservation_revise";
	}
	
	@RequestMapping(value="/revise",method=RequestMethod.PUT)
	public ResponseEntity<String> reviseReservation(@RequestParam(value="id", required=true)String id, @RequestBody ReservationDto.Request resRequest) throws Exception{
		resRequest.setId(Integer.parseInt(id));
		if(resRequest.getTitle() == "" || resRequest.getTitle() == null){
			return new ResponseEntity<String>("titleNull", HttpStatus.BAD_REQUEST);
		}
		
		if(resRequest.getTitle().length()<1 || resRequest.getTitle().length()>9){
			return new ResponseEntity<String>("titleLong", HttpStatus.BAD_REQUEST);
		}
		
		if(resRequest.getUserName() == "" || resRequest.getUserName() == null){
			return new ResponseEntity<String>("userNull", HttpStatus.BAD_REQUEST);
		}
		
		if(resRequest.getUserName().length()<1 || resRequest.getUserName().length()>9){
			return new ResponseEntity<String>("userLong", HttpStatus.BAD_REQUEST);
		}
		
		// time reversion
		String _sTime = resRequest.getsTime();
		String _eTime = resRequest.geteTime();
		if(_sTime.compareTo(_eTime) >= 0){
			return new ResponseEntity<String>("timeReversal", HttpStatus.BAD_REQUEST);
		}
		
		// meeting room in_use false
		if(!roomService.findByName(resRequest.getRoomName()).isIn_use()){
			return new ResponseEntity<String>("roomFalse", HttpStatus.BAD_REQUEST);
		}
		
		// time overlap
		if(!resService.errorCheck(resRequest)){
			return new ResponseEntity<String>("overlap", HttpStatus.BAD_REQUEST);
		}
		
		Reservation reservation = resService.saveOrUpdateReservation(resRequest);
		return new ResponseEntity<String>(String.valueOf(reservation.getId()), HttpStatus.OK);
	}
	
	@RequestMapping(value="",method=RequestMethod.DELETE)
	public @ResponseBody String deleteReservation(@RequestParam(value="id", required=true)String id) throws Exception{
		return resService.deleteReservation(Integer.parseInt(id));
	}
}
