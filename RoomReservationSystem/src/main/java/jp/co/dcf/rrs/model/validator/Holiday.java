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
import java.util.Locale;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Holiday.HolidayValidator.class)
public @interface Holiday {
    public String message() default "{reservation.holiday}";// エラー時に出すメッセージを定義する
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String element1();
    
    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface List {
        Holiday[] value();
    }
    
    class HolidayValidator implements ConstraintValidator<Holiday, Object> {
        private String dayOfWeekProperty;
        
        private String message;
        
        @Override
        public void initialize(Holiday constraintAnnotation) {
            this.dayOfWeekProperty = constraintAnnotation.element1();
            this.message = constraintAnnotation.message();
        }
    
		@Override
        public boolean isValid(Object bean, ConstraintValidatorContext context) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
            LocalDate dayOfWeek = (LocalDate) beanWrapper.getPropertyValue(dayOfWeekProperty);
            
            if(dayOfWeek == null) { // これはそもそも「必須項目である」というバリデーションに引っかかるべきなので、このバリデーションではtrueを返しておく。
                return true; 
            }
            //String型で日本語の曜日を取得
            String day = dayOfWeek.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.JAPANESE);
            
            if (day.equals("日曜日") || day.equals("土曜日")) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(dayOfWeekProperty)
                    .addConstraintViolation();               
                return false;
            } else {
                return true;
            }
        }
    }
}