package cl.demo.user.fixture;

import cl.demo.user.domain.dto.UserDto;
import cl.demo.user.domain.dto.UserPhoneDto;
import cl.demo.user.domain.exception.ExceptionResponseMessage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FixtureUserDto {

    public static UserDto createUserDto(){
        List<UserPhoneDto> phones = new ArrayList<>();

        phones.add(UserPhoneDto.builder().cityCode(3).countryCode(3).number(3213).build());

        UserDto userDto = new UserDto();

        userDto.setEmail("fer@gmail.com");
        userDto.setName("fernando");
        userDto.setPassword("Hola1234.");
        userDto.setPhones(phones);

        return userDto;
    }

    public static UserDto createInvalidPasswordUserDto(){
        List<UserPhoneDto> phones = new ArrayList<>();

        phones.add(UserPhoneDto.builder().cityCode(3).countryCode(3).number(3213).build());

        UserDto userDto = new UserDto();

        userDto.setEmail("fer@gmail.com");
        userDto.setName("fernando");
        userDto.setPassword("Hola14.");
        userDto.setPhones(phones);

        return userDto;
    }

    public static ExceptionResponseMessage createExceptionResponseMessage(){
        ExceptionResponseMessage msg = new ExceptionResponseMessage();

        msg.setError("Bad Request");
        msg.setMessage("ContraseÃ±a debe tener mÃ\u00ADnimo 8 caracteres con al menos un nÃºmero, un caracter especial (@#$%^&+=.) y una letra mayÃºscula");
        msg.setStatus(400);
        msg.setTime(Instant.now());

        return msg;
    }
}
