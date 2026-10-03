package com.ecommerce.auth;

import com.ecommerce.common.dto.AuthResponseDTO;
import com.ecommerce.common.dto.LogInRequestDTO;

public interface AuthService {
    public AuthResponseDTO login(LogInRequestDTO request);
    public AuthResponseDTO register();
}
