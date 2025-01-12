package vn.khanhduc.backendservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.khanhduc.backendservice.common.Gender;
import vn.khanhduc.backendservice.common.UserType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
public class UserCreationResponse implements Serializable {
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String username;
    private String email;
    private String phone;
    private UserType type;
}
