package com.example.cinepick_be.dto;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MbtiDTO {
   private String mbti;

   private String profileUrl;

   private String person;

   private String quote;

   @Lob
   private String description;


   private String goodChemistry;


   private String badChemistry;

   @Lob
   private String story;


   private List<Long> recommend = new ArrayList<>();

   private List<GenreDTO> genres = new ArrayList<>();

}
