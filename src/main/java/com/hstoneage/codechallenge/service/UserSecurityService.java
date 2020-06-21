package com.hstoneage.codechallenge.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hstoneage.codechallenge.entity.Author;
import com.hstoneage.codechallenge.entity.FollowAuthors;
import com.hstoneage.codechallenge.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

   private AuthorRepository authorRepository;


   @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
   {
      Optional<Author> author = authorRepository.findAuthorByNickName(username);
      UserDetails result = new UserDetails()
      {
         @Override public Collection<? extends GrantedAuthority> getAuthorities()
         {
            return author.isPresent()? author.get().getRolesList().stream()
                  .map( roles -> { return new SimpleGrantedAuthority("ROLE_"+roles); } )
                  .collect(Collectors.toSet() ): Collections.EMPTY_SET;
         }

         @Override public String getPassword()
         {
            return null; // author.get().password;
         }

         @Override public String getUsername()
         {
            return author.get().getNickName();
         }

         @Override public boolean isAccountNonExpired()
         {
            return false;
         }

         @Override public boolean isAccountNonLocked()
         {
            return false;
         }

         @Override public boolean isCredentialsNonExpired()
         {
            return false;
         }

         @Override public boolean isEnabled()
         {
            return false;
         }
      };
      return result;
   }

   public Long saveuser( Author auth) {
      Author result = authorRepository.save(auth);
      return result.getId();
   }
   public Optional<Author> getAuthorByNickname(String nickname) {
      return authorRepository.findAuthorByNickName(nickname);
   }

   public Set<FollowAuthors> getAuthors(Author authContext) {
       return authorRepository.findAuthorByNickNameIsNot(authContext.getNickName());
   }
}
