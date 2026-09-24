package com.ecommerce.cart;

import com.ecommerce.user.AppUser;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartId;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false, unique = true)
    private AppUser user;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();
}
