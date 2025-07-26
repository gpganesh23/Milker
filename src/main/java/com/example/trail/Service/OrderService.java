package com.example.trail.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.trail.DTO.OrderRequest;
import com.example.trail.Repository.OrderRepository;
import com.example.trail.Tables.Order;
import com.example.trail.Tables.OrderItem;
import com.example.trail.Tables.Product; // Keep this import for the Product object

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService; // <--- CHANGE: Inject ProductService instead of ProductController

    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        // 1. Create a new Order entity
        Order order = new Order();
        order.setOrderTrackingNumber(UUID.randomUUID().toString()); // Generate a unique tracking number
        order.setCustomerMobileNo(orderRequest.getMobileNo());
        order.setTotalAmount(orderRequest.getTotalAmount());

        // Set delivery address details
        if (orderRequest.getDeliveryAddress() != null) {
            order.setDeliveryAddressLine1(orderRequest.getDeliveryAddress().getAddressLine1());
            order.setDeliveryAddressLine2(orderRequest.getDeliveryAddress().getAddressLine2());
            order.setDeliveryCity(orderRequest.getDeliveryAddress().getCity());
            order.setDeliveryState(orderRequest.getDeliveryAddress().getState());
            order.setDeliveryPincode(orderRequest.getDeliveryAddress().getPincode());
        }

        // 2. Add OrderItems to the Order
        if (orderRequest.getCartItems() != null) {
            orderRequest.getCartItems().forEach(cartItemDto -> {
                // Fetch product details to ensure historical accuracy and fill missing fields
                Optional<Product> productOpt = productService.findProductById(cartItemDto.getProductId()); // <--- CHANGE: Use ProductService
                if (productOpt.isEmpty()) {
                    throw new RuntimeException("Product not found for ID: " + cartItemDto.getProductId() + " during order placement.");
                }
                Product product = productOpt.get();

                OrderItem orderItem = new OrderItem(
                    product.getId(),
                    product.getName(), // Use actual product name from DB (or your static list)
                    product.getPrice(), // Use actual product price from DB (or your static list)
                    cartItemDto.getQuantity(),
                    product.getImage() // Use actual product image URL from DB (or your static list)
                );
                order.addOrderItem(orderItem);
            });
        }

        // 3. Save the Order (this will cascade save OrderItems due to CascadeType.ALL)
        Order savedOrder = orderRepository.save(order);

        // 4. Clear the cart after successful order placement
        cartService.clearCart();

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}