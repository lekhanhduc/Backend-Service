package vn.khanhduc.backendservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vn.khanhduc.backendservice.common.Gender;
import vn.khanhduc.backendservice.common.UserType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserCreationRequest implements Serializable {
    @NotBlank(message = "firstName must be not blank")
    private String firstName;

    @NotBlank(message = "firstName must be not blank")
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String username;

    @Email(message = "Email invalid")
    private String email;

    @NotBlank(message = "password must be not blank")
    private String password;

    private String phone;
    private UserType type;
    private List<AddressRequest> addresses;
}
