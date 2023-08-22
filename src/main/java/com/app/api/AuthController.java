package com.app.api;

import com.app.api.dto.UserInfo;
import com.app.domain.user.User;
import com.app.service.security.UserService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;

  private final AuthenticationManager authenticationManager;

  @GetMapping("current-user")
  @ResponseBody
  public ResponseEntity<User> getCurrentUser(Principal principal) {
    return userService
        .getCurrentUser(principal)
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
  public ResponseEntity<UserInfo> signupUser(@RequestBody @NonNull @Valid UserInfo userInfo) {
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

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("users")
  public ResponseEntity<List<UserInfo>> getUsers(@RequestParam(required = false) Boolean isActive) {
    val users =
        userService.getAllUsers(isActive).stream()
            .map(
                u ->
                    UserInfo.builder()
                        .id(u.getId())
                        .firstName(u.getFirstName() == null ? "NA" : u.getFirstName())
                        .lastName(u.getLastName() == null ? "NA" : u.getLastName())
                        .emailId(u.getEmailId())
                        .password("NA")
                        .active(u.isActive())
                        .build())
            .collect(Collectors.toList());
    return ResponseEntity.ok().body(users);
  }
}
