package vn.khanhduc.backendservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.khanhduc.backendservice.common.UserStatus;
import vn.khanhduc.backendservice.dto.request.UserCreationRequest;
import vn.khanhduc.backendservice.dto.request.UserUpdateRequest;
import vn.khanhduc.backendservice.dto.response.UserCreationResponse;
import vn.khanhduc.backendservice.dto.response.UserPageResponse;
import vn.khanhduc.backendservice.dto.response.UserResponse;
import vn.khanhduc.backendservice.exception.AppException;
import vn.khanhduc.backendservice.exception.ErrorCode;
import vn.khanhduc.backendservice.exception.InvalidDataException;
import vn.khanhduc.backendservice.exception.ResourceNotFoundException;
import vn.khanhduc.backendservice.mapper.UserMapper;
import vn.khanhduc.backendservice.model.AddressEntity;
import vn.khanhduc.backendservice.model.UserEntity;
import vn.khanhduc.backendservice.repository.AddressRepository;
import vn.khanhduc.backendservice.repository.UserRepository;
import vn.khanhduc.backendservice.service.EmailService;
import vn.khanhduc.backendservice.service.UserService;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j(topic = "USER_SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    @PreAuthorize("isAuthenticated() && hasAuthority('USER')")
    public UserResponse findById(Long id) {
        log.info("Find user by id: {}", id);

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserResponse.builder()
                .id(id)
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .gender(userEntity.getGender())
                .birthday(userEntity.getBirthDate())
                .username(userEntity.getUsername())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .build();
    }

    @Override
    public UserResponse findByEmail(String email) {
        log.info("Find user by email: {}", email);

        UserEntity userEntity = userRepository.findByEmail(email);

        return UserResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .gender(userEntity.getGender())
                .birthday(userEntity.getBirthDate())
                .username(userEntity.getUsername())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserCreationResponse save(UserCreationRequest req) {
        log.info("Saving user: {}", req);

        UserEntity userByEmail = userRepository.findByEmail(req.getEmail());
        if (userByEmail != null) {
            throw new InvalidDataException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(req.getFirstName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthDate(req.getBirthday());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUsername(req.getUsername());
        user.setType(req.getType());
        user.setStatus(UserStatus.ACTIVE);

        UserEntity result = userRepository.save(user);
        log.info("Saved user: {}", user);

        if (result.getId() != null) {
            log.info("user id: {}", result.getId());
            List<AddressEntity> addresses = new ArrayList<>();
            req.getAddresses().forEach(address -> {
                AddressEntity addressEntity = new AddressEntity();
                addressEntity.setApartmentNumber(address.getApartmentNumber());
                addressEntity.setFloor(address.getFloor());
                addressEntity.setBuilding(address.getBuilding());
                addressEntity.setStreetNumber(address.getStreetNumber());
                addressEntity.setStreet(address.getStreet());
                addressEntity.setCity(address.getCity());
                addressEntity.setCountry(address.getCountry());
                addressEntity.setAddressType(address.getAddressType());
                addressEntity.setUser(result);
                addresses.add(addressEntity);
            });
            result.setAddresses(addresses);
            addressRepository.saveAll(addresses);
            log.info("Saved addresses: {}", addresses);
        }

        emailService.emailVerification(req.getEmail(), req.getUsername());

        return UserCreationResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .birthday(user.getBirthDate())
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .type(user.getType())
                .build();
    }

    @Override
    public void update(UserUpdateRequest req) {

    }

    @Override
//    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse userDetail(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return UserMapper.toUserResponse(user);
    }

    private static UserPageResponse getUserPageResponse(int page, int size, Page<UserEntity> userEntities) {
        log.info("Convert User Entity Page");

        List<UserResponse> userList = userEntities.stream().map(entity -> UserResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .birthday(entity.getBirthDate())
                .username(entity.getUsername())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .build()
        ).toList();

        UserPageResponse response = new UserPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElements(userEntities.getTotalElements());
        response.setTotalPages(userEntities.getTotalPages());
        response.setUsers(userList);

        return response;
    }

}
