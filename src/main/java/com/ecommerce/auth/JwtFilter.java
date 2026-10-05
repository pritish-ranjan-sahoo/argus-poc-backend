package com.ecommerce.auth;

import com.ecommerce.common.error.UserNotFoundException;
import com.ecommerce.user.AppUser;
import com.ecommerce.user.UserRepository;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            final String accessToken  = request.getHeader("Authorization");
            if(accessToken==null || !accessToken.startsWith("Bearer")){
                filterChain.doFilter(request,response);
                return;
            }
            String jwtToken = accessToken.split("Bearer ")[1];
            String userId = jwtUtil.verifyToken(jwtToken);
            if(userId!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                AppUser principle = userRepository.findById(UUID.fromString(userId)).orElseThrow(()->
                        new UserNotFoundException("User not found from bearer token")
                );
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(principle.getId(),null,null);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            filterChain.doFilter(request,response);
        } catch (Exception ex){
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }
    }
}