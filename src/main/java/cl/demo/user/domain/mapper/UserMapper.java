package cl.demo.user.domain.mapper;

import cl.demo.user.config.Constant;
import cl.demo.user.domain.model.User;
import cl.demo.user.domain.model.UserPhone;
import cl.demo.user.domain.dto.UserDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User convertDtoToUser(UserDto userDto);

    UserDto convertUserToDto(User user);

    @AfterMapping
    default User setUserOnPhones(@MappingTarget User user) {
        List<UserPhone> userPhones = user.getPhones().stream().map(userPhone -> {
            userPhone.setUser(user);
            return userPhone;
        }).collect(Collectors.toList());

        user.setIsActive(true);

        user.setPhones(userPhones);
        user.setToken(UUID.randomUUID().toString());
        user.setTokenExpiration(Instant.now().plusSeconds(Constant.EXPIRATION_TOKEN_IN_SECONDS));
        user.setLastLogin(Instant.now());

        return user;
    }
}
