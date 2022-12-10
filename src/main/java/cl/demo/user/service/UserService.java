package cl.demo.user.service;

import cl.demo.user.exception.UsernameExistsException;
import cl.demo.user.exception.UsernameNotFoundException;
import cl.demo.user.persistence.model.User;
import cl.demo.user.persistence.repository.IGenericRepo;
import cl.demo.user.persistence.repository.IUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public String login(String email, String password) {
        Optional<User> user = userRepo.findOneByEmail(email);

        if(user.isPresent() &&
                bcrypt.matches(password, user.get().getPassword())){

            User userCustom = user.get();
            String token = UUID.randomUUID().toString();
            userCustom.setToken(token);

            userRepo.save(userCustom);

            return token;
        }else{
            throw new UsernameNotFoundException("Email or password incorrect");
        }
    }

    @Override
    public Optional<org.springframework.security.core.userdetails.User> findByToken(String token) {
        Optional<User> user = userRepo.findByToken(token);

        if(user.isPresent()){
            org.springframework.security.core.userdetails.User userDetails =
                    new org.springframework.security.core.userdetails.User(
                            user.get().getName(),
                            user.get().getPassword(),
                            true,
                            true,
                            true,
                            true,
                            AuthorityUtils.createAuthorityList("USER")
                    );
            return Optional.of(userDetails);
        }
        return Optional.empty();
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
