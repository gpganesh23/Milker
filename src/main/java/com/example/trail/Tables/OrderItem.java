package com.example.trail.Tables;


import com.fasterxml.jackson.annotation.JsonBackReference; // Import this
import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productName;
    private double unitPrice;
    private int quantity;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY) // Added FetchType.LAZY for performance
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference // <--- ADD THIS ANNOTATION
    private Order order;

    // Constructors
    public OrderItem() {
    }

    public OrderItem(Long productId, String productName, double unitPrice, int quantity, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters (omitted for brevity, assume they are still here)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}