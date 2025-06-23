package com.example.cinepick_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"goodChemistry", "badChemistry","recommend"})
@Table(name = "mbti")
public class Mbti {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private int id;

   private String mbti;

   private String profileImage;

   private String person;

   private String quote;

   @Lob
   private String description;

   @ManyToOne
   @JoinColumn(name = "good_chemistry_id")
   @JsonManagedReference
   private Mbti goodChemistry;

   @ManyToOne
   @JoinColumn(name = "bad_chemistry_id")
   @JsonManagedReference
   private Mbti badChemistry;

   @Lob
   private String story;

//   @OneToMany(mappedBy = "mbti", cascade = CascadeType.ALL,orphanRemoval = true)
//   @JsonManagedReference
//   private List<Movie> recommend = new ArrayList<>();


   @ManyToMany(fetch= FetchType.EAGER)
   @JoinTable(
         name = "mbti_genre",
         joinColumns = @JoinColumn(name = "mbti_id"),
         inverseJoinColumns = @JoinColumn(name = "genre_id")
   )
   private List<Genre> genres = new ArrayList<>();


   public Mbti(int id, String description, String mbti, String person, String profileImage, String quote, String story) {
      this.id=id;
      this.description=description;
      this.mbti=mbti;
      this.person=person;
      this.profileImage=profileImage;
      this.quote=quote;
      this.story=story;
   }
   public Mbti(List<Genre> genres){
      this.genres=genres;
   }
}
