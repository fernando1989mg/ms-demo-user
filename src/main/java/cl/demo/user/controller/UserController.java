package cl.demo.user.controller;

import cl.demo.user.domain.model.User;
import cl.demo.user.service.IUserService;
import cl.demo.user.domain.dto.TokenDto;
import cl.demo.user.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserDto> userCreate(@Valid @RequestBody UserDto dto){
        return new ResponseEntity<UserDto>(userService.register(dto),HttpStatus.CREATED);
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

        return new ResponseEntity<UserDto>(userService.getUser(id),  HttpStatus.OK);
    }

}
