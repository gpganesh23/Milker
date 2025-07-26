package com.example.trail.Tables;




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference; // Import this

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table; // Added for Order constructor

@Entity
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderTrackingNumber;
    private String customerMobileNo;

    private String deliveryAddressLine1;
    private String deliveryAddressLine2;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryPincode;

    private double totalAmount;

    private LocalDateTime dateCreated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY) // Added FetchType.LAZY for performance
    @JsonManagedReference // <--- ADD THIS ANNOTATION
    private List<OrderItem> orderItems = new ArrayList<>();

    // Constructors
    public Order() {
        this.orderTrackingNumber = UUID.randomUUID().toString();
        this.dateCreated = LocalDateTime.now();
    }

    // Add convenience method to add items
    public void addOrderItem(OrderItem item) {
        if (item != null) {
            if (orderItems == null) {
                orderItems = new ArrayList<>();
            }
            orderItems.add(item);
            item.setOrder(this);
        }
    }

    // Getters and Setters (omitted for brevity, assume they are still here)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderTrackingNumber() { return orderTrackingNumber; }
    public void setOrderTrackingNumber(String orderTrackingNumber) { this.orderTrackingNumber = orderTrackingNumber; }
    public String getCustomerMobileNo() { return customerMobileNo; }
    public void setCustomerMobileNo(String customerMobileNo) { this.customerMobileNo = customerMobileNo; }
    public String getDeliveryAddressLine1() { return deliveryAddressLine1; }
    public void setDeliveryAddressLine1(String deliveryAddressLine1) { this.deliveryAddressLine1 = deliveryAddressLine1; }
    public String getDeliveryAddressLine2() { return deliveryAddressLine2; }
    public void setDeliveryAddressLine2(String deliveryAddressLine2) { this.deliveryAddressLine2 = deliveryAddressLine2; }
    public String getDeliveryCity() { return deliveryCity; }
    public void setDeliveryCity(String deliveryCity) { this.deliveryCity = deliveryCity; }
    public String getDeliveryState() { return deliveryState; }
    public void setDeliveryState(String deliveryState) { this.deliveryState = deliveryState; }
    public String getDeliveryPincode() { return deliveryPincode; }
    public void setDeliveryPincode(String deliveryPincode) { this.deliveryPincode = deliveryPincode; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}