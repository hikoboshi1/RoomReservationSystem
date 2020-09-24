package jp.co.dcf.rrs.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.dcf.rrs.model.Reservation;
import jp.co.dcf.rrs.model.User;
import jp.co.dcf.rrs.model.repository.ReservationsRepository;
import jp.co.dcf.rrs.service.ReservationsService;
import jp.co.dcf.rrs.service.constants.FormatConstants;
import jp.co.dcf.rrs.service.exception.ReservationNoSuchElementException;
import jp.co.dcf.rrs.service.exception.ReservationOverlapException;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/reservations")
@Log4j2
@SessionAttributes(names="reservation")
public class ReservationsController {
	@Autowired
	ReservationsRepository reservationsRepository;
	@Autowired
	ReservationsService reservationsService;
	@Autowired
	MessageSource messageSource;
	
	@GetMapping({""})
	public String indexForward() {
		return "forward:/";
	}
	
	@GetMapping({ "/","/list" })
	public ModelAndView index(ModelAndView mav, @AuthenticationPrincipal User user, @RequestParam("date") Optional<String> dateParam, @ModelAttribute("successMessage") String successMessage) {
		String useDate = dateParam.orElse(LocalDate.now().format(FormatConstants.JS_DATE)); //dateParamを返すと空になる。dateParamをorElseした結果を返す
		mav.addObject("useDate",useDate);
		mav.addObject("successMessage", successMessage);
		
		//カレンダーで検索したもののList
		List<Reservation> reservationList = reservationsService.findReservationsByUseDate(useDate);
	    
		//カレンダーで指定した日の予約が無ければ、その旨のメッセージを表示
		if(reservationList.isEmpty())
	    {
			//メッセージは、reservation.not-anyにあるのを利用
	    	String message = messageSource.getMessage("reservation.not-any", null, null);
	        mav.addObject("message", message);
	    }
	    mav.addObject("reservationList", reservationList);
		mav.setViewName("reservations/list");
		return mav;
	}
	
	@RequestMapping({ "/new" })
	public ModelAndView newReservation(ModelAndView mav, @RequestParam("date") Optional<String> dateParam, @AuthenticationPrincipal User loginUser) {
		Reservation reservation = new Reservation();
	  //新規　新規作成ボタン横の日付と、新規作成ボタンを押してページを飛んだ先の利用日が一致する
		String useDate = dateParam.orElse(LocalDate.now().format(FormatConstants.JS_DATE));
		reservation.setUseDate(LocalDate.parse(useDate, FormatConstants.JS_DATE));		
		
		reservation.setReserver(loginUser); //新規作成の予約者名には、ログインユーザー名を初期表示させる
		mav.addObject("reservation",reservation);
		mav.addObject("isNewMode", true);
		mav.setViewName("reservations/edit");
		return mav;
	}
	
	@RequestMapping({ "/{id}" })
	public ModelAndView edit(ModelAndView mav, @PathVariable("id") Integer id, @AuthenticationPrincipal User loginUser) throws ReservationNoSuchElementException { //@Auth... でログイン情報を得る。型はUser
			
		//先に初期化: これを行わないと下のif文の引数がコンパイルエラーを起こす
	    Reservation reservation = null;
		try {
			reservation = reservationsService.findReservationById(id);
		}catch(ReservationNoSuchElementException e) {
			mav.addObject("errorMessage", messageSource.getMessage("reservation.not-any", null, null));
            mav.setViewName("reservations/list");
	        return mav;				
		}
			
		//HTML側の、th:disabled="${isRefMode}" とかを動的に制御。条件に当てはまれば、disabledが作動する。
		if(reservationsService.isEdittableReservation(reservation,loginUser)) {
			log.info("編集モードで描画します");
			mav.addObject("isEditMode",true);
		}else {
			log.info("参照モードで描画します");
			mav.addObject("isRefMode",true);
		}
			mav.addObject("reservation", reservation);
			mav.setViewName("reservations/edit");
			return mav;
	}	
	
