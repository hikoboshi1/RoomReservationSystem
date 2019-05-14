package jp.co.dcf.rrs.application.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class ReservationException extends Exception{
	protected static MessageSource messageSource;
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		ReservationException.messageSource = messageSource;
	}
	
	public ReservationException(String message) {
		super(message);
	}
	
	public ReservationException() {
		super();
	}
}
