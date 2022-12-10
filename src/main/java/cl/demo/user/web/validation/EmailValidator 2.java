package cl.demo.user.web.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Configuration
@PropertySource("classpath:configuration.properties")
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Value("${regexp.email}")
    private String regexp;

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && email.matches(regexp);
    }
}
