package com.eland.meetingRoom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.eland.meetingRoom.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
	
	@Query(value="select * from reservation r left join meetingroom m on r.meetingroom_id=m.id where m.in_use=true",nativeQuery=true)
	public List<Reservation> findResInUse();
	
	@Query(value="select * from reservation r left join meetingroom m on r.meetingroom_id=m.id where m.in_use=false",nativeQuery=true)
	public List<Reservation> findResNotInUse();

	@Query(value="select * from reservation r left join meetingroom m on r.meetingroom_id=m.id where m.in_use=true AND r.DATE=?1",nativeQuery=true)
	public List<Reservation> findByDate(String date);
	
	@Query(value="select * from reservation r left join meetingroom m on r.meetingroom_id=m.id where m.in_use=true AND m.id=?1 AND r.DATE=?2",nativeQuery=true)
	public List<Reservation> findByMeetingRoomIdAndDate(int roomId, String date);
	
	public List<Reservation> findByUserName(String userName);
	
	public Reservation findById(int id);
	
	@Transactional
	@Modifying
	public String deleteById(int id);
	
}
