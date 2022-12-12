package cl.demo.user.web.mapper;

import cl.demo.user.config.Constant;
import cl.demo.user.persistence.model.User;
import cl.demo.user.persistence.model.UserPhone;
import cl.demo.user.web.dto.UserDto;
import cl.demo.user.web.dto.UserPhoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    public UserDto toUserRegisterDto(User user) {

        List<UserPhoneDto> phones =
            Optional.ofNullable(user.getUserPhones())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(phone ->
                            UserPhoneDto.builder()
                                    .cityCode(phone.getCityCode())
                                    .countryCode(phone.getCountryCode())
                                    .number(phone.getNumber())
                                    .build()
                    )
                    .collect(Collectors.toList());

        return new UserDto(
                user.getName(),
                user.getEmail(),
                null,
                user.getId(),
                user.getCreatedAt(),
                user.getModifiedAt(),
                user.getLastLogin(),
                user.getToken(),
                true,
                phones);
    }

    public User toUser(UserDto userDTO) {


        User user = new User(
                null,
                Instant.now(),
                true,
                userDTO.getName(),
                userDTO.getEmail(),
                bcrypt.encode(userDTO.getPassword()),
                Collections.emptyList(),
                UUID.randomUUID().toString(),
                Instant.now().plusSeconds(Constant.EXPIRATION_TOKEN_IN_SECONDS));

        List<UserPhone> phones =
                Optional.ofNullable(userDTO.getPhones())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(phone ->
                                UserPhone.builder()
                                        .cityCode(phone.getCityCode())
                                        .countryCode(phone.getCountryCode())
                                        .number(phone.getNumber())
                                        .userId(user)
                                        .build()
                        )
                        .collect(Collectors.toList());

        user.setUserPhones(phones);

        return user;
    }
}
