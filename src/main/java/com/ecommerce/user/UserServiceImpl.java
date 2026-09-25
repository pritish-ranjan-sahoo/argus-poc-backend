package com.ecommerce.user;

import com.ecommerce.common.dto.SignUpRequestDTO;
import com.ecommerce.common.dto.UpdateRoleRequestDTO;
import com.ecommerce.common.dto.UserResponseDTO;
import com.ecommerce.common.error.UserNotFoundException;
import com.ecommerce.common.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDTO register(SignUpRequestDTO data) {
        AppUser user = AppUser.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .password(data.getPassword())
                .role(authUtil.getRole(data.getRole()))
                .isActive(true)
                .build();
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<AppUser> users = userRepository.findAll(pageable);
        return users.map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    @Override
    public Page<UserResponseDTO> getUsersByRole(String role, Pageable pageable) {
        Page<AppUser> users = userRepository.findByRole(authUtil.getRole(role), pageable);
        return users.map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    @Override
    public Page<UserResponseDTO> getActiveUsers(Pageable pageable) {
        Page<AppUser> users = userRepository.findByIsActive(true, pageable);
        return users.map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    @Override
    public UserResponseDTO updateUserRole(UpdateRoleRequestDTO data) {
        UUID uuid = UUID.fromString(data.getId());
        AppUser user = userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+data.getId()));
        user.setRole(authUtil.getRole(data.getRole()));
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO toggleUserActivityStatus(String id) {
        UUID uuid = UUID.fromString(id);
        AppUser user = userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));
        boolean currentStatus = user.isActive();
        user.setActive(!currentStatus);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public boolean isAdmin(String id) {
        UUID uuid = UUID.fromString(id);
        AppUser user = userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));
        return user.getRole().equals(RoleType.ADMIN);
    }

    @Override
    public boolean isSeller(String id) {
        UUID uuid = UUID.fromString(id);
        AppUser user = userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));
        return user.getRole().equals(RoleType.SELLER);
    }

    @Override
    public boolean isCustomer(String id) {
        UUID uuid = UUID.fromString(id);
        AppUser user = userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));
        return user.getRole().equals(RoleType.CUSTOMER);
    }

    @Override
    public UserResponseDTO findById(String id) {
        UUID uuid = UUID.fromString(id);
        AppUser user = userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO findByCredential(String credential) {
        AppUser user = userRepository.findByUsernameOrEmail(credential, credential)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+credential));
        return modelMapper.map(user, UserResponseDTO.class);
    }


}
