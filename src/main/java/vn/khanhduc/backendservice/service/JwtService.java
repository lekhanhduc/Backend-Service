package vn.khanhduc.backendservice.service;

import vn.khanhduc.backendservice.common.TokenType;
import java.util.List;

public interface JwtService {

    String generateAccessToken(String email, List<String> authorities);

    String generateRefreshToken( String email, List<String> authorities);

    String extractUsername(String token, TokenType tokenType);

}
