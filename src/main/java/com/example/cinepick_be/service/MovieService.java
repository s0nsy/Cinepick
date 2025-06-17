package com.example.cinepick_be.service;

import com.example.cinepick_be.dto.*;
import com.example.cinepick_be.entity.*;
import com.example.cinepick_be.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieService {

   private final UserRepository userRepository;
   private final MovieRepository movieRepository;
   private final CommentRepository commentRepository;
   private final RecommendRepository recommendRepository;

   public Movie addMovie(Long movieId){
      Movie newMovie = new Movie();
      newMovie.setMovieId(movieId);
      movieRepository.save(newMovie);
      return movieRepository.findByMovieId(movieId);
   }

   public void addComment(Long movieId,String content, String userId){
      User user = userRepository.findByUserId(userId)
              .orElseThrow(()->new IllegalArgumentException());
      Movie movie = movieRepository.findByMovieId(movieId);
      if(movie==null) addMovie(movieId);

      Comment comment = new Comment();
      comment.setUser(user);
      comment.setMovie(movie);
      comment.setContent(content);
      comment.setCreatedAt(LocalDateTime.now());
      commentRepository.save(comment);
   }

   public void editComment(Long commentId,String content, String userId) throws AccessDeniedException {
      Comment comment = commentRepository.findById(commentId)
              .orElseThrow(()-> new IllegalArgumentException());
      System.out.println("유저:"+comment.getUser().getUserId());
      if(!comment.getUser().getUserId().equals(userId))
         throw new AccessDeniedException("작성자가 아닙니다.");

      if(content !=null){
         comment.setContent(content);
      }
      comment.setUpdatedAt(LocalDateTime.now());
      commentRepository.save(comment);
   }

   public void deleteComment(Long id){
      Comment comment = commentRepository.findById(id)
              .orElseThrow(()-> new IllegalArgumentException());
      commentRepository.delete(comment);
   }

   public void addRecommend(Long movieId, String content, String userId){
      User user = userRepository.findByUserId(userId)
              .orElseThrow(()->new IllegalArgumentException());
      Movie movie = movieRepository.findByMovieId(movieId);
      if(movie==null) addMovie(movieId);

      Recommend recommend= new Recommend();
      recommend.setUser(user);
      recommend.setMovie(movie);
      recommend.setContent(content);
      recommend.setCreatedAt(LocalDateTime.now());

      recommendRepository.save(recommend);

   }

   public void deleteRecommend(Long id){
      Recommend recommend= recommendRepository.findById(id)
              .orElseThrow(()->new IllegalArgumentException());

      recommendRepository.delete(recommend);
   }

   public List<RecommendDTO> recommends(Long movieId){
      Movie movie = movieRepository.findByMovieId(movieId);
      List<Recommend> recommends=recommendRepository.findByMovieId(movie.getId());
      return recommends.stream()
              .map(recommend -> new RecommendDTO(recommend)).collect(Collectors.toList());
   }
}
