package com.hstoneage.codechallenge.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Roles {

   @Id
   private Long id;
   private String rolename;
}
