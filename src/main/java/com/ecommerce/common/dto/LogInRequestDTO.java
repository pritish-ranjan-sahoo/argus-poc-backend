package com.ecommerce.common.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogInRequestDTO {
    @NonNull
    String credential;
    @NonNull
    String password;
}
