package com.example.cinepick_be.controller;

import com.example.cinepick_be.dto.MbtiDTO;
import com.example.cinepick_be.dto.MbtiResultDTO;
import com.example.cinepick_be.dto.UserDTO;
import com.example.cinepick_be.entity.Mbti;
import com.example.cinepick_be.entity.Question;
import com.example.cinepick_be.entity.User;
import com.example.cinepick_be.repository.MbtiRepository;
import com.example.cinepick_be.repository.UserRepository;
import com.example.cinepick_be.service.QuestionService;
import com.example.cinepick_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class MbtiController {

   @Value("${file.upload-dir}")
   private String uploadDir;

   private final UserService userService;
   private final UserRepository userRepository;
   private final MbtiRepository mbtiRepository;
   private final QuestionService questionService;

   @PutMapping("/submit")
   public ResponseEntity<UserDTO> mbtiResult(@RequestBody MbtiResultDTO mbtiResultDTO){
      if(mbtiResultDTO.getMbtiResult() == null || mbtiResultDTO.getMbtiResult().size() !=12)
         return ResponseEntity.badRequest().build();
      List<Integer> result = mbtiResultDTO.getMbtiResult();
      int[] mbti = new int[4]; // e/i, s/n, f/t, p/j
      for(int i =0;i<12;i++){
         int cur = result.get(i);
         if(cur==1)
            mbti[i%4]++;
         else if(cur==2) mbti[i%4]--;
         else throw new IllegalArgumentException();
      }
      StringBuilder mbtiResult= new StringBuilder();
      if(mbti[0]>0) // e/i
         mbtiResult.append("E");
      else mbtiResult.append("I");
      if(mbti[1]>0) // s/n
         mbtiResult.append("S");
      else mbtiResult.append("N");
      if(mbti[2]>0) // f/t
         mbtiResult.append("F");
      else mbtiResult.append("T");
      if(mbti[3]>0) // p/j
         mbtiResult.append("P");
      else mbtiResult.append("J");

      System.out.println("mbtiResult: "+mbtiResult);

      UserDTO user= userService.updateUserWithMbti(mbtiResultDTO.getUserId(),mbtiResult.toString());

      return ResponseEntity.ok(user);


   }

   @GetMapping
   public List<Question> getQuestion(){
      return questionService.getAllQuestion();
   }

   @GetMapping("/result")
   public ResponseEntity<MbtiDTO> getUser(Authentication authentication) {
      String userId =authentication.getName();
      User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

      UserDTO userDTO = new UserDTO(user);

      Mbti mbti = mbtiRepository.findByMbti(userDTO.getMbti());


      MbtiDTO mbtiDTO = new MbtiDTO();
      mbtiDTO.setMbti(mbti.getMbti());
      mbtiDTO.setPerson(mbti.getPerson());
      mbtiDTO.setQuote(mbti.getQuote());
      mbtiDTO.setDescription(mbti.getDescription());
      mbtiDTO.setStory(mbti.getStory());

      mbtiDTO.setProfileUrl("http://52.78.3.251:8080/uploads/mbti/" + mbti.getProfileImage());
//      mbtiDTO.setProfileUrl("http://localhost:8080/uploads/mbti/" + mbti.getProfileImage());

      mbtiDTO.setGoodChemistry(mbti.getGoodChemistry().getMbti());
      mbtiDTO.setBadChemistry(mbti.getBadChemistry().getMbti());

      return ResponseEntity.ok(mbtiDTO);
   }
}
