package com.example.trail.Controller;

//Example Controller
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
 @GetMapping("/reviews") // Or "/" if you want it as the home page
 public String showReviewsPage() {
     return "reviews"; // refers to src/main/resources/templates/reviews.html
 }
}
