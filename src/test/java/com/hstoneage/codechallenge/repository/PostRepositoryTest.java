package com.hstoneage.codechallenge.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.hstoneage.codechallenge.entity.Author;
import com.hstoneage.codechallenge.entity.Post;

@DataJpaTest
public class PostRepositoryTest
{

   @Autowired
   private TestEntityManager entityManager;
   @Autowired
   private PostRepository postRepository;

   private Author givenAuthor;


   @Test public void findPostsByAuthor()  {
      this.givenAuthor = new Author(1L, "first test name","last name" );
      entityManager.persist(givenAuthor);
      entityManager.flush();


      Post post1 = new Post( givenAuthor, "test message");
      Post post2 = new Post( givenAuthor, "second message");
      post1.setId(1L);
      post2.setId(2L);
      entityManager.persist(post1);
      entityManager.persist(post2);
      entityManager.flush();

      Set<Post> found = postRepository.findPostsByAuthor(post1.getAuthor());

      assertThat( found.isEmpty() ).isFalse();
      assertThat( found ).extracting("author").contains(givenAuthor);
   }

   @Test
   public void  canSaveMorePostAsUser() {


   }
}
