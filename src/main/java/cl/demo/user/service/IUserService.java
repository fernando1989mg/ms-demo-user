package cl.demo.user.service;

import cl.demo.user.domain.model.User;
import cl.demo.user.domain.dto.UserDto;

import java.util.Optional;

public interface IUserService extends IGenericService<User, String> {

    public User login(String email, String password);
    public Optional<org.springframework.security.core.userdetails.User> findByToken(String token);
    public UserDto register(UserDto user);
    public UserDto getUser(String id);
}
