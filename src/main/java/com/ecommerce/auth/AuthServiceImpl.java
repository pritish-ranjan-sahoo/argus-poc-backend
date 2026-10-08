package com.ecommerce.auth;

import com.ecommerce.common.dto.AuthResponseDTO;
import com.ecommerce.common.dto.LogInRequestDTO;
import com.ecommerce.common.dto.SignUpRequestDTO;
import com.ecommerce.common.dto.UserResponseDTO;
import com.ecommerce.common.error.UserNotFoundException;
import com.ecommerce.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO login(LogInRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCredential(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserResponseDTO appUser = userService
                .findByCredential(authentication.getName());

        if(appUser==null){
            throw new UserNotFoundException("User not found in db during log in!");
        }

        AuthResponseDTO response = AuthResponseDTO
                .builder()
                .accessToken(jwtUtil.generateJwtToken(appUser.getId().toString()))
                .user(appUser)
                .build();
        return response;
    }

    @Override
    public AuthResponseDTO register(SignUpRequestDTO request) {
        UserResponseDTO user = userService.register(request);
        String token = jwtUtil.generateJwtToken(user.getId().toString());
        AuthResponseDTO response = AuthResponseDTO
                .builder()
                .accessToken(token)
                .user(user)
                .build();
        return response;
    }
}
