package com.hstoneage.codechallenge.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hstoneage.codechallenge.entity.Author;
import com.hstoneage.codechallenge.entity.FollowAuthors;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

   Optional<Author> findAuthorByNickName(String nickname);
   Set<FollowAuthors> findAuthorByNickNameIsNot(String nickName);
   Set<Author> findAuthorByIdIsIn (List<Long> id);
}
