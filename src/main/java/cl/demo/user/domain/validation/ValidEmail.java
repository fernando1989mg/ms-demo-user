package cl.demo.user.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "Invalid Email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
