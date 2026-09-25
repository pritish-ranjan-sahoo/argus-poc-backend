package com.ecommerce.common.dto;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDTO {
    @NonNull
    @Size(min = 3, max = 60, message = "Address line 1 must be between {min} and {max} characters")
    private String line_1;

    private String line_2;
    private String line_3;

    @NonNull
    @Size(min = 3, max = 30, message = "City name must be between {min} and {max} characters")
    private String city;

    @NonNull
    @Size(min = 3, message = "City name must be minimum {min} characters long")
    private String state;

    @NonNull
    @Size(min = 6, max = 6, message = "Zip code name must be minimum {min} characters long")
    private String zipCode;

    @NonNull
    private String customerId;
}
