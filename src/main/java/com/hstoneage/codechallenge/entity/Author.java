package com.hstoneage.codechallenge.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class Author implements FollowAuthors {

   @Id
   @NonNull
   public Long id;
   @NonNull
   public String firstName;
   @NonNull
   public String lastName;
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
