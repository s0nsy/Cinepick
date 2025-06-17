package com.example.cinepick_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie")
public class Movie {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   private Long movieId;

   @OneToMany(mappedBy = "movie", cascade= CascadeType.ALL)
   private List<Like> likeList=new ArrayList<>();

   @OneToMany(mappedBy="movie", cascade = CascadeType.ALL)
   private List<Recommend> recommendList= new ArrayList<>();

   @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
   private List<Comment> comment;


}
