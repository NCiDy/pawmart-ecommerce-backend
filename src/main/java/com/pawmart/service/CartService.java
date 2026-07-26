package com.pawmart.service;

import com.pawmart.DTO.Cart.CartItemRequest;
import com.pawmart.DTO.Cart.CartResponse;

public interface CartService {
    CartResponse addToCart(Long userId,CartItemRequest request);

    CartResponse getCart(Long userId);

    CartResponse updateQuantity(Long userId,Long cartItemId,Integer quantity);
    void removeItem(Long userId,Long cartItemId);
}
