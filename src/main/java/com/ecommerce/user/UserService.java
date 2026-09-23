package com.ecommerce.user;


import com.ecommerce.common.dto.SignUpRequestDTO;
import com.ecommerce.common.dto.UpdateRoleRequestDTO;
import com.ecommerce.common.dto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponseDTO register(SignUpRequestDTO data);
    UserResponseDTO findById(String id);
    UserResponseDTO findByCredential(String credential);
    Page<UserResponseDTO> getAllUsers(Pageable pageable);
    Page<UserResponseDTO> getUsersByRole(String role, Pageable pageable);
    Page<UserResponseDTO> getActiveUsers(Pageable pageable);
    UserResponseDTO updateUserRole(UpdateRoleRequestDTO data);
    UserResponseDTO toggleUserActivityStatus(String id);
    boolean isAdmin(String id);
    boolean isSeller(String id);
    boolean isCustomer(String id);
}
