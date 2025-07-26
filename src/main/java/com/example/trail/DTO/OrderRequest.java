package com.example.trail.DTO;

import java.util.List;

public class OrderRequest {
    private List<OrderItemDto> cartItems; // Or just 'items'
    private String mobileNo;
    private DeliveryAddress deliveryAddress;
    private double totalAmount;

    // Getters and Setters
    public List<OrderItemDto> getCartItems() { return cartItems; }
    public void setCartItems(List<OrderItemDto> cartItems) { this.cartItems = cartItems; }
    // ... similar for other fields
    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
    public DeliveryAddress getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(DeliveryAddress deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}