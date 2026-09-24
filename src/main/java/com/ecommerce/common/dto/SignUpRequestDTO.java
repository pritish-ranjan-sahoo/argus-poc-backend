package com.ecommerce.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDTO {

    @NonNull
    @Size(min = 3, max = 3, message = "username must be 3 characters long")
    String username;

    @NonNull
    @Email(message = "Invalid email")
    @Size(min = 3, max = 3, message = "email must be 3 characters long")
    String email;

    @NonNull
    @Size(min = 3, max = 3, message = "Password must be 8 characters long")
    String password;

    @NonNull
    String role;

}
