package vn.khanhduc.backendservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.khanhduc.backendservice.dto.request.SignInRequest;
import vn.khanhduc.backendservice.dto.response.ResponseData;
import vn.khanhduc.backendservice.dto.response.TokenResponse;
import vn.khanhduc.backendservice.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j(topic = "AUTH-CONTROLLER")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    ResponseData<TokenResponse> getAccessToken(@RequestBody SignInRequest request) {
        log.info("SignIn Request");
       var tokenResponse = authenticationService.getAccessToken(request);

        return ResponseData.<TokenResponse>builder()
                .code(HttpStatus.OK.value())
                .data(tokenResponse)
                .build();
    }

    @PostMapping("/refresh")
    ResponseData<TokenResponse> getRefreshToken(@RequestBody String refreshToken) {
        log.info("Refresh Request");

        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken("Dummy-AccessToken")
                .refreshToken("Dummy-RefreshToken")
                .build();

        return ResponseData.<TokenResponse>builder()
                .code(HttpStatus.OK.value())
                .data(tokenResponse)
                .build();
    }
}
