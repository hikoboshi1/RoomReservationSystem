package jp.co.dcf.rrs.application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.dcf.rrs.application.service.ReservationListApplicationService;

@Controller
@RequestMapping("/reservations")
public class ReservationEditController {
	@Autowired
	ReservationListApplicationService reservationListApplicationService;

	@RequestMapping(value = { "/new" })
	public ModelAndView newReservation(ModelAndView mav, @RequestParam("date") Optional<String> reservationId) {
		mav.setViewName("reservation_edit");
		return mav;
	}
	
	@RequestMapping(value = { "/{reservation_id}" })
	public ModelAndView edit(ModelAndView mav, @PathVariable("reservation_id") Optional<Integer> reservationId) {
		mav.addObject("reservationId", reservationId);
		mav.setViewName("reservation_edit");
		return mav;
	}
}