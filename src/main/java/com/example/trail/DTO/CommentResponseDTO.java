package com.example.trail.DTO;

import java.util.List;

public class CommentResponseDTO {
	
	 private CommentDTO savedcom;          // single CommentDTO
	    private List<CommentDTO> retrive;     // list of CommentDTO

	    public CommentResponseDTO(CommentDTO savedcom, List<CommentDTO> retrive) {
	        super();
	        this.savedcom = savedcom;
	        this.retrive = retrive;
	    }

	    public CommentResponseDTO() {
	        super();
	    }

	    public CommentDTO getSavedcom() {
	        return savedcom;
	    }

	    public void setSavedcom(CommentDTO savedcom) {
	        this.savedcom = savedcom;
	    }

	    public List<CommentDTO> getRetrive() {
	        return retrive;
	    }

	    public void setRetrive(List<CommentDTO> retrive) {
	        this.retrive = retrive;
	    }

}


