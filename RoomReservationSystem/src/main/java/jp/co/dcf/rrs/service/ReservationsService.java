package jp.co.dcf.rrs.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.dcf.rrs.model.Reservation;
import jp.co.dcf.rrs.model.repository.ReservationsRepository;

@Service
public class ReservationsService {
	@Autowired
	ReservationsRepository reservationsRepository;

	public List<Reservation> findAllReservations() {
		return reservationsRepository.findAll();
	}
	
	public List<Reservation> findReservationsByUseDate(LocalDate useDate){
		return reservationsRepository.findByUseDate(useDate);
	}
	
	public List<Reservation> findTimeOverlapReservations(LocalDate useDate, LocalTime useStartTime, LocalTime useEndTime){
		return reservationsRepository.findTimeOverlapReservations(useDate, useStartTime, useEndTime);
	}
}
