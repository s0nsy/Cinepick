package com.example.cinepick_be.service;

import com.example.cinepick_be.entity.Genre;
import com.example.cinepick_be.entity.User;
import com.example.cinepick_be.repository.MbtiRepository;
import com.example.cinepick_be.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TmdbService {

   private final UserRepository userRepository;
   private final MbtiRepository mbtiRepository;
   @Value("${tmdb.token}")
   private String tmdbToken;

   public TmdbService(UserRepository userRepository, MbtiRepository mbtiRepository) {
      this.userRepository = userRepository;
      this.mbtiRepository = mbtiRepository;
   }

   // TMDB 헤더
   public ResponseEntity<String> getHeader(String url) {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + tmdbToken);

      HttpEntity<String> entity = new HttpEntity<>(headers);

      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
      return response;
   }

   // TMDB 영화 전체 조회
   public ResponseEntity<String> getMovies() {

      String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/trending/movie/week")
            .queryParam("language", "ko-KR")
            .build()
            .toUriString();
      ResponseEntity<String> response = getHeader(url);

      System.out.println(response);
      return response;
   }

   // TMDB 영화 성향 별 조회
   public ResponseEntity<String> getRecommendMovies(String username) {

      User user = userRepository.findByUserId(username)
            .orElseThrow(() -> new AccessDeniedException(""));

      String mbti = user.getMbti().getMbti();

      List<Genre> userGenres = mbtiRepository.findGenresByMbtiId(mbti);

      String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/discover/movie")
            .queryParam("with_genres", userGenres.stream()
                  .map(genre -> String.valueOf(genre.getId()))
                  .collect(Collectors.joining(",")))
            .queryParam("language", "ko-KR")
            .build()
            .toUriString();

      ResponseEntity<String> response = getHeader(url);
      return response;
   }


   // TMDB 영화 찾기
   public ResponseEntity<String> searchMovie(String keyword) {
      String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/search/movie")
            .queryParam("query", keyword)
            .queryParam("language", "ko-KR")
            .build()
            .toUriString();

      ResponseEntity<String> response = getHeader(url);

      return response;

   }

   // TMDB 영화 필터링
   public ResponseEntity<String> filterMovie(List<Integer> genres) {
      String genre = genres.stream().map(String::valueOf).collect(Collectors.joining(","));
      String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/discover/movie")
            .queryParam("with_genres", genre)
            .queryParam("language", "ko-KR")
            .build()
            .toUriString();

      ResponseEntity<String> response = getHeader(url);

      return response;
   }

   // TMDB 영화 별 시청 가능 서비스 조회
   public Map<String,List<String>> getWatchMovieService(Long movieId) throws JsonProcessingException {
      String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/movie")
            .pathSegment(String.valueOf(movieId))
            .pathSegment("watch", "providers") // =/watch/providers
            .build()
            .toUriString();
      ResponseEntity<String> response = getHeader(url);

      ObjectMapper mapper= new ObjectMapper();
      JsonNode root = mapper.readTree(String.valueOf(response.getBody()).toString());
      JsonNode krRoot = root.path("results").path("KR");
      System.out.println(krRoot);
      String[] types = {"flatrate","ads","buy","rent","free"};
      Map<String,List<String>> result = new HashMap<>();
      for(String type: types){
         JsonNode typeNode = krRoot.path(type);
         if(typeNode.isArray()){
            List<String> list = new ArrayList<>();
            for(JsonNode provider: typeNode){
               String providerName = provider.path("provider_name").asText();
               list.add(providerName);
            }
            result.put(type,list);
         }
      }
      return result;
   }

   // TMDB 영화 상세 페이지
   public  ResponseEntity<String> getMovieDetail(Long movieId){
      String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/movie")
            .pathSegment(String.valueOf(movieId))
            .queryParam("language", "ko-KR")
            .build()
            .toUriString();
      System.out.println("url: "+ url);
      ResponseEntity<String> response= getHeader(url);

      return response;
   }

   // TMDB 영화 기본 정보 조회
   public Map<String, String> getMovieInfo(Long movieId) throws JsonProcessingException {
      String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/movie")
            .pathSegment(String.valueOf(movieId))
            .queryParam("language", "ko-KR")
            .build()
            .toUriString();
      System.out.println("url: "+ url);
      ResponseEntity<String> response= getHeader(url);

      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(String.valueOf(response.getBody()).toString());
      JsonNode imageUri = root.path("poster_path");
      String imageUrl = "https://image.tmdb.org/t/p/w500".concat(imageUri.toString());
      JsonNode title = root.path("title");
      Map<String, String> recommendMovies = new HashMap<>();
      recommendMovies.put("movieId",movieId.toString());
      recommendMovies.put("title",title.asText());
      recommendMovies.put("imageUrl",imageUrl);

      return recommendMovies;

   }

}
