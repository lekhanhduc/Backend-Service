package vn.khanhduc.backendservice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.khanhduc.backendservice.common.Gender;
import vn.khanhduc.backendservice.common.UserStatus;
import vn.khanhduc.backendservice.common.UserType;
import vn.khanhduc.backendservice.dto.request.AddressRequest;
import vn.khanhduc.backendservice.dto.request.UserCreationRequest;
import vn.khanhduc.backendservice.dto.response.UserCreationResponse;
import vn.khanhduc.backendservice.dto.response.UserResponse;
import vn.khanhduc.backendservice.exception.InvalidDataException;
import vn.khanhduc.backendservice.exception.ResourceNotFoundException;
import vn.khanhduc.backendservice.model.UserEntity;
import vn.khanhduc.backendservice.repository.AddressRepository;
import vn.khanhduc.backendservice.repository.UserRepository;
import vn.khanhduc.backendservice.service.impl.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static UserEntity khanhDuc;
    private static UserEntity johnDoe;

    private UserService userService;

    private @Mock UserRepository userRepository;
    private @Mock AddressRepository addressRepository;
    private @Mock PasswordEncoder passwordEncoder;
    private @Mock EmailService emailService;


    @BeforeAll
    static void beforeAll() {
        khanhDuc = new UserEntity();
        khanhDuc.setId(1L);
        khanhDuc.setFirstName("Le Khanh");
        khanhDuc.setLastName("Duc");
        khanhDuc.setUsername("Le Khanh Duc");
        khanhDuc.setGender(Gender.MALE);
        khanhDuc.setEmail("duc@gmail.com");
        khanhDuc.setPassword("123456");
        khanhDuc.setPhone("0123456789");
        khanhDuc.setStatus(UserStatus.ACTIVE);
        khanhDuc.setType(UserType.USER);

        johnDoe = new UserEntity();
        johnDoe.setId(2L);
        johnDoe.setFirstName("Anh");
        johnDoe.setLastName("Hong");
        johnDoe.setUsername("Anh Hong");
        johnDoe.setGender(Gender.FEMALE);
        johnDoe.setEmail("hong@gmail.com");
        johnDoe.setPassword("123456");
        johnDoe.setPhone("0123456789");
        johnDoe.setStatus(UserStatus.INACTIVE);
        johnDoe.setType(UserType.USER);
    }

    @BeforeEach
    void setUp() {
        // Khởi tạo bước triển khai UserService
        userService = new UserServiceImpl(userRepository, addressRepository, passwordEncoder, emailService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(khanhDuc));
        UserResponse response = userService.findById(1L);

        assertNotNull(response);
        assertEquals(khanhDuc.getId(), response.getId());
        assertEquals(khanhDuc.getFirstName(), response.getFirstName());
    }

    @Test
    void testGetUserById_Failure() {

        ResourceNotFoundException notFoundException = assertThrows(ResourceNotFoundException.class,
                () -> userService.findById(100L));

        assertEquals("User not found", notFoundException.getMessage());
    }


    @Test
    void testFindUserByEmail_Success() {
        when(userRepository.findByEmail("hong@gmail.com")).thenReturn(johnDoe);

        UserResponse response = userService.findByEmail(johnDoe.getEmail());

        assertNotNull(response);
        assertEquals(johnDoe.getId(), response.getId());
    }

    @Test
    void testSaveUser_Success() {

        when(userRepository.save(any(UserEntity.class))).thenReturn(khanhDuc);
        UserCreationRequest request = new UserCreationRequest();
        request.setFirstName("Le Khanh");
        request.setLastName("Duc");
        request.setUsername("Le Khanh Duc");
        request.setEmail("duc@gmail.com");
        request.setPassword("123456");
        request.setPhone("0123456789");
        request.setGender(Gender.MALE);
        request.setType(UserType.USER);

        AddressRequest address = new AddressRequest();
        address.setApartmentNumber("ApartmentNumber");
        address.setFloor("Floor");
        address.setBuilding("Building");
        address.setStreet("Street");
        address.setCity("Hanoi");
        address.setCountry("Vietnam");
        address.setAddressType(1);
        request.setAddresses(List.of(address));

        UserCreationResponse response = userService.save(request);

        assertNotNull(response);
        assertEquals(request.getFirstName(), response.getFirstName());
        assertEquals(request.getLastName(), response.getLastName());
        assertEquals(request.getGender(), response.getGender());
        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getPhone(), response.getPhone());
        assertEquals(request.getGender(), response.getGender());
        assertEquals(UserType.USER, response.getType());

    }

    @Test
    void testSaveUser_Failure() {

        when(userRepository.findByEmail("duc@gmail.com")).thenReturn(new UserEntity());

        UserCreationRequest request = new UserCreationRequest();
        request.setFirstName("Le Khanh");
        request.setLastName("Duc");
        request.setUsername("Le Khanh Duc");
        request.setEmail("duc@gmail.com");
        request.setPassword("123456");
        request.setPhone("0123456789");
        request.setGender(Gender.MALE);
        request.setType(UserType.USER);

        AddressRequest address = new AddressRequest();
        address.setApartmentNumber("ApartmentNumber");
        address.setFloor("Floor");
        address.setBuilding("Building");
        address.setStreet("Street");
        address.setCity("Hanoi");
        address.setCountry("Vietnam");
        address.setAddressType(1);
        request.setAddresses(List.of(address));

        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> userService.save(request));

        assertEquals("Email already exists", invalidDataException.getMessage());
    }

    @Test
    void update() {
    }

    @Test
    void userDetail() {
    }
}