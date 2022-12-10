package cl.demo.user.service;

import cl.demo.user.persistence.model.User;

import java.util.Optional;

public interface IUserService extends IGenericService<User, String> {

    public String login(String email, String password);
    public Optional<org.springframework.security.core.userdetails.User> findByToken(String token);
}
