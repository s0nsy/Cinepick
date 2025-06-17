package com.example.cinepick_be.controller;

import com.example.cinepick_be.entity.Mbti;
import com.example.cinepick_be.repository.MbtiRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@AllArgsConstructor
public class ImageFileController {

   @Autowired
   private MbtiRepository mbtiRepository;

   @CrossOrigin(origins = "http://52.78.3.251:3000")
   @GetMapping("/image/{mbti}")
   public ResponseEntity<FileSystemResource> getImage(@PathVariable String mbti) {
      try {
         Mbti mbtiData = mbtiRepository.findByMbti(mbti);
         if (mbtiData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }

         String basePath;

         if (System.getProperty("os.name").toLowerCase().contains("win")) {
            basePath = "C:/Users/82104/IdeaProjects/Back/uploads/mbti/";
         } else {
            basePath = "/home/ubuntu/Back/uploads/mbti/";
         }
         String imageFilePath = basePath + mbtiData.getProfileImage();
         Path path = Paths.get(imageFilePath).toAbsolutePath();  // 절대 경로로 변환

         System.out.println("Resolved Path: " + path);
         File imageFile = path.toFile();

         if (imageFile.exists()) {
            String fileExtension = imageFile.getName().substring(imageFile.getName().lastIndexOf(".") + 1);
            MediaType mediaType = MediaType.IMAGE_PNG; // 기본값

            if (fileExtension.equalsIgnoreCase("jpeg") || fileExtension.equalsIgnoreCase("jpg")) {
               mediaType = MediaType.IMAGE_JPEG;
            }

            FileSystemResource resource = new FileSystemResource(imageFile);
            return ResponseEntity.ok()
                  .contentType(mediaType)
                  .body(resource);
         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }
}
