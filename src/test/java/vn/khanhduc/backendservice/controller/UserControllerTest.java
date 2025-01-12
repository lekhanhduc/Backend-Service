package vn.khanhduc.backendservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vn.khanhduc.backendservice.common.Gender;
import vn.khanhduc.backendservice.dto.response.UserResponse;
import vn.khanhduc.backendservice.service.JwtService;
import vn.khanhduc.backendservice.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // Kích hoạt Mockito
@WebMvcTest(controllers = UserController.class) // Chỉ test controller
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // Đối tượng giả lập để test các request HTTP

    @Mock
    private UserService userService; // Mock UserService

    @InjectMocks
    private UserController userController; // Inject các mock vào controller

    private static UserResponse khanhDuc;

    @BeforeAll
    static void initTestData() {
        khanhDuc = new UserResponse();
        khanhDuc.setId(8L);
        khanhDuc.setFirstName("Le Khanh");
        khanhDuc.setLastName("Duc");
        khanhDuc.setUsername("Le Khanh Duc");
        khanhDuc.setGender(Gender.MALE);
        khanhDuc.setEmail("duc@gmail.com");
        khanhDuc.setPhone("0123456789");
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Khởi tạo các mock
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Cấu hình MockMvc
    }

    @Test
    @WithMockUser(authorities = {"USER"}) // Giả lập người dùng với quyền USER
    void testGetUserById_Success() throws Exception {
        // Giả lập hành vi của UserService
        when(userService.findById(8L)).thenReturn(khanhDuc);

        // Gửi request GET đến API
        mockMvc.perform(get("/api/v1/users/8").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Kiểm tra HTTP status là 200 OK

        // Kiểm tra dữ liệu trả về từ service
        UserResponse response = userService.findById(8L);
        assertNotNull(response);
        assertEquals(8L, response.getId());
        assertEquals("Le Khanh Duc", response.getUsername());
    }
}
