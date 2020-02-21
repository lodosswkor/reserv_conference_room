package com.eland.meetingRoom.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eland.meetingRoom.dto.MeetingRoomDto;
import com.eland.meetingRoom.entity.MeetingRoom;
import com.eland.meetingRoom.repository.MeetingRoomRepository;
import com.eland.meetingRoom.service.MeetingRoomService;
import com.eland.meetingRoom.service.ReservationService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	MeetingRoomRepository roomRepository;
	
	@Autowired
	private MeetingRoomService roomService;
	
	@Autowired
	private ReservationService resService;

	@RequestMapping(value="", method=RequestMethod.GET)
	public String findAllRoom(ModelMap model){
		List<MeetingRoomDto.Response> meetingTrue = roomService.findInUse();
		List<MeetingRoomDto.Response> meetingFalse = roomService.findNotInUse();
		
		model.addAttribute("meetingTrue",meetingTrue);
		model.addAttribute("meetingFalse",meetingFalse);
		return "admin";
	}
	
	@RequestMapping(value="/room/insert",method=RequestMethod.GET)
	public String insertMeetingRoomForm(){
		return "meetingRoom_form";
	}
	
	@RequestMapping(value="/room/insert",method=RequestMethod.POST)
	public ResponseEntity<String> saveMeetingRoom(@RequestBody MeetingRoomDto.Request roomRequest) throws Exception{
				
		if("" == roomRequest.getName() || null == roomRequest.getName()){
			return new ResponseEntity<String>("nameNull", HttpStatus.BAD_REQUEST);
		}
		
		MeetingRoom room = roomService.findByName(roomRequest.getName());
		if(null != room){
			return new ResponseEntity<String>("nameOverlap", HttpStatus.BAD_REQUEST);
		}
		if("" == roomRequest.getUser() || null == roomRequest.getUser()){
			return new ResponseEntity<String>("userNull", HttpStatus.BAD_REQUEST);
		}
		if("" == roomRequest.getDepart() || null == roomRequest.getDepart()){
			roomRequest.setDepart("자율");
		}
		
		MeetingRoom meetingRoom = roomService.saveOrUpdateMeetingRoom(roomRequest);
		return new ResponseEntity<String>(String.valueOf(meetingRoom.getId()), HttpStatus.OK);
	}

	
	@RequestMapping(value="/room/revise/{id}",method=RequestMethod.GET)
	public String findMeetingRoom(@PathVariable int id, ModelMap model) throws Exception{
		MeetingRoom meetingRoom = roomService.findById(id);
		MeetingRoomDto.Response roomResponse = roomService.convertMeetingRoomToDtoResponse(meetingRoom);
		model.addAttribute("roomResponse",roomResponse);
		return "meetingRoom_revise";
	}
	
	@RequestMapping(value="/room/revise",method=RequestMethod.PUT)
	public ResponseEntity<String> reviseMeetingRoom(@RequestParam(value="id", required=true)String id, @RequestBody MeetingRoomDto.Request roomRequest) throws Exception{
		roomRequest.setId(Integer.parseInt(id));
		if("" == roomRequest.getName() || null == roomRequest.getName()){
			return new ResponseEntity<String>("nameNull", HttpStatus.BAD_REQUEST);
		}
		MeetingRoom room = roomService.findByName(roomRequest.getName());
		if(null != room && (room.getId()!= roomRequest.getId())){
			return new ResponseEntity<String>("nameOverlap", HttpStatus.BAD_REQUEST);
		}
		if("" == roomRequest.getUser() || null == roomRequest.getUser()){
			return new ResponseEntity<String>("userNull", HttpStatus.BAD_REQUEST);
		}
		if("" == roomRequest.getDepart() || null == roomRequest.getDepart()){
			roomRequest.setDepart("자율");
		}
		
		MeetingRoom meetingRoom = roomService.saveOrUpdateMeetingRoom(roomRequest);
		return new ResponseEntity<String>(String.valueOf(meetingRoom.getId()), HttpStatus.OK);
	}
	
	@RequestMapping(value="/room",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteMeetingRoom(@RequestParam(value="id", required=true)String id){
		roomService.deleteRoom(Integer.parseInt(id));
		return String.valueOf(id);
	}
	
}
