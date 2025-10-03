package fr.rougeux.projet.auction.controller.security;

import fr.rougeux.projet.auction.configuration.security.jwt.JwtUtils;
import fr.rougeux.projet.auction.dto.UserDTO;
import fr.rougeux.projet.auction.dto.jwt.LoginRequest;
import fr.rougeux.projet.auction.dto.jwt.LoginResponse;
import fr.rougeux.projet.auction.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserDetailsService userDetailsService,
                          UserService userService,
                            PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/me")
    public UserDTO authVerif() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // récupère le username/email

        return userService.findByEmail(email);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password!");
        }
        String token = jwtUtils.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        response.addCookie(setCookie(3600, token));
        return new LoginResponse(token, userDetails.getUsername(), roles);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        response.addCookie(setCookie(0, null));
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletResponse response, UserDetails userDetails) {
        String token = jwtUtils.generateToken(userDetails);

        response.addCookie(setCookie(3600, token));
        return ResponseEntity.ok(Map.of("message", "Refresh successfully"));
    }

    private Cookie setCookie(Integer maxAge, String token) {

        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        return cookie;
    }
}
