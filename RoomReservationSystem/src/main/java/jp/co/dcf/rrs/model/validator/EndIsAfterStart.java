package jp.co.dcf.rrs.model.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EndIsAfterStart.EndIsAfterStartValidator.class)
public @interface EndIsAfterStart {
    public String message() default "{end-is-after-start}";// エラー時に出すメッセージを定義する
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String element1();
    String element2();
    
    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface List {
        EndIsAfterStart[] value();
    }
    
    class EndIsAfterStartValidator implements ConstraintValidator<EndIsAfterStart, Object> {
        private String startProperty;
        private String endProperty;
        
        private String message;
        
        @Override
        public void initialize(EndIsAfterStart constraintAnnotation) {
            this.startProperty = constraintAnnotation.element1();
            this.endProperty = constraintAnnotation.element2();
            this.message = constraintAnnotation.message();
        }
    
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public boolean isValid(Object bean, ConstraintValidatorContext context) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
            Comparable start = (Comparable) beanWrapper.getPropertyValue(startProperty);
            Comparable end= (Comparable) beanWrapper.getPropertyValue(endProperty);
            if(start == null || end == null) { // これはそもそも「必須項目である」というバリデーションに引っかかるべきなので、このバリデーションではtrueを返しておく。
                return true; 
            }
            
            if (start.compareTo(end) > 0 ) {
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