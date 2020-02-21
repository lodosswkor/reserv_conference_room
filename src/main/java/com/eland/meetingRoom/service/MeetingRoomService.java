package com.eland.meetingRoom.service;

import java.util.List;

import javax.jdo.annotations.Transactional;

import com.eland.meetingRoom.dto.MeetingRoomDto;
import com.eland.meetingRoom.dto.ReservationDto;
import com.eland.meetingRoom.entity.MeetingRoom;

public interface MeetingRoomService {
	List<MeetingRoomDto.Response> findAll();
	
	List<MeetingRoomDto.Response> findInUse();
	
	List<MeetingRoomDto.Response> findNotInUse();
	
	MeetingRoom saveOrUpdateMeetingRoom(MeetingRoomDto.Request roomRequest);
	
	MeetingRoom updateMeetingRoomNotInUseById(int id);
	
	MeetingRoomDto.Response convertMeetingRoomToDtoResponse(MeetingRoom meetingRoom);

	MeetingRoom findById(int id);
	
	MeetingRoom findByName(String name);

	void deleteRoom(int parseInt);
	
}
