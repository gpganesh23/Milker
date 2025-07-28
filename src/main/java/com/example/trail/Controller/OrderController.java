package com.example.trail.Controller;


import com.example.trail.Service.OrderService;
import com.example.trail.Tables.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/checkout") 
public class OrderController {

    @Autowired
    private OrderService orderService;

  
    @GetMapping("/orders") 
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        if (orders.isEmpty()) {
            
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.ok(orders);
    }


}
