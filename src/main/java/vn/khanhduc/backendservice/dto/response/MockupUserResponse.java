package vn.khanhduc.backendservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
public class MockupUserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phone;
    private String gender;
    private LocalDate birthDate;

}
