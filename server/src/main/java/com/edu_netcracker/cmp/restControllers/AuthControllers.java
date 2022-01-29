package com.edu_netcracker.cmp.restControllers;

import com.edu_netcracker.cmp.entities.RefreshToken;
import com.edu_netcracker.cmp.entities.repositories.UsersRepository;
import com.edu_netcracker.cmp.entities.users.User;
import com.edu_netcracker.cmp.restControllers.payload.TokenRefreshRequest;
import com.edu_netcracker.cmp.restControllers.payload.TokenRefreshResponse;
import com.edu_netcracker.cmp.security.JwtTokenProvider;
import com.edu_netcracker.cmp.security.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/v1/auth")
public class AuthControllers {

    private final AuthenticationManager authenticationManager;
    private UsersRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthControllers(AuthenticationManager authenticationManager, UsersRepository userRepository,
                           JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userRepository.findUsersByUserName(request.getEmail());
            if (user == null) {
                throw new UsernameNotFoundException("Unknown User");
            }
            String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", request.getEmail());
            response.put("access_token", token);
            response.put("refresh_token", refreshTokenService.createRefreshToken(user.getUserName()).getToken());
            response.put("token_type", "Bearer");

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtTokenProvider.createToken(user.getUserName(), user.getRole().name());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
