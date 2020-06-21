package com.hstoneage.codechallenge.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Follower
{

   @Id
   public Long id;

   @OneToOne
   public Author itIsMe;

   @OneToMany
   public Set<Author> following;
   public LocalDateTime startFollowing;

}
