package jp.co.dcf.rrs.model.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import ch.qos.logback.core.util.Duration;
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OutsideHours.OutsideHoursValidator.class)
public @interface OutsideHours {
    public String message() default "{reservation.outsideHours}";// エラー時に出すメッセージを定義する
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String element1();
    String element2();
    
    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface List {
        OutsideHours[] value();
    }
    
    class OutsideHoursValidator implements ConstraintValidator<OutsideHours, Object> {
        private String startProperty;
        private String endProperty;
        
        private String message;
        
        @Override
        public void initialize(OutsideHours constraintAnnotation) {
            this.startProperty = constraintAnnotation.element1();
            this.endProperty = constraintAnnotation.element2();
            this.message = constraintAnnotation.message();
        }
    
		@Override
        public boolean isValid(Object bean, ConstraintValidatorContext context) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
            LocalTime start = (LocalTime) beanWrapper.getPropertyValue(startProperty);
            LocalTime end= (LocalTime) beanWrapper.getPropertyValue(endProperty);
            LocalTime nine = LocalTime.parse("09:00");
            LocalTime eighteen = LocalTime.parse("18:00");
            
            if(start == null || end == null) { // これはそもそも「必須項目である」というバリデーションに引っかかるべきなので、このバリデーションではtrueを返しておく。
                return true; 
            }
            
            if (start.isBefore(nine) || end.isAfter(eighteen)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(endProperty)
                    .addConstraintViolation();               
                return false;
            } else {
                return true;
            }
        }
    }
}