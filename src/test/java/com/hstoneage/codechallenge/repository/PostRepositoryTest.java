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
      this.givenAuthor = new Author();
      givenAuthor.setFirstName("maciek");
      givenAuthor.setLastName("skro");
      givenAuthor.setNickName("maciejskro");
      entityManager.persist(givenAuthor);
      entityManager.flush();


      Post post1 = new Post( givenAuthor, "test message");
      Post post2 = new Post( givenAuthor, "second message");
      entityManager.persist(post1);
      entityManager.persist(post2);
      entityManager.flush();

      Set<Post> found = postRepository.findPostsByAuthor(post1.getAuthor());

      assertThat( found.isEmpty() ).isFalse();
      assertThat( found ).extracting("author").contains(givenAuthor);
   }


}
