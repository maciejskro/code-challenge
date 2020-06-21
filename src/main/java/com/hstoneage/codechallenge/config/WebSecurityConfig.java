package com.hstoneage.codechallenge.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Value("${apps.keepass}")
   private String keepass;


   public JwtFilter jwtAuthenticatinFilter() throws Exception {
      return  new JwtFilter( authenticationManager(), keepass );
   }

   public AuthenticationEntryPoint unauthorizedEntryPoint() {
      return  new JwtAuthenticationEntryPoint();
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception
   {
      http
            .httpBasic().disable()
            .cors().and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                  .antMatchers(SWAGGER_AUTH_WHITELIST).permitAll()
                  .antMatchers("/api/login").permitAll()
                  .antMatchers("/api/**").authenticated()
                  //.antMatchers("/api/test").hasRole("ADMIN")
                  .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint( unauthorizedEntryPoint() )
            .and()
            .addFilter( jwtAuthenticatinFilter() );

   }

   private static final String[] SWAGGER_AUTH_WHITELIST = {
         "/v2/api-docs",
         "/configuration/ui",
         "/configuration/security",
         "/swagger-resources/**",
         "/webjars/**",
         "/swagger-ui.html"
   };

   @Bean AuthenticationSuccessHandler successHandler() {
      final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
      successHandler.setRedirectStrategy(new NoRedirectStrategy());
      return successHandler;
   }

   @Bean
   AuthenticationEntryPoint forbiddenEntyry() {
      return  new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
   }

}
