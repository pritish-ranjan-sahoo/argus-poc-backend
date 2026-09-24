package com.ecommerce.config;

import com.ecommerce.address.Address;
import com.ecommerce.common.dto.AddressResponseDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Address, String> addressStringConverter = context -> {
            Address src = context.getSource();
            if (src == null) return "";

            return ((src.getLine_1() != null ? src.getLine_1() : "") + " " +
                    (src.getLine_2() != null ? src.getLine_2() : "") + " " +
                    (src.getLine_3() != null ? src.getLine_3() : "")).trim().replaceAll("\\s+", " ");
        };

        modelMapper.typeMap(Address.class, AddressResponseDTO.class)
                .addMappings(mapper -> mapper.using(addressStringConverter)
                        .map(src -> src, AddressResponseDTO::setFullAddress));

        return modelMapper;
    }

}
