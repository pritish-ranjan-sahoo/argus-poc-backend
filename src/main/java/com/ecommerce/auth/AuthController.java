package com.ecommerce.auth;

import com.ecommerce.common.dto.AuthResponseDTO;
import com.ecommerce.common.dto.LogInRequestDTO;
import com.ecommerce.common.dto.SignUpRequestDTO;
import com.ecommerce.common.dto.UserResponseDTO;
import com.ecommerce.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDTO> signUp(@RequestBody @Valid SignUpRequestDTO newUser){
        UserResponseDTO createdUser = authService.register(newUser);

        return new ResponseEntity<AuthResponseDTO>(HttpStatus.CREATED);
    }


    @PostMapping("/log-in")
    public ResponseEntity<AuthResponseDTO> logIn(@RequestBody @Valid SignUpRequestDTO newUser){
        UserResponseDTO createdUser = authService.register(newUser);
        return new ResponseEntity<AuthResponseDTO>(createdUser, HttpStatus.CREATED);
    }

}
