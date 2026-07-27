package com.pawmart.controller.user;

import com.pawmart.DTO.Cart.CartItemRequest;
import com.pawmart.DTO.Cart.CartResponse;
import com.pawmart.entity.User;
import com.pawmart.exception.AppException;
import com.pawmart.repository.UserRepository;
import com.pawmart.security.JwtService;
import com.pawmart.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/cart")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@Tag(name = "User - Cart")
public class CartUserController {
    private final CartService cartService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody CartItemRequest request) {
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->new AppException(HttpStatus.NOT_FOUND,"User not found"));
        return ResponseEntity.ok(
                cartService.addToCart(user.getId(), request)
        );
    }
    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->new AppException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(cartService.getCart(user.getId()));
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> updateQuantity(@Valid @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,@PathVariable Long cartItemId,@RequestParam Integer quantity) {
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->new AppException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(cartService.updateQuantity(user.getId(),cartItemId,quantity));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,@PathVariable Long cartItemId) {
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->new AppException(HttpStatus.NOT_FOUND, "User not found"));
        cartService.removeItem(user.getId(),cartItemId);
        return ResponseEntity.noContent().build();
    }
}
