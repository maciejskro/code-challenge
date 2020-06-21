package com.hstoneage.codechallenge.controller;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hstoneage.codechallenge.entity.FollowedPost;
import com.hstoneage.codechallenge.entity.MessageToSave;
import com.hstoneage.codechallenge.entity.Post;
import com.hstoneage.codechallenge.service.PostService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequestMapping @RestController @RequiredArgsConstructor public class PostController
{

   private final PostService postService;

   // Posting
   @PostMapping("/api/post/save")
   @ApiOperation(value = "Save my post. Send to it only message as string. A user should be able to post a 140 character message.")
   public ResponseEntity savePost(@RequestBody MessageToSave post)  {
      String who = SecurityContextHolder.getContext().getAuthentication().getName();
      if (post.getMessage().length() > 140)
      {
         ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Too long message");
      }
      Post result = postService.addPost(post.getMessage(), who);
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
   }

   // Wall
   @GetMapping("/api/posts/{userId}")
   @ApiOperation(value = "A user should be able to see a list of the messages they've posted, in reverse chronological order.")
   public Set<FollowedPost> getUserPost(Long userId)  {
      String who = SecurityContextHolder.getContext().getAuthentication().getName();
      return postService.getUserPost(who);
   }

   // Timeline
   @GetMapping("/api/post/following")
   @ApiOperation(value = "A user should be able to see a list of the messages posted by all the people they follow, in reverse chronological order.")
   public Set<FollowedPost> getPostOfFollowedAutor()  {
      String who = SecurityContextHolder.getContext().getAuthentication().getName();
      TreeSet<FollowedPost> postSet = new TreeSet<>(new Comparator<FollowedPost>()
      {
         @Override public int compare(FollowedPost o1, FollowedPost o2)
         {
            return o1.getCreatedDate().compareTo(o2.getCreatedDate());
         }
      });
      postSet.addAll(postService.getMyFollowedPosts(who));
      return postSet;
   }

}
