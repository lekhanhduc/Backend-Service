package vn.khanhduc.backendservice.dto.response;

import lombok.*;
import vn.khanhduc.backendservice.common.Gender;
import vn.khanhduc.backendservice.dto.request.AddressRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String username;
    private String email;
    private String phone;
    private List<AddressRequest> addresses;
}
