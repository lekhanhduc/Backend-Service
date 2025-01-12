package vn.khanhduc.backendservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import vn.khanhduc.backendservice.common.Gender;
import vn.khanhduc.backendservice.dto.response.UserResponse;
import vn.khanhduc.backendservice.service.JwtService;
import vn.khanhduc.backendservice.service.UserService;
import vn.khanhduc.backendservice.service.UserServiceDetail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    private static UserResponse khanhDuc;
    private static UserResponse johnDoe;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserServiceDetail userServiceDetail;

    @Mock
    private JwtService jwtService;

    @BeforeAll
    static void setUp() {
        khanhDuc = new UserResponse();
        khanhDuc.setId(1L);
        khanhDuc.setFirstName("Le Khanh");
        khanhDuc.setLastName("Duc");
        khanhDuc.setUsername("Le Khanh Duc");
        khanhDuc.setGender(Gender.MALE);
        khanhDuc.setEmail("duc@gmail.com");
        khanhDuc.setPhone("0123456789");

        johnDoe = new UserResponse();
        johnDoe.setId(2L);
        johnDoe.setFirstName("Anh");
        johnDoe.setLastName("Hong");
        johnDoe.setUsername("Anh Hong");
        johnDoe.setGender(Gender.FEMALE);
        johnDoe.setEmail("hong@gmail.com");
        johnDoe.setPhone("0123456789");
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void testGetUserById_Success() throws Exception {
        UserResponse khanhDuc = new UserResponse();
        khanhDuc.setId(8L);
        khanhDuc.setFirstName("Le Khanh");
        khanhDuc.setLastName("Duc");
        khanhDuc.setUsername("Le Khanh Duc");
        khanhDuc.setGender(Gender.MALE);
        khanhDuc.setEmail("duc@gmail.com");
        khanhDuc.setPhone("0123456789");

        when(userService.findById(1L)).thenReturn(khanhDuc);
        mockMvc.perform(get("/api/v1/users/8").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        UserResponse userResponse = userService.findById(8L);
        assertNotNull(userResponse);
        assertEquals(8L, userResponse.getId());
    }
}
