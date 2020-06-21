package com.hstoneage.codechallenge.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public final class JwtFilter extends BasicAuthenticationFilter  {

   private String keepass;

   static final String TOKEN_PREFIX = "Bearer";
   static final String HEADER_STRING = "Authorization";

   public JwtFilter(AuthenticationManager authenticationManager, String keepasss ) {
      super(authenticationManager);
      this.keepass = keepasss;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
         throws IOException, ServletException {
      String header = request.getHeader("Authorization");
      if (header != null ) {
         UsernamePasswordAuthenticationToken usernameResul = getAuthFromToken(header);
         SecurityContextHolder.getContext().setAuthentication(usernameResul);
      }
      chain.doFilter(request, response);
   }

   private UsernamePasswordAuthenticationToken getAuthFromToken(String header) {
      Jws<Claims> jwtClaim = Jwts.parser().setSigningKey(keepass.getBytes())
            .parseClaimsJws(header.replace("Bearer ", ""));
      String username = jwtClaim.getBody().get("name").toString();
      String role = jwtClaim.getBody().get("role").toString();
      Set<SimpleGrantedAuthority> roles =  Collections.singleton ( new SimpleGrantedAuthority(role));

      UsernamePasswordAuthenticationToken userandtoken =  new UsernamePasswordAuthenticationToken(
            username, null , roles);

      return userandtoken;
   }
   String resolveToken(HttpServletRequest req) {
      String bearerToken = req.getHeader(HEADER_STRING);
      if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
         return bearerToken.substring(7);
      }
      return null;
   }
}
