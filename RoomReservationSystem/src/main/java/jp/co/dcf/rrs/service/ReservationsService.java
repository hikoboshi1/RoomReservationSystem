package jp.co.dcf.rrs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.dcf.rrs.model.Reservation;
import jp.co.dcf.rrs.model.User;
import jp.co.dcf.rrs.model.repository.ReservationsRepository;
import jp.co.dcf.rrs.service.constants.FormatConstants;
import jp.co.dcf.rrs.service.exception.ReservationNoSuchElementException;
import jp.co.dcf.rrs.service.exception.ReservationOverlapException;

@Service
public class ReservationsService {
	@Autowired
	ReservationsRepository reservationsRepository;

	//ControllerからUser情報を手に入れたので、idを参考にRepositoryで呼ぶ
	public List<Reservation> findReservationsByReserver(User reserver) {
		
		return reservationsRepository.findByReserverId(reserver.getId());
	}
	
	//日付(useDae)を基準にRepositoryクラスでListを返す(→検索はRepositoryクラスで行っている)
	public List<Reservation> findReservationsByUseDate(LocalDate useDate){
		return reservationsRepository.findByUseDate(useDate);
	}
	
	public List<Reservation> findReservationsByUseDate(String useDateStr){ 
		LocalDate useDate;
		try {
			useDate = LocalDate.parse(useDateStr, FormatConstants.JS_DATE);
		}catch (DateTimeParseException ex){
			return new ArrayList<Reservation>();
		}
		return reservationsRepository.findByUseDate(useDate);
	}
	
	//予約リクエスト受け取り
	public List<Reservation> findTimeOverlapReservations(LocalDate useDate, LocalTime useStartTime, LocalTime useEndTime){
		return reservationsRepository.findTimeOverlapReservations(useDate, useStartTime, useEndTime);
	}
	public List<Reservation> findTimeOverlapReservations(Reservation reservation){
		return reservationsRepository.findTimeOverlapReservations(reservation.getUseDate(), reservation.getUseStartTime(), reservation.getUseEndTime());
    }
	
	//idを引数に予約情報(Reservationクラスのオブジェクト)を返すメソッド
	public Reservation findReservationById(Integer id) throws ReservationNoSuchElementException{
		Optional<Reservation> reservation = reservationsRepository.findById(id);
		if(reservation.isPresent()) {
			return reservationsRepository.findById(id).get();//reservationRepositoryは、予約レコードのこと
		}else {
			throw new ReservationNoSuchElementException();
		}		
	}

	//現在時刻 < 予約日時 かつ ログインユーザー = 予約者 なら編集
	public boolean isEdittableReservation(Reservation reservation, User loginUser) {
		// TODO 自動生成されたメソッド・スタブ
		LocalDate date = reservation.getUseDate();
		LocalTime startTime = reservation.getUseStartTime();
		LocalDateTime dateStartTime = LocalDateTime.of(date, startTime);
		LocalDateTime now = LocalDateTime.now();
		return reservation.getReserver().equals(loginUser) && dateStartTime.isAfter(now);
	}
	
	//DBへ登録
	private void saveReservation(Reservation reservation) {
	    reservationsRepository.saveAndFlush(reservation);
	}

	//DBからレコード抹消
	public void deleteReservation(Reservation reservation) {
	    reservationsRepository.delete(reservation);
	}
	
	//追加ボタン押下時、予約が被っていないかチェック: 被っていればエラーを投げる
	public void trySaveReservation(Reservation reservation) throws ReservationOverlapException {
		List<Reservation> overlapReservation = findTimeOverlapReservations(reservation);
		Integer reservationId = reservation.getId();
		boolean save = overlapReservation.isEmpty() || (overlapReservation.size() == 1 && overlapReservation.get(0).getId().equals(reservationId));
		if(save) {
			saveReservation(reservation);
		}
		else {
			throw new ReservationOverlapException();
		}
	}
	
}
