package com.eland.meetingRoom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eland.meetingRoom.entity.MeetingRoom;
import com.eland.meetingRoom.entity.Reservation;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer>{

	@Query(value="select * from MEETINGROOM order by id", nativeQuery=true)
	public List<MeetingRoom> findAll();
	
	@Query(value="select * from MEETINGROOM where in_use=true order by id", nativeQuery=true)
	public List<MeetingRoom> findInUse();
	
	@Query(value="select * from MEETINGROOM where in_use=false order by id", nativeQuery=true)
	public List<MeetingRoom> findNotINUse();
	
	public MeetingRoom findByName(String name);

	public MeetingRoom findById(int id);
	
	@Transactional
	@Modifying
	public void delete(int id);

}
