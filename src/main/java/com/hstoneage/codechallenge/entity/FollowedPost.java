package com.hstoneage.codechallenge.entity;

import java.time.LocalDateTime;

public interface FollowedPost{

   FollowAuthors getAuthor();
   LocalDateTime getCreatedDate();
   String getPostMessage();

}
