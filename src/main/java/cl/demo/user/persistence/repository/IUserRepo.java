package cl.demo.user.persistence.repository;

import cl.demo.user.persistence.model.User;

import java.util.Optional;

public interface IUserRepo extends IGenericRepo<User, String>{

    Optional<User> findOneByEmail(String email);

    User findByToken(String token);
}
