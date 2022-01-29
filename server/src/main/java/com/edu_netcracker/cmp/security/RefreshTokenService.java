package com.edu_netcracker.cmp.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.edu_netcracker.cmp.entities.RefreshToken;
import com.edu_netcracker.cmp.entities.repositories.RefreshTokenRepository;
import com.edu_netcracker.cmp.entities.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RefreshTokenService {
    private Long refreshTokenDurationMs = 120000L;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsersRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenDurationMs*1000));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(String userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
