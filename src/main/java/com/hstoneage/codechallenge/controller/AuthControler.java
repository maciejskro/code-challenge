package com.hstoneage.codechallenge.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hstoneage.codechallenge.dto.AuthBody;
import com.hstoneage.codechallenge.entity.Author;
import com.hstoneage.codechallenge.entity.FollowAuthors;
import com.hstoneage.codechallenge.service.FollowService;
import com.hstoneage.codechallenge.service.UserSecurityService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthControler {

   private final UserSecurityService userSecurityService;
   private final FollowService followService;

   @PostMapping("/api/login")
   @ApiOperation( value = "method for login to system. You have to provide AuthBody object.")
   public ResponseEntity login (@RequestBody AuthBody authData) throws Exception{
      try {
         String username = authData.getUserNickName();
         UserDetails loadeduser = userSecurityService.loadUserByUsername(username);
         UsernamePasswordAuthenticationToken authenticationToken =
               new UsernamePasswordAuthenticationToken( username, authData.getPassword(),loadeduser.getAuthorities());
         return ResponseEntity.ok("username");
      } catch (NullPointerException e) {
         log.warn("Wrong  login" + e.getMessage());
         return ResponseEntity.status(HttpStatus.OK).body("User or password not match");
      }
   }

   // Helper for
   @GetMapping("/api/get2Follower")
   @ApiOperation(value = "helper method for get author with I just have followed")
   public Set<FollowAuthors> getAuthor2Follow() {
      String who = SecurityContextHolder.getContext().getAuthentication().getName();
      Optional<Author> authContext = userSecurityService.getAuthorByNickname(who);
      return userSecurityService.getAuthors(authContext.get());
   }

   //Following
   @PutMapping("/api/following")
   @ApiOperation(value = "method for set follow set of author. Send to it list of id as string")
   public void setFollowingAuthor(@RequestBody String[]  followed ) {
      String who = SecurityContextHolder.getContext().getAuthentication().getName();
      Optional<Author> authContext= userSecurityService.getAuthorByNickname(who);
      if (authContext.isPresent()){
         String followedList[] = followed;
         followService.addFollowedToMine(authContext.get(), followedList );
      }
   }

}
