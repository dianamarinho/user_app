package com.api.poi.converters;

import com.api.user.converters.UserToUserDto;
import com.api.user.domain.User;
import com.api.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserToUserDtoTest {

    @InjectMocks
    private UserToUserDto userToUserDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_user_when_wantConvert_then_receiveUserDto(){

        Integer id = 1;
        String name = "Diana";
        String addressName = "Rua";
        String city = "Porto";

        User user = new User();

        user.setId(id);
        user.setName(name);
        user.setCity(city);
        user.setAddressName(addressName);

        UserDto userDto = userToUserDto.convertUserToUserDto(user);

        assertTrue(userDto.getId() == id);
        assertTrue(userDto.getName().equals(name));
        assertTrue(userDto.getCity().equals(city));
        assertTrue(userDto.getAddressName().equals(addressName));

    }

}