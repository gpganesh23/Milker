package com.example.trail.Controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.trail.DTO.OrderRequest;
import com.example.trail.Service.OrderService;
import com.example.trail.Tables.Order;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Map<String, String>> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
           
            Order placedOrder = orderService.placeOrder(orderRequest);

            return ResponseEntity.ok(Collections.singletonMap("orderId", placedOrder.getOrderTrackingNumber()));

        } catch (RuntimeException e) {
            System.err.println("Error placing order: " + e.getMessage());
       
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Failed to place order: " + e.getMessage()));
        }
    }

}