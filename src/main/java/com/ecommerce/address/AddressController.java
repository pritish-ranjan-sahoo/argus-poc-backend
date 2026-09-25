package com.ecommerce.address;

import com.ecommerce.common.dto.AddressRequestDTO;
import com.ecommerce.common.dto.AddressResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/address")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Customer Address APIs", description = "APIs to perform operations on the customer addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{id}")
    @Operation(summary = "Get address by the User ID", description = "Get address by the address ID provided as path parameter")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable String id){
        AddressResponseDTO response = addressService.getAddressById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    @Operation(summary = "Add address", description = "Add new address by providing the required attributes")
    public ResponseEntity<AddressResponseDTO> createNewAddress(@Valid @RequestBody AddressRequestDTO request){
        AddressResponseDTO response = addressService.addAddress(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete address by the address ID", description = "Soft delete address by the given address id")
    public ResponseEntity<AddressResponseDTO> deleteAddressById(@PathVariable String id){
        AddressResponseDTO response = addressService.removeAddress(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get all address", description = "Get all address")
    public ResponseEntity<Page<AddressResponseDTO>> getAllAddress(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "city") String attribute
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(attribute).descending());
        Page<AddressResponseDTO> response = addressService.getAllAddress(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all/{customerId}")
    @Operation(summary = "Get specific user addresses", description = "Get all active addresses of an user")
    public ResponseEntity<List<AddressResponseDTO>> getAddressByCustomerId( @PathVariable String customerId ) {
        List<AddressResponseDTO> response = addressService.getAddressByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }

}
