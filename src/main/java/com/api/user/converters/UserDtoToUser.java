package com.api.user.converters;

import com.api.user.domain.User;
import com.api.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A {@link Converter} implementation, responsible for {@link UserDto} to {@link User}  type conversion
 */
@Component
public class UserDtoToUser {

    /**
     * Converts the User DTO into a User model object
     *
     * @param userDto the User DTO
     * @return the User
     */
    public User convertUserDtoToUser(UserDto userDto){

        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAddressName(userDto.getAddressName());
        user.setZipCode(userDto.getZipCode());
        user.setCity(userDto.getCity());

        return user;
    }
}
