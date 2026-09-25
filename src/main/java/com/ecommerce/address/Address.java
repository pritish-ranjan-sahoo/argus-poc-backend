package com.ecommerce.address;

import com.ecommerce.user.AppUser;
import com.ecommerce.user.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID addressId;

    @Column(nullable = false)
    @Size(min = 3, max = 100, message = "Address line 1 must be between {min} and {max} characters")
    private String line_1;

    @Size(min = 3, max = 60, message = "Address line 2 must be between {min} and {max} characters")
    private String line_2;

    @Size(min = 8, max = 50, message = "Address line 3 must be between {min} and {max} characters")
    private String line_3;

    @Column(nullable = false)
    @Size(min = 3, max = 30, message = "City name must be between {min} and {max} characters")
    private String city;

    @Column(nullable = false)
    @Size(min = 3, max = 30, message = "State must be between {min} and {max} characters")
    private String state;

    @Column(nullable = false, length = 6)
    private String zipCode;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private UUID customerId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
