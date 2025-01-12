package vn.khanhduc.backendservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import vn.khanhduc.backendservice.controller.AuthenticationController;
import vn.khanhduc.backendservice.controller.EmailController;
import vn.khanhduc.backendservice.controller.UserController;

@SpringBootTest
class BackendServiceApplicationTests {

    @InjectMocks
    private  UserController userController;

    @InjectMocks
    private AuthenticationController authenticationController;

    @InjectMocks
    private EmailController emailController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userController);
        Assertions.assertNotNull(authenticationController);
        Assertions.assertNotNull(emailController);
    }

}
