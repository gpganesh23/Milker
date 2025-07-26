package com.example.trail.Service;


import com.example.trail.Tables.Product;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service // Mark this as a Spring Service
public class ProductService {

    // Your static list of products, now managed by this service
    private final List<Product> products = Arrays.asList(
            new Product(1L, "Full Cream Milk (1L)", "Rich and creamy, perfect for your daily needs.", 70.00, "images/fulcreammilk.jpg"),
            new Product(2L, "Skim Milk (1L)", "Low fat, healthy choice for a balanced diet.", 60.00, "images/skimmilk.jpg"),
            new Product(3L, "Toned Milk (1L)", "A good balance of creaminess and health.", 65.00, "images/tonedmilk.jpg"),
            new Product(4L, "Cow Milk (1L)", "Farm fresh cow milk, natural and nutritious.", 75.00, "images/cowmilk.jpg"),
            new Product(5L, "Buffalo Milk (1L)", "High in fat and protein, great for traditional recipes.", 80.00, "images/buffalomilk.jpg"),
            new Product(6L, "Curd (500g)", "Homemade style curd, thick and delicious.", 40.00, "images/curd.jpg"),
            new Product(7L, "Paneer (200g)", "Fresh and soft cottage cheese, ideal for cooking.", 120.00, "images/paneer.jpg"),
            new Product(8L, "Ghee (500ml)", "Pure cow ghee, aromatic and healthy.", 350.00, "images/ghee.jpg")
    );

    public List<Product> findAllProducts() {
        return products;
    }

    public Optional<Product> findProductById(Long id) {
        return products.stream()
                       .filter(p -> p.getId().equals(id))
                       .findFirst();
    }
}