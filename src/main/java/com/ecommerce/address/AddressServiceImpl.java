package com.ecommerce.address;


import com.ecommerce.common.dto.AddressRequestDTO;
import com.ecommerce.common.dto.AddressResponseDTO;
import com.ecommerce.common.error.DataNotFoundException;
import com.ecommerce.common.error.UserNotFoundException;
import com.ecommerce.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Override
    public AddressResponseDTO addAddress(AddressRequestDTO request) {
        if(!userService.isCustomer(request.getCustomerId())) {
            throw new UserNotFoundException("Customer not found with id: " + request.getCustomerId());
        }

        Address address = Address
                .builder()
                .line_1(request.getLine_1().trim())
                .line_2(request.getLine_2().trim())
                .line_3(request.getLine_3().trim())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .isActive(true)
                .customerId(UUID.fromString(request.getCustomerId()))
                .build();

        addressRepository.save(address);
        AddressResponseDTO responseDTO = modelMapper.map(address, AddressResponseDTO.class);
        responseDTO.setFullAddress(address.getLine_1() + " " + address.getLine_2() +  " " + address.getLine_3());
        return responseDTO;
    }

    @Override
    public AddressResponseDTO removeAddress(String id) {
        UUID uuid = UUID.fromString(id);
        Address address = addressRepository
                .findByAddressId(uuid)
                .orElseThrow(() -> new DataNotFoundException("Address not found with id: " + id));
        address.setActive(false);
        addressRepository.save(address);
        return modelMapper.map(address, AddressResponseDTO.class);
    }

    @Override
    public AddressResponseDTO getAddressById(String id) {
        UUID uuid = UUID.fromString(id);
        Address address = addressRepository
                .findByAddressId(uuid)
                .orElseThrow(() -> new DataNotFoundException("Address not found with id: " + id));
        return modelMapper.map(address, AddressResponseDTO.class);
    }

    @Override
    public Page<AddressResponseDTO> getAllAddress(Pageable pageable) {
        Page<Address> addressList = addressRepository.findAll(pageable);
        return addressList
                .map((address) -> modelMapper.map(address, AddressResponseDTO.class));
    }

    @Override
    public List<AddressResponseDTO> getAddressByCustomerId(String id) {
        UUID uuid = UUID.fromString(id);
        List<Address> addressList = addressRepository.findAllByCustomerId(uuid);
        return addressList.stream()
                .filter(address -> address.isActive())
                .map((address) -> modelMapper.map(address, AddressResponseDTO.class))
                .collect(Collectors.toList());
    }
}
