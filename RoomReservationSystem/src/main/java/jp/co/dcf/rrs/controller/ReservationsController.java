package jp.co.dcf.rrs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.dcf.rrs.model.Reservation;
import jp.co.dcf.rrs.model.User;
import jp.co.dcf.rrs.model.repository.ReservationsRepository;
import jp.co.dcf.rrs.service.ReservationsService;

@Controller
@RequestMapping("/reservations")
public class ReservationsController {
	@Autowired
	ReservationsService reservationsService;
	
	@RequestMapping({""})
	public String indexForward() {
		return "forward:/";
	}
	
	@RequestMapping({ "/","/list" })
	public ModelAndView index(ModelAndView mav, @AuthenticationPrincipal User user) {
		List<Reservation> reservationList = reservationsService.findAllReservations();
		mav.addObject("reservationList", reservationList);
		mav.setViewName("reservations/list");
		return mav;
	}
	
	@RequestMapping({ "/new" })
	public ModelAndView newReservation(ModelAndView mav, @RequestParam("date") Optional<String> date) {
		mav.setViewName("reservations/edit");
		return mav;
	}
	
	@RequestMapping({ "/{id}" })
	public ModelAndView edit(ModelAndView mav) {
		mav.setViewName("reservations/edit");
		return mav;
	}
	

}
