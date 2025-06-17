package com.example.cinepick_be.controller;

import com.example.cinepick_be.dto.LikeDTO;
import com.example.cinepick_be.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/my/like")
@RequiredArgsConstructor
public class LikeController {
   private final LikeService likeService;
   @GetMapping()
   public ResponseEntity<List<LikeDTO>> getLikes(Authentication authentication){
      String userId= authentication.getName();
      List<LikeDTO> likes =  likeService.like(userId);
      return ResponseEntity.ok(likes);
   }

   @DeleteMapping("/delete/{movieId}")
   public ResponseEntity<Void> deleteLike(
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long movieId){
      likeService.addOrRemoveLike(userDetails.getUsername(), movieId);
      return ResponseEntity.noContent().build();
   }

}
