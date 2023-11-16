package danentang.app_thien_nguyen.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danentang.app_thien_nguyen.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService service;
  private final UserService userService;


  @PostMapping("/register")
public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
    // Kiểm tra trường thông tin rỗng
    if (request.getUsername().isEmpty() || request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse("All fields must be filled in."));
    }

    // Kiểm tra trùng lặp thông tin username hoặc email
    if (userService.existsByUsername(request.getUsername())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse("Username is already taken."));
    }

    if (userService.existsByEmail(request.getEmail())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse("Email is already registered."));
    }

    // Tiến hành đăng ký nếu không có vấn đề nào
    return ResponseEntity.ok(service.register(request));
}


  @GetMapping("/register")
  public String getregister() {
    return "Register page";
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

}
