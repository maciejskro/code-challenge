package com.hstoneage.codechallenge.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hstoneage.codechallenge.entity.Author;
import com.hstoneage.codechallenge.entity.FollowedPost;
import com.hstoneage.codechallenge.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

   Set<Post> findPostsByAuthor(Author author);

   Set<FollowedPost> findPostsByAuthorOrderByCreatedDateDesc( Author author);

}
