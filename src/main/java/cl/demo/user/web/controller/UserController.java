package cl.demo.user.web.controller;

import cl.demo.user.persistence.model.User;
import cl.demo.user.service.IUserService;
import cl.demo.user.web.dto.TokenDto;
import cl.demo.user.web.dto.UserDto;
import cl.demo.user.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

    private IUserService userService;
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> userCreate(@Valid @RequestBody UserDto dto){
        User user = userMapper.toUser(dto);

        return new ResponseEntity<UserDto>(
                userMapper.toUserRegisterDto(userService.register(user)),
                HttpStatus.CREATED);
    }

    @PostMapping(path = "login")
    public ResponseEntity<TokenDto> userLogin(@RequestParam("email") final String email, @RequestParam("password") final String password){
        User user= userService.login(email,password);

        return new ResponseEntity<TokenDto>(
                TokenDto.builder().token(user.getToken()).build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id){

        return new ResponseEntity<UserDto>(
                userMapper.toUserRegisterDto(userService.getById(id)),
                HttpStatus.OK);
    }

}
