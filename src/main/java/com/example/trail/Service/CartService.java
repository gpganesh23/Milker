package com.example.trail.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import com.example.trail.Controller.ProductController; // REMOVE THIS IMPORT
import com.example.trail.Repository.CartItemRepository;
import com.example.trail.Tables.CartItem;
import com.example.trail.Tables.Product;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService; // <--- CHANGE: Inject ProductService instead of ProductController

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Transactional
    public CartItem addProductToCart(Long productId, int quantity) {
        // Find the product details using the ProductService
        Optional<Product> productOpt = productService.findProductById(productId); // <--- CHANGE: Use ProductService

        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product with ID " + productId + " not found.");
        }

        Product product = productOpt.get();

        // Check if the item already exists in the cart
        Optional<CartItem> existingCartItemOpt = cartItemRepository.findByProductId(productId);

        CartItem cartItem;
        if (existingCartItemOpt.isPresent()) {
            // Update quantity if item exists
            cartItem = existingCartItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Create new cart item
            cartItem = new CartItem(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImage(),
                quantity
            );
        }
        return cartItemRepository.save(cartItem); // Save or update in the database
    }

    @Transactional
    public CartItem updateCartItemQuantity(Long cartItemId, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        return cartItemRepository.findById(cartItemId)
            .map(cartItem -> {
                cartItem.setQuantity(newQuantity);
                return cartItemRepository.save(cartItem);
            })
            .orElseThrow(() -> new RuntimeException("Cart item with ID " + cartItemId + " not found."));
    }

    @Transactional
    public void removeCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new RuntimeException("Cart item with ID " + cartItemId + " not found.");
        }
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    public void clearCart() {
        cartItemRepository.deleteAll();
    }
}