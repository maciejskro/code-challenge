package com.hstoneage.codechallenge.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class Author implements FollowAuthors {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   public Long id;
   @NotNull
   public String firstName;
   @NotNull
   public String lastName;
   @NotNull
   public String nickName;
   public String password;
   public String email;

   @OneToMany(mappedBy = "author")
   List<Post> myPosts;

   @OneToOne
   Follower follower;

   @OneToMany
   List<Roles> rolesList;

}
