package vn.khanhduc.backendservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordRequest {
    private Long id;
    private String oldPassword;
    private String newPassword;
}
