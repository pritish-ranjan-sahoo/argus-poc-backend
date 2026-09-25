package com.ecommerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponseDTO {
    private UUID addressId;
    private UUID customerId;
    private String fullAddress;
    private String city;
    private String state;
    private String zipCode;
}
