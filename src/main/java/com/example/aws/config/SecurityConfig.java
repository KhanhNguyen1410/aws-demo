package com.example.aws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String[] pathArray = new String[] { "/api/v1/system/**", "/swagger-ui.html", "/v2/api-docs",
    "/swagger-resources/**", "/webjars/**", "/api/v1/auth/**", "/api/v1/firebase/unsubscribe-device" };

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().httpBasic().disable().csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**")
      .anonymous().antMatchers(pathArray).permitAll().anyRequest().authenticated().and().exceptionHandling()
      .authenticationEntryPoint(unauthorizedEntryPoint()).and().sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS).
      and().apply(new JwtConfigurer(jwtTokenProvider));
  }

  @Bean
  public AuthenticationEntryPoint unauthorizedEntryPoint() {
    return (request, response, authException) -> response
      .sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}
