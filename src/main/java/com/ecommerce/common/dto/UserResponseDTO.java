package com.ecommerce.common.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    String id;
    String username;
    String email;
    String password;
    String role;
}
