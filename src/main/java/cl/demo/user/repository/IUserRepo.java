package cl.demo.user.repository;

import cl.demo.user.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends IGenericRepo<User, String>{

    Optional<User> findOneByEmail(String email);

    Optional<User> findByToken(String token);
}