	@PostMapping({""})
	public ModelAndView create(ModelAndView mav, @ModelAttribute("reservation") @Validated Reservation reservation, BindingResult result, RedirectAttributes redirectAttr) {
		log.info("追加ボタンが押されました");
		log.info("ID:{}", reservation.getId());
		log.info("利用日:{}", reservation.getUseDate());
	    log.info("利用開始時刻:{}", reservation.getUseStartTime());
	    log.info("利用終了時刻:{}", reservation.getUseEndTime());
	    log.info("予約者:{}", reservation.getReserver().getUsername());
	    log.info("利用者名:{}", reservation.getRoomUsername());
	    log.info("備考:{}", reservation.getNote());
	    
	    if(result.hasErrors()) {
	    	
	    	mav.addObject("isNewMode", true);
	    	mav.setViewName("reservations/edit");
	    	
	    } else {
	    	try {
	    		reservationsService.trySaveReservation(reservation);
	    	}
	    	catch(ReservationOverlapException e){
	    		mav.addObject("isNewMode", true);
	            mav.addObject("errorMessage", messageSource.getMessage("reservation.duplicate", null, null));
	            mav.setViewName("reservations/edit");
	            return mav;
	    	}
	    	mav.addObject("date", reservation.getUseDate().format(FormatConstants.JS_DATE));
	    	String successMessage = messageSource.getMessage("reservation.success.create", null, null);
	    	redirectAttr.addFlashAttribute("successMessage", successMessage);
	    	mav.setViewName("redirect:reservations/list");
	    }
	    return mav;
	}
	@PatchMapping({""})
	public ModelAndView update(ModelAndView mav, @ModelAttribute("reservation") @Validated Reservation reservation, BindingResult result, RedirectAttributes redirectAttr) {
		log.info("更新ボタンが押されました");
		log.info("ID:{}", reservation.getId());
		log.info("利用日:{}", reservation.getUseDate());
	    log.info("利用開始時刻:{}", reservation.getUseStartTime());
	    log.info("利用終了時刻:{}", reservation.getUseEndTime());
	    log.info("予約者:{}", reservation.getReserver().getUsername());
	    log.info("利用者名:{}", reservation.getRoomUsername());
	    log.info("備考:{}", reservation.getNote());
		
	    if(result.hasErrors()) {
			mav.addObject("isEditMode", true);
			mav.setViewName("reservations/edit");
		} else {
			try{
				reservationsService.trySaveReservation(reservation);
			}
			catch(ReservationOverlapException e) {			
				mav.addObject("isEditMode", true);
	            mav.addObject("errorMessage", messageSource.getMessage("reservation.duplicate", null, null));
	            mav.setViewName("reservations/edit");
	            return mav;
			}
			mav.addObject("date", reservation.getUseDate().format(FormatConstants.JS_DATE));
			String successMessage = messageSource.getMessage("reservation.success.update", null, null);
			redirectAttr.addFlashAttribute("successMessage", successMessage);
			mav.setViewName("redirect:reservations/list");
		}
	    return mav;
	}
	@DeleteMapping({""})
	public ModelAndView delete(ModelAndView mav, @ModelAttribute("reservation") Reservation reservation, RedirectAttributes redirectAttr) {
		log.info("削除ボタンが押されました");
		log.info("ID:{}", reservation.getId());
		log.info("利用日:{}", reservation.getUseDate());
	    log.info("利用開始時刻:{}", reservation.getUseStartTime());
	    log.info("利用終了時刻:{}", reservation.getUseEndTime());
	    log.info("予約者:{}", reservation.getReserver());
	    log.info("利用者名:{}", reservation.getRoomUsername());
	    log.info("備考:{}", reservation.getNote());
	    
	    reservationsService.deleteReservation(reservation);
	    
	    mav.addObject("date", reservation.getUseDate().format(FormatConstants.JS_DATE));
	    String successMessage = messageSource.getMessage("reservation.success.destroy", null, null);
	    redirectAttr.addFlashAttribute("successMessage", successMessage);
	    mav.setViewName("redirect:reservations/list");
		return mav;
	}
}

	
	
	