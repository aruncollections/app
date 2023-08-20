package com.app.api;

import com.app.api.dto.UserInfo;
import com.app.domain.user.User;
import javax.servlet.http.HttpServletRequest;

import com.app.service.security.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;

  private final AuthenticationManager authenticationManager;

  @GetMapping("current-user")
  @ResponseBody
  public ResponseEntity<User> getCurrentUser(Principal principal) {
    return userService.getCurrentUser(principal)
            .map(user -> ResponseEntity.ok().body(user))
            .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("authenticate")
  public ResponseEntity<HttpStatus> login(@RequestBody @NonNull User user) throws Exception {
    Authentication authObject = null;
    try {
      log.info("User {} logging in", user.getEmailId());
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authObject);
    } catch (BadCredentialsException e) {
      log.error("Invalid login by user {}", user.getEmailId());
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok().build();
  }

  @PostMapping("signup")
  public ResponseEntity<UserInfo> signupUser(@RequestBody @NonNull UserInfo userInfo) {
      userService.createNewUser(userInfo);
      return ResponseEntity.created(null).build();
  }

  @PutMapping("activate/{emailId}")
  public ResponseEntity<UserInfo> activate(@PathVariable @NonNull String emailId) {
    userService.setActiveStatus(emailId, true);
    return ResponseEntity.ok().build();
  }

  @PutMapping("inactivate/{emailId}")
  public ResponseEntity<UserInfo> inactivate(@PathVariable @NonNull String emailId) {
    userService.setActiveStatus(emailId, false);
    return ResponseEntity.ok().build();
  }

  @GetMapping("logout")
  public String logout(HttpServletRequest request) {
    request.getSession().invalidate();
    return "redirect:/login";
  }

}
