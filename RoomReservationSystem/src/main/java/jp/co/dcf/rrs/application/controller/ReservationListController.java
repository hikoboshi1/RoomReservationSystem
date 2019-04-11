package jp.co.dcf.rrs.application.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.dcf.rrs.application.service.ReservationListApplicationService;
import jp.co.dcf.rrs.domain.entity.ReservationTblEntity;

@Controller
public class ReservationListController {
	@Autowired
	ReservationListApplicationService reservationListApplicationService;
	
	@RequestMapping({ "/reservation_list" })
	public ModelAndView index(ModelAndView mav) {
		List<ReservationTblEntity> reservationList = reservationListApplicationService.getAllReservationList();
		mav.addObject("reservationList", reservationList);
		mav.setViewName("reservation_list");
		return mav;
	}
	

}