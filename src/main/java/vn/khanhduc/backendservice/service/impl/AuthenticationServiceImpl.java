package vn.khanhduc.backendservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.khanhduc.backendservice.dto.request.SignInRequest;
import vn.khanhduc.backendservice.dto.response.TokenResponse;
import vn.khanhduc.backendservice.exception.AppException;
import vn.khanhduc.backendservice.exception.ErrorCode;
import vn.khanhduc.backendservice.service.AuthenticationService;
import vn.khanhduc.backendservice.service.JwtService;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTH_SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResponse getAccessToken(SignInRequest request) {
        log.info("Get AccessToken");
        List<String> authorities = new ArrayList<>();
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            log.info("Authenticated authorities: {}", authentication.getAuthorities());

            authorities.add(authentication.getAuthorities().toString());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Authorities {}", authorities);

        } catch (Exception e) {
            log.info(e.getMessage());
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String accessToken =  jwtService.generateAccessToken( request.getEmail(), authorities);
        String refreshToken =  jwtService.generateRefreshToken(request.getEmail(), authorities);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse getRefreshToken(String refreshToken) {
        return null;
    }
}
