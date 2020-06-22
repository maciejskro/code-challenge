package com.hstoneage.codechallenge.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
public class Post implements FollowedPost{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   public Long id;

   @ManyToOne
   @NonNull
   public Author author;
   public LocalDateTime createdDate;
   @NonNull
   public String postMessage;

}
