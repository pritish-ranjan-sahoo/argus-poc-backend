package com.ecommerce.user;

import com.ecommerce.common.dto.UpdateRoleRequestDTO;
import com.ecommerce.common.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User APIs", description = "APIs to perform operations on the user data")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Get user by the User ID", description = "Get user by the User ID provided as path parameter")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        UserResponseDTO data = userService.findById(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get All Users", description = "Get all users chunk by chunk providing filtering factors in request parameters")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "username") String attribute
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(attribute).ascending());
        Page<UserResponseDTO> data = userService.getAllUsers(pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{role}")
    @Operation(summary = "Get all users having a specific role", description = "Get all users having a specific role")
    public ResponseEntity<Page<UserResponseDTO>> getUsersByRole(
            @PathVariable String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "username") String attribute
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(attribute).ascending());
        Page<UserResponseDTO> data = userService.getUsersByRole(role,pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/get-active")
    @Operation(summary = "Get active users", description = "Get all users having active account state")
    public ResponseEntity<Page<UserResponseDTO>> getActiveUsers(
            @PathVariable String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "username") String attribute
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(attribute).ascending());
        Page<UserResponseDTO> data = userService.getActiveUsers(pageable);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Block user by ID", description = "Soft delete any user by using the userId")
    public ResponseEntity<UserResponseDTO> blockUser(@PathVariable String id) {
        UserResponseDTO data = userService.toggleUserActivityStatus(id);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/change-role")
    @Operation(summary = "Change user role", description = "Promote or Demote User by the user ID")
    public ResponseEntity<UserResponseDTO> changeRole(UpdateRoleRequestDTO request) {
        UserResponseDTO data = userService.updateUserRole(request);
        return ResponseEntity.ok(data);
    }
}
