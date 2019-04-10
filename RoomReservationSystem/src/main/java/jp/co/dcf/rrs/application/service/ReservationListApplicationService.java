package jp.co.dcf.rrs.application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.dcf.rrs.domain.entity.ReservationTblEntity;
import jp.co.dcf.rrs.domain.repository.ReservationTblRepository;

@Service
public class ReservationListApplicationService {
	@Autowired
	ReservationTblRepository reservationTblRepository;

	public List<ReservationTblEntity> getAllReservationList() {
		return reservationTblRepository.findAll();
	}
	
	public List<ReservationTblEntity> getReservationListByUseDate(LocalDate useDate){
		return reservationTblRepository.findByUseDate(useDate);
	}
	
	public List<ReservationTblEntity> getTimeOverlapReservationList(LocalDate useDate, LocalTime useStartTime, LocalTime useEndTime){
		return reservationTblRepository.findTimeOverlapReservation(useDate, useStartTime, useEndTime);
	}
}