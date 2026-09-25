package com.ecommerce.common.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    UUID id;
    String username;
    String email;
    String password;
    String role;
}
