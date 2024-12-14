package vn.khanhduc.backendservice.service;

import vn.khanhduc.backendservice.dto.request.SignInRequest;
import vn.khanhduc.backendservice.dto.response.TokenResponse;

public interface AuthenticationService {

    TokenResponse getAccessToken(SignInRequest request);
    TokenResponse getRefreshToken(String refreshToken);
}
