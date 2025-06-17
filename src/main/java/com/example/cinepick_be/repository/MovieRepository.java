package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
   Movie findByMovieId(Long id);
}
