package vn.khanhduc.backendservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.khanhduc.backendservice.dto.request.UserCreationRequest;
import vn.khanhduc.backendservice.dto.response.MockupUserResponse;
import vn.khanhduc.backendservice.dto.response.ResponseData;
import vn.khanhduc.backendservice.dto.response.UserCreationResponse;
import vn.khanhduc.backendservice.dto.response.UserResponse;
import vn.khanhduc.backendservice.service.UserService;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                     @RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "10") int size
                                      ) {

        MockupUserResponse response1 = new MockupUserResponse();
        response1.setId(1L);
        response1.setFirstName("Khanh");
        response1.setLastName("Duc");
        response1.setUserName("Khanh Duc");
        response1.setEmail("duc@gmail.com");
        response1.setPhone("0123456789");
        response1.setBirthDate(LocalDate.now());
        response1.setGender("MALE");

        MockupUserResponse response2 = new MockupUserResponse();
        response2.setId(2L);
        response2.setFirstName("Anh");
        response2.setLastName("Hong");
        response2.setUserName("Anh Hong");
        response2.setEmail("AnhHong@gmail.com");
        response2.setPhone("0123456789");
        response2.setBirthDate(LocalDate.now());
        response2.setGender("FEMALE");

        List<MockupUserResponse> list = List.of(response1, response2);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("code", HttpStatus.OK.value());
        result.put("message", "Users");
        result.put("data", list);

        return result;

    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    ResponseData<UserCreationResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        log.info("Create User: {}", request);

        return ResponseData.<UserCreationResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(userService.save(request))
                .build();
    }

    @GetMapping("/{userId}")
    ResponseData<UserResponse> getUserById(@PathVariable @Min(1) Long userId) {
        var result = userService.findById(userId);

        return ResponseData.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .data(result)
                .build();
    }

}
