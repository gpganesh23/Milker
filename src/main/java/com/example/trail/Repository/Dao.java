package com.example.trail.Repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.trail.Tables.Review;



@Repository
public interface Dao extends CrudRepository<Review,Long> {

	List<Review> findTop10ByOrderByIdDesc();


		

}