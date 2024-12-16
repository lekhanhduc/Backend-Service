package vn.khanhduc.backendservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ErrorResponse {

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private Date timestamp;
    private int status;
    private String path;
    private String error;
    private String message;
}
