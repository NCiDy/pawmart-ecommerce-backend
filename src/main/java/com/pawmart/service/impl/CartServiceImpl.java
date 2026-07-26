package com.pawmart.service.impl;

import com.pawmart.DTO.Cart.CartItemRequest;
import com.pawmart.DTO.Cart.CartItemResponse;
import com.pawmart.DTO.Cart.CartResponse;
import com.pawmart.entity.Cart;
import com.pawmart.entity.CartItem;
import com.pawmart.entity.Product;
import com.pawmart.entity.User;
import com.pawmart.exception.AppException;
import com.pawmart.repository.CartItemRepository;
import com.pawmart.repository.CartRepository;
import com.pawmart.repository.ProductRepository;
import com.pawmart.repository.UserRepository;
import com.pawmart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private Cart getOrCreateCart(Long userId){
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() ->new AppException(HttpStatus.NOT_FOUND,"User not found"));
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });

    }
    @Override
    public CartResponse addToCart(Long userId,CartItemRequest request) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->new AppException(HttpStatus.NOT_FOUND,"Product not found"));
        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(),product.getId()).orElse(null);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
        } else {
            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );
        }
        cartItemRepository.save(cartItem);
        return getCart(userId);
    }

    @Override
    public CartResponse getCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        List<CartItemResponse> items = cart.getCartItems()
                .stream()
                .map(item -> {
                    CartItemResponse dto = new CartItemResponse();
                    dto.setId(item.getId());
                    dto.setProductId(item.getProduct().getId());
                    dto.setProductName(item.getProduct().getName());
                    dto.setPrice(item.getProduct().getPrice());
                    dto.setQuantity(item.getQuantity());
                    dto.setSubtotal(item.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())));

                    return dto;

                })
                .toList();

        response.setItems(items);
        response.setTotalPrice(
                items.stream()
                        .map(CartItemResponse::getSubtotal)
                        .reduce(
                                java.math.BigDecimal.ZERO,
                                java.math.BigDecimal::add
                        )
        );
        return response;
    }

    @Override
    public CartResponse updateQuantity(Long userId,Long cartItemId,Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new AppException(HttpStatus.BAD_REQUEST,"Cart item does not belong to user");
        }
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return getCart(userId);
    }

    @Override
    public void removeItem(Long userId,Long cartItemId) {
        Cart cart = getOrCreateCart(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Cart item not found"));
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new AppException(HttpStatus.BAD_REQUEST,"Cart item does not belong to user");
        }
        cartItemRepository.delete(cartItem);
    }
}
