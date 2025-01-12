package vn.khanhduc.backendservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vn.khanhduc.backendservice.repository.UserRepository;

@Service
public record UserServiceDetail(UserRepository userRepository) {

    public UserDetailsService getUserDetailsService() {
        return userRepository::findByEmail;
    }
}
