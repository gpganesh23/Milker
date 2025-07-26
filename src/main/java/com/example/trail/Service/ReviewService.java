package com.example.trail.Service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trail.DTO.CommentDTO;
import com.example.trail.DTO.CommentResponseDTO;
import com.example.trail.Repository.Dao;
import com.example.trail.Tables.Review;



@Service
public class ReviewService {
	
	private final Dao db ;
	@Autowired
	public ReviewService(Dao db) {
		super();
		this.db = db;
	}

	 private CommentDTO mapEntityToDTO(Review entity) {
	        CommentDTO dto = new CommentDTO();
	        dto.setName(entity.getName());
	        dto.setComment(entity.getComment());
	        return dto;
	    }
	    
	    public CommentResponseDTO commentService(CommentDTO request) {
	        Review topcom = new Review();
	        topcom.setName(request.getName());
	        topcom.setComment(request.getComment());
	        
	        Review savedcom = db.save(topcom);
	        
	        List<Review> lastTen = db.findTop10ByOrderByIdDesc();
	        
	        List<CommentDTO> lastTenDTOs = new ArrayList<>();
	        for (Review e : lastTen) {
	            lastTenDTOs.add(mapEntityToDTO(e));
	        }
	        
	        CommentDTO savedDTO = mapEntityToDTO(savedcom);
	        return new CommentResponseDTO(savedDTO, lastTenDTOs);
	    }
	    
	    public List<CommentDTO> getAllComments() {
	        List<Review> lastTen = db.findTop10ByOrderByIdDesc();
	        
	        List<CommentDTO> result = new ArrayList<>();
	        for (Review e : lastTen) {
	            result.add(mapEntityToDTO(e));
	        }
	        
	        return result;
	    }

}