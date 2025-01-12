package vn.khanhduc.backendservice.mapper;

import vn.khanhduc.backendservice.dto.request.AddressRequest;
import vn.khanhduc.backendservice.dto.response.UserResponse;
import vn.khanhduc.backendservice.model.UserEntity;

public class UserMapper {

    private UserMapper(){
    }

    public static UserResponse toUserResponse(final UserEntity user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthDate())
                .gender(user.getGender())
                .addresses(user.getAddresses().stream().map(adr -> AddressRequest.builder()
                        .apartmentNumber(adr.getApartmentNumber())
                        .floor(adr.getFloor())
                        .building(adr.getBuilding())
                        .streetNumber(adr.getStreetNumber())
                        .street(adr.getStreet())
                        .city(adr.getCity())
                        .country(adr.getCountry())
                        .addressType(adr.getAddressType())
                        .build()).toList())
                .build();
    }
    
}
