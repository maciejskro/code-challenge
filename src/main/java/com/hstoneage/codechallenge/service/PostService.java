package com.hstoneage.codechallenge.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hstoneage.codechallenge.entity.Author;
import com.hstoneage.codechallenge.entity.FollowedPost;
import com.hstoneage.codechallenge.entity.Follower;
import com.hstoneage.codechallenge.entity.Post;
import com.hstoneage.codechallenge.repository.AuthorRepository;
import com.hstoneage.codechallenge.repository.FollowRepository;
import com.hstoneage.codechallenge.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
public class PostService {

   private final PostRepository postRepository;
   private final AuthorRepository authorRepository;
   private final FollowRepository followRepository;

   @Autowired
   public PostService(PostRepository postRepository, AuthorRepository authorRepository,
         FollowRepository followRepository)
   {
      this.postRepository = postRepository;
      this.authorRepository = authorRepository;
      this.followRepository = followRepository;
   }

   public Set<Post> getMyPosts( Author author) {
      return  postRepository.findPostsByAuthor(author);
   }

   public Set<FollowedPost> getUserPost(String userNickname)
   {
      Optional<Author> author = authorRepository.findAuthorByNickName(userNickname);
      return author.isPresent()? postRepository.findPostsByAuthorOrderByCreatedDateDesc(author.get()) : Collections.emptySet();
   }

   public Post addPost(String post , String who)  {
      Author author = authorRepository.findAuthorByNickName(who).get();
      Post post2save = new Post(author, post);
      post2save.setCreatedDate(LocalDateTime.now());
      Post result =postRepository.save(post2save);
      return  result;
   }

   public Set<Post> getMyFollowedPosts(String who)
   {
      Author author = authorRepository.findAuthorByNickName(who).get();
      Follower follower = followRepository.findFollowerByItIsMe(author);

      Set<Post> getResult = null;
      for ( Author a : follower.getFollowing() )
           getResult.addAll( postRepository.findPostsByAuthor(a) );
      return getResult;
   }
}

