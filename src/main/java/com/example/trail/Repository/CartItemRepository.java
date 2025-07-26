package com.example.trail.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.trail.Tables.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Custom method to find a CartItem by its productId
    Optional<CartItem> findByProductId(Long productId);
}