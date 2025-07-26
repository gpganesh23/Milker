package com.example.trail.Controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.trail.DTO.CommentDTO;
import com.example.trail.DTO.CommentResponseDTO;
import com.example.trail.Service.ReviewService;

@RestController
@RequestMapping("/api")
public class ReviewController {
	
	private ReviewService serviceComment ;
	@Autowired
	public ReviewController(ReviewService serviceComment) {
		super();
		this.serviceComment = serviceComment;
	}
	
	 @PostMapping("/comments")
	 
	    public CommentResponseDTO postComment(@RequestBody CommentDTO request) {
	        return serviceComment.commentService(request);
	    }

	    @GetMapping("/comments")
	 
	    public List<CommentDTO> getAllComments() {
	        return serviceComment.getAllComments(); // You must implement this
	    }

}
