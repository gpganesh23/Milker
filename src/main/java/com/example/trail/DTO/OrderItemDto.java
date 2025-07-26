package com.example.trail.DTO;


public class OrderItemDto {
    private Long productId; // Use Long if your Product ID is Long
    private int quantity;
    private double price;

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
} 