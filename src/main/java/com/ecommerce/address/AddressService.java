package com.ecommerce.address;

import com.ecommerce.common.dto.AddressRequestDTO;
import com.ecommerce.common.dto.AddressResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {

    AddressResponseDTO addAddress(AddressRequestDTO request);
    AddressResponseDTO removeAddress(String id);
    AddressResponseDTO getAddressById(String id);
    Page<AddressResponseDTO> getAllAddress(Pageable pageable);
    List<AddressResponseDTO> getAddressByCustomerId(String id);

}
