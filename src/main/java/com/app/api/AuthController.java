package com.app.api;

import com.app.domain.user.User;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class AuthController {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @PostMapping("authenticate")
  public ResponseEntity<HttpStatus> login(@RequestBody User user) throws Exception {
    Authentication authObject = null;
    try {
      log.info("User {} logging in", user.getEmailId());
      log.info("User {} logging in", passwordEncoder.encode("adminp@ss"));
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authObject);
    } catch (BadCredentialsException e) {
      log.error("Invalid login by user {}", user.getEmailId());
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok().build();
  }

  @GetMapping("logout")
  public String logout(HttpServletRequest request) {
    request.getSession().invalidate();
    return "redirect:/login?logout";
  }
}
