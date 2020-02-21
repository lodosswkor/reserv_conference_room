package com.eland.meetingRoom.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eland.meetingRoom.dto.MeetingRoomDto;
import com.eland.meetingRoom.dto.MeetingRoomDto.Request;
import com.eland.meetingRoom.dto.MeetingRoomDto.Response;
import com.eland.meetingRoom.entity.MeetingRoom;
import com.eland.meetingRoom.repository.MeetingRoomRepository;
import com.eland.meetingRoom.repository.ReservationRepository;
@Service
public class MeetingRoomServiceImpl implements MeetingRoomService{
	
	@Autowired
	private MeetingRoomRepository meetingRoomRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public List<Response> findAll() {
		List<MeetingRoom> meetingRooms = meetingRoomRepository.findAll();
		List<Response> resultRoom = saveAsList(meetingRooms);
		return resultRoom;
	}
	
	@Override
	public List<Response> findInUse() {
		List<MeetingRoom> meetingRooms = meetingRoomRepository.findInUse();
		List<Response> resultRoom = saveAsList(meetingRooms);
		return resultRoom;
	}
	
	@Override
	public List<Response> findNotInUse() {
		List<MeetingRoom> meetingRooms = meetingRoomRepository.findNotINUse();
		List<Response> resultRoom = saveAsList(meetingRooms);
		return resultRoom;
	}

	@Override
	public Response convertMeetingRoomToDtoResponse(MeetingRoom meetingRoom) {
		MeetingRoomDto.Response roomDto = new Response();
		roomDto.setId(meetingRoom.getId());
		roomDto.setName(meetingRoom.getName());
		roomDto.setDepart(meetingRoom.getDepart());
		roomDto.setUser(meetingRoom.getUser());
		roomDto.setIn_use(meetingRoom.isIn_use());
		roomDto.setUser(meetingRoom.getUser());
		
		return roomDto;
	}
	
	private List<MeetingRoomDto.Response> saveAsList(List<MeetingRoom> rooms){
		List<MeetingRoomDto.Response> resultRoom = new ArrayList<MeetingRoomDto.Response>();
		
		for(MeetingRoom room:rooms){
			MeetingRoomDto.Response roomDto = new Response();
			roomDto = convertMeetingRoomToDtoResponse(room);
			
			resultRoom.add(roomDto);
		}
		return resultRoom;
	}
	
	@Override
	public MeetingRoom saveOrUpdateMeetingRoom(MeetingRoomDto.Request roomRequest) {
		
		MeetingRoom room = meetingRoomRepository.findById(roomRequest.getId());
		
		// create new room
		if(null == room){
			MeetingRoom meetingRoom = new MeetingRoom();
			
			meetingRoom.setName(roomRequest.getName());
			meetingRoom.setDepart(roomRequest.getDepart());
			meetingRoom.setUser(roomRequest.getUser());
			meetingRoom.setIn_use(true);
			meetingRoom.setCreate_date(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()));
			meetingRoom.setCreator(roomRequest.getUser());
			
			return meetingRoomRepository.save(meetingRoom);
		}
		
		// already exist -> only 1
		else{
			MeetingRoom meetingRoom = new MeetingRoom();
			
			meetingRoom.setId(room.getId());
			meetingRoom.setDepart(roomRequest.getDepart());
			meetingRoom.setUser(roomRequest.getUser());
			meetingRoom.setName(roomRequest.getName());
			meetingRoom.setIn_use(roomRequest.isIn_use());
			meetingRoom.setCreate_date(room.getCreate_date());
			meetingRoom.setUpdate_date(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()));
			meetingRoom.setCreator(room.getCreator());
			meetingRoom.setUpdater(roomRequest.getUser());
			
			return meetingRoomRepository.save(meetingRoom);
		}
	}

	@Override
	public MeetingRoom updateMeetingRoomNotInUseById(int id) {
		MeetingRoom meetingRoom = meetingRoomRepository.findById(id);
		meetingRoom.setIn_use(false);
		meetingRoom.setUpdate_date(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()));
		return meetingRoomRepository.save(meetingRoom);
	}

	@Override
	public MeetingRoom findById(int id) {
		return meetingRoomRepository.findById(id);
	}
	
	@Override
	public void deleteRoom(int id) {
		meetingRoomRepository.delete(id);
	}

	@Override
	public MeetingRoom findByName(String name) {
		return meetingRoomRepository.findByName(name);
	}

}
