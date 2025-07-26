package com.example.trail.Controller;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.trail.Service.CartService;
import com.example.trail.Tables.CartItem;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:8080") // IMPORTANT: Match your frontend's URL here
public class CartController {

    @Autowired
    private CartService cartService;

    // Get all items in the cart
    @GetMapping
    public List<CartItem> getCartItems() {
        return cartService.getAllCartItems();
    }

    // Add a product to the cart (or update quantity if it exists)
    // Request body example: { "productId": 1, "quantity": 1 }
    @PostMapping("/add")
    public ResponseEntity<CartItem> addProductToCart(@RequestBody Map<String, Long> payload) {
        Long productId = payload.get("productId");
        // Default quantity to 1 if not provided, or extract from payload if needed
        int quantity = payload.getOrDefault("quantity", 1L).intValue(); 
        
        if (productId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            CartItem updatedCartItem = cartService.addProductToCart(productId, quantity);
            return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Product not found, etc.
        }
    }

    // Update quantity of an item in the cart
    // Request body example: { "newQuantity": 3 }
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody Map<String, Integer> payload) {
        Integer newQuantity = payload.get("newQuantity");
        if (newQuantity == null || newQuantity <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            CartItem updatedCartItem = cartService.updateCartItemQuantity(cartItemId, newQuantity);
            return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Cart item not found
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Invalid quantity
        }
    }

    // Remove an item from the cart
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId) {
        try {
            cartService.removeCartItem(cartItemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content for successful deletion
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Cart item not found
        }
    }

    // Clear the entire cart
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}