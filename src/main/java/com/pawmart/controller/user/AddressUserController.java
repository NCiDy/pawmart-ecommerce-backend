package com.pawmart.controller.user;

import com.pawmart.DTO.Address.AddressRequest;
import com.pawmart.DTO.Address.AddressResponse;
import com.pawmart.exception.AppException;
import com.pawmart.repository.UserRepository;
import com.pawmart.security.JwtService;
import com.pawmart.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/address")
@RequiredArgsConstructor
@Tag(name = "User - Address")
public class AddressUserController {
    private final AddressService addressService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private Long getUserId(String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found")).getId();
    }

    @PostMapping
    public ResponseEntity<AddressResponse> create(@Valid @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody AddressRequest request) {
        return ResponseEntity.ok(
                addressService.create(getUserId(authHeader), request));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return ResponseEntity.ok(addressService.getAll(getUserId(authHeader)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> update(@Valid @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,@PathVariable Long id,@RequestBody AddressRequest request) {
       return ResponseEntity.ok(addressService.update(getUserId(authHeader),id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,@PathVariable Long id) {
        addressService.delete(getUserId(authHeader), id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/default")
    public ResponseEntity<Void> setDefault(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable Long id) {
        addressService.setDefault(getUserId(authHeader),id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/default")
    public ResponseEntity<AddressResponse> getDefault(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return ResponseEntity.ok(addressService.getDefault(getUserId(authHeader)));
    }
}
