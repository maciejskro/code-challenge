package com.hstoneage.codechallenge.service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hstoneage.codechallenge.entity.Author;
import com.hstoneage.codechallenge.entity.FollowAuthors;
import com.hstoneage.codechallenge.entity.Follower;
import com.hstoneage.codechallenge.repository.AuthorRepository;
import com.hstoneage.codechallenge.repository.FollowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

   private final FollowRepository followRepository;
   private final AuthorRepository authorRepository;

   public void addFollowedToMine(Author author, String[] followedList)
   {
      Follower follower = author.getFollower();
      Set<Author> author2follow = authorRepository.findAuthorByIdIsIn( Arrays.asList(followedList).stream()
                             .map(Long::parseLong).collect(Collectors.toList()) );
      follower.getFollowing().addAll( author2follow );
      followRepository.save(follower);
   }
}
