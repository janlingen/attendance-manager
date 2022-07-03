package com.azakamu.attendancemanager.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * @author janlingen
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final Set<String> tutor;
  private final Set<String> admin;

  public WebSecurityConfiguration(
      @Value("${attendance-manager.role.tutor}") Set<String> tutor,
      @Value("${attendance-manager.role.admin}") Set<String> admin) {
    this.tutor = Collections.unmodifiableSet(tutor);
    this.admin = Collections.unmodifiableSet(admin);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    HttpSecurity security =
        http.authorizeRequests(
            a ->
                a.antMatchers("/css/*", "/js/*", "/error", "/stats", "/static/*", "/favicon.ico")
                    .permitAll()
                    .anyRequest()
                    .authenticated());
    security
        .logout()
        .clearAuthentication(true)
        .deleteCookies()
        .invalidateHttpSession(true)
        .permitAll()
        .and()
        .oauth2Login()
        .userInfoEndpoint()
        .userService(createUserService());
  }

  private OAuth2UserService<OAuth2UserRequest, OAuth2User> createUserService() {
    return new OAuth2UserService<>() {

      final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();

      @Override
      public OAuth2User loadUser(OAuth2UserRequest userRequest)
          throws OAuth2AuthenticationException {
        OAuth2User oauth2User = defaultService.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();
        Map<String, Object> extendedAttributes = new HashMap<>(attributes);
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        String login = attributes.get("login").toString();
        if (tutor.contains(login)) {
          authorities.add(new SimpleGrantedAuthority("ROLE_TUTOR"));
        }
        if (admin.contains(login)) {
          authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return new DefaultOAuth2User(authorities, extendedAttributes, "login");
      }
    };
  }
}
