package com.example.cinepick_be.controller;

import com.example.cinepick_be.service.TmdbService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tmdb")
public class TmdbController {

   private final TmdbService tmdbService;

   // 메인 페이지
   @GetMapping("/movies/all")
   public ResponseEntity<String> getMovies(){
      return tmdbService.getMovies();
   }

   // 메인 페이지(성격 별 추천 영화)
   @GetMapping("/movies/personal")
   public ResponseEntity<String> getRecommendMovies(@AuthenticationPrincipal UserDetails userDetails){
      return tmdbService.getRecommendMovies(userDetails.getUsername());
   }

   // 영화 검색
   @GetMapping("/movies/search")
   public ResponseEntity<String> searchMovies(@RequestParam String keyword){
      return tmdbService.searchMovie(keyword);
   }

   // 영화 필터링 검색
   @GetMapping("/movies/filter")
   public ResponseEntity<String> filterMovies(@RequestParam List<Integer> genres){
      return tmdbService.filterMovie(genres);
   }
   // TMDB 영화 별 시청 가능 서비스 조회
   @GetMapping("/movies/service-provider")
   public Map<String,List<String>> getWatchMovieService(@RequestParam Long movieId){
      try {
         return tmdbService.getWatchMovieService(movieId);
      } catch (JsonProcessingException e) {
         throw new RuntimeException(e);
      }
   }
   // TMDB 영화 상세 페이지
   @GetMapping("/movies/detail")
   public ResponseEntity<String> getMovieDetail(@RequestParam Long movieId){
      return tmdbService.getMovieDetail(movieId);
   }

   // TMDB 영화 기본 정보 조회
   @GetMapping("/movies/info")
   public Map<String, String> getMovieInfo(Long movieId) throws JsonProcessingException {
      return tmdbService.getMovieInfo(movieId);
   }

   }
