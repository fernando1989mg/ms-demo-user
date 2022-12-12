package cl.demo.user.service;

import cl.demo.user.config.Constant;
import cl.demo.user.domain.exception.TokenExpiredException;
import cl.demo.user.domain.exception.UsernameExistsException;
import cl.demo.user.domain.exception.UsernameNotFoundException;
import cl.demo.user.domain.model.User;
import cl.demo.user.repository.IGenericRepo;
import cl.demo.user.repository.IUserRepo;
import cl.demo.user.domain.dto.UserDto;
import cl.demo.user.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service("UserService")
@RequiredArgsConstructor
public class UserService extends GenericService<User, String> implements IUserService {

    private final IUserRepo userRepo;

    private final BCryptPasswordEncoder bcrypt;

    private final UserMapper userMapper;

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
    public UserDto register(UserDto rqUserDto) {
        userRepo.findOneByEmail(rqUserDto.getEmail())
                .ifPresent(userData -> {
                    throw new UsernameExistsException("This email is already registered");
                });

        User user = userMapper.convertDtoToUser(rqUserDto);

        return userMapper.convertUserToDto(super.register(user));
    }

    @Override
    public UserDto getUser(String id) {

        return  userMapper.convertUserToDto(super.getById(id));
    }
}
