package cl.demo.user.domain.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordEncoderMapper {

    private final BCryptPasswordEncoder bcrypt;

    @EncodedMapping
    public String encode(String value) {
        return bcrypt.encode(value);
    }
}
