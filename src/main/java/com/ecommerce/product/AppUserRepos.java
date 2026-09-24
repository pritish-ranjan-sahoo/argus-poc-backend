package com.ecommerce.product;
//remvoe this and link with actual repo
import com.ecommerce.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUserRepos extends JpaRepository<AppUser, UUID> {
}
