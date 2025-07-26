package com.example.trail.Controller;


import java.util.Collections;
import java.util.List; // Import List
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping; // Import GetMapping
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.trail.DTO.OrderRequest;
import com.example.trail.Service.OrderService;
import com.example.trail.Tables.Order;

@RestController
@RequestMapping("/api/checkout") // Or @RequestMapping("/api/orders") if you create a separate OrderController
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Map<String, String>> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            // Call the OrderService to place the order and save to DB
            Order placedOrder = orderService.placeOrder(orderRequest);

            // Return the order tracking number
            return ResponseEntity.ok(Collections.singletonMap("orderId", placedOrder.getOrderTrackingNumber()));

        } catch (RuntimeException e) {
            System.err.println("Error placing order: " + e.getMessage());
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Failed to place order: " + e.getMessage()));
        }
    }

    // NEW ENDPOINT: Get all orders
    @GetMapping("/orders") // Example: GET /api/checkout/orders
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no orders
        }
        return ResponseEntity.ok(orders);
    }

    // Optional: Get order by ID or Tracking Number
    /*
    @GetMapping("/orders/{orderTrackingNumber}")
    public ResponseEntity<Order> getOrderByTrackingNumber(@PathVariable String orderTrackingNumber) {
        // Implement this in OrderService and use it here
        Order order = orderService.getOrderByTrackingNumber(orderTrackingNumber);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }
    */
}