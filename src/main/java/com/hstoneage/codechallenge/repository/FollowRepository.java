package com.hstoneage.codechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hstoneage.codechallenge.entity.Author;
import com.hstoneage.codechallenge.entity.Follower;

@Repository
public interface FollowRepository extends JpaRepository<Follower, Long> {

   Follower findFollowerByItIsMe(Author auth);
}
