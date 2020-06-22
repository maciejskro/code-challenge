package com.hstoneage.codechallenge.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.hstoneage.codechallenge.entity.Author;
import com.hstoneage.codechallenge.entity.Follower;

@DataJpaTest
class FollowRepositoryTest
{
   @Autowired
   private TestEntityManager entityManager;

   @Autowired
   private FollowRepository followRepository;

   @Test
   void findFollowerByItIsMe() {
      Author  itisme = new Author();
      itisme.setNickName("maciejskro");
      itisme.setFirstName("maciej");
      itisme.setLastName("skro");
      entityManager.persist(itisme);
      entityManager.flush();

      Author alice = new Author();
      alice.setNickName("alice");
      alice.setFirstName("alice");
      alice.setLastName("aaa");
      entityManager.persist(alice);
      entityManager.flush();

      Author bob = new Author();
      bob.setNickName("bob");
      bob.setFirstName("Bob");
      bob.setLastName("bbbb");
      entityManager.persist(bob);
      entityManager.flush();

      Follower follower = new Follower();
      follower.setItIsMe(alice);
      follower.setFollowing(Stream.of(bob,itisme).collect(Collectors.toSet()));

      entityManager.persist(follower);
      entityManager.flush();

      Follower gettedFollower = followRepository.findFollowerByItIsMe(alice);

      assertThat(gettedFollower.getFollowing()).contains(bob);
      assertThat(gettedFollower.getItIsMe()).isEqualTo(alice);

   }

}
