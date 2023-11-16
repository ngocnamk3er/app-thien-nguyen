package danentang.app_thien_nguyen.demo;

import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/demo-controller")
@RequiredArgsConstructor
public class DemoController {
  private final HttpServletRequest request;

  @GetMapping
  public ResponseEntity<String> sayHello() {
    System.out.println("--------------------------");
    System.out.println(request.getAttribute("userName"));
    System.out.println("--------------------------");

    return ResponseEntity.ok("Hello " +request.getAttribute("userName")+ " from 1234 secured endpoint ");
  }

}
