package vn.khanhduc.backendservice.service;

import vn.khanhduc.backendservice.dto.request.UserCreationRequest;
import vn.khanhduc.backendservice.dto.request.UserUpdateRequest;
import vn.khanhduc.backendservice.dto.response.UserCreationResponse;
import vn.khanhduc.backendservice.dto.response.UserResponse;

public interface UserService {

    UserResponse findById(Long id);

    UserResponse findByEmail(String email);

    UserCreationResponse save(UserCreationRequest req);

    void update(UserUpdateRequest req);

    UserResponse userDetail(Long userId);

}
