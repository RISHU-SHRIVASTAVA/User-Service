package userService.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userService.dtos.LogOutRequestDTO;
import userService.dtos.LoginRequestDTO;
import userService.dtos.SignUpRequestDTO;
import userService.dtos.UserDTO;
import userService.models.Token;
import userService.models.User;
import userService.repositories.TokenRepository;
import userService.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private final TokenRepository tokenRepository;

    public UserController(UserService userService,
                          TokenRepository tokenRepository) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/signup")
    public UserDTO signup(@RequestBody SignUpRequestDTO requestDTO){
        User user=userService.signUp(
                requestDTO.getEmail(),
                requestDTO.getName(),
                requestDTO.getPassword()
        );
        return UserDTO.from(user);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDTO requestDTO){
        Token token=userService.login(
                requestDTO.getEmail(),
                requestDTO.getPassword()
        );
        return token;
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogOutRequestDTO requestDTO){
        userService.logout(requestDTO.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public UserDTO validateToken(@PathVariable String token){
        User user = userService.validateToken(token);
        return UserDTO.from(user);
    }

    @GetMapping("/users/{id}")
    public UserDTO getUserById(@PathVariable Long id){
        return null;
    }
}
