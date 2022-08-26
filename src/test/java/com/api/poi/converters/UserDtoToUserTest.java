package com.api.poi.converters;

import com.api.user.converters.UserDtoToUser;
import com.api.user.domain.User;
import com.api.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

class UserDtoToUserTest {

    @InjectMocks
    private UserDtoToUser userDtoToUser;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_userDto_when_wantConvert_then_receiveUser(){

        Integer id = 1;
        String name = "Diana";
        String addressName = "Rua";
        String city = "Porto";

        UserDto userDto = new UserDto();

        userDto.setId(id);
        userDto.setName(name);
        userDto.setCity(city);
        userDto.setAddressName(addressName);

        User user = userDtoToUser.convertUserDtoToUser(userDto);

        assertTrue(user.getId() == id);
        assertTrue(user.getName().equals(name));
        assertTrue(user.getCity().equals(city));
        assertTrue(user.getAddressName().equals(addressName));

    }

}