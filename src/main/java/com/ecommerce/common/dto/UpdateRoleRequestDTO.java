package com.ecommerce.common.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRoleRequestDTO {
    @NonNull
    String id;

    @NonNull
    String role;

}