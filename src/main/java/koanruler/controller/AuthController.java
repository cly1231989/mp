package koanruler.controller;

import koanruler.auth.JwtAuthenticationRequest;
import koanruler.auth.JwtAuthenticationResponse;
import koanruler.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by chengyuan on 2017/9/24.
 */
@RestController
//@CrossOrigin(origins = "http://localhost:8060")
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @PostMapping("${jwt.route.authentication.path}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException{
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @GetMapping("${jwt.route.authentication.refresh}")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }
}
