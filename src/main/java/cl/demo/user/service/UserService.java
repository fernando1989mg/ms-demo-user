package cl.demo.user.service;

import cl.demo.user.config.Constant;
import cl.demo.user.exception.TokenExpiredException;
import cl.demo.user.exception.UsernameExistsException;
import cl.demo.user.exception.UsernameNotFoundException;
import cl.demo.user.persistence.model.User;
import cl.demo.user.persistence.repository.IGenericRepo;
import cl.demo.user.persistence.repository.IUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service("UserService")
@AllArgsConstructor
public class UserService extends GenericService<User, String> implements IUserService {

    private IUserRepo userRepo;

    private BCryptPasswordEncoder bcrypt;

    @Override
    protected IGenericRepo<User, String> getRepo() {
        return this.userRepo;
    }


    @Override
    public User login(String email, String password) {
        Optional<User> user = userRepo.findOneByEmail(email);

        return user
                .filter(userData -> bcrypt.matches(password, userData.getPassword()))
                .map(userData -> {
                    userData.setToken(UUID.randomUUID().toString());
                    userData.setTokenExpiration(Instant.now().plusSeconds(Constant.EXPIRATION_TOKEN_IN_SECONDS));
                    return userRepo.save(userData);
                })
                .orElseThrow(() -> new UsernameNotFoundException("Email or password incorrect"));
    }

    @Override
    public Optional<org.springframework.security.core.userdetails.User> findByToken(String token) {
        Optional<User> user = userRepo.findByToken(token);

        user
            .filter(userData -> Instant.now().isAfter(userData.getTokenExpiration()) )
            .map(userData -> {
                throw new TokenExpiredException("Token has expired");
                //return Optional.empty();
            });


        return user
                .flatMap(userData -> {
                    org.springframework.security.core.userdetails.User userDetails =
                            new org.springframework.security.core.userdetails.User(
                                    userData.getName(),
                                    userData.getPassword(),
                                    true,
                                    true,
                                    true,
                                    true,
                                    AuthorityUtils.createAuthorityList("USER")
                            );
                    return Optional.of(userDetails);
                })
                .or(() -> Optional.empty());
    }

    @Override
    public User register(User user) {
        userRepo.findOneByEmail(user.getEmail())
                .ifPresent(userData -> {
                    throw new UsernameExistsException("This email is already registered");
                });

        return super.register(user);
    }
}
