package jp.co.dcf.rrs.model.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeIsFuture.DateTimeIsFutureValidator.class)
public @interface DateTimeIsFuture {
    public String message() default "{reservation.future-required}";// エラー時に出すメッセージを定義する
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String element1();
    String element2();
    
    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface List {
        DateTimeIsFuture[] value();
    }
    
    class DateTimeIsFutureValidator implements ConstraintValidator<DateTimeIsFuture, Object> {
        private String dateProperty;
        private String timeProperty;
        
        private String message;
        
        @Override
        public void initialize(DateTimeIsFuture constraintAnnotation) {
            this.dateProperty = constraintAnnotation.element1();
            this.timeProperty = constraintAnnotation.element2();
            this.message = constraintAnnotation.message();
        }
    
        @Override
        public boolean isValid(Object bean, ConstraintValidatorContext context) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
            LocalDate date = (LocalDate) beanWrapper.getPropertyValue(dateProperty);
            LocalTime time = (LocalTime) beanWrapper.getPropertyValue(timeProperty);
            LocalDateTime now = LocalDateTime.now();
            if(date == null || time == null) { // これはそもそも「必須項目である」というバリデーションに引っかかるべきなので、このバリデーションではtrueを返しておく。
                return true; 
            }
            
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            
            if (dateTime.isBefore(now)  ) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(timeProperty)
                    .addConstraintViolation();               
                return false;
            } else {
                return true;
            }
        }
    }
}