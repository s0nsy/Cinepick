package com.example.cinepick_be.dto;

import com.example.cinepick_be.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
   private String user;
   private Movie movie;
}
