package com.api.user.converters;

import com.api.user.domain.User;
import com.api.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A {@link Converter} implementation, responsible for {@link User} to {@link UserDto} type conversion
 */
@Component
public class UserToUserDto {

    /**
     * Converts User into User DTO
     *
     * @param user the User
     * @return the User DTO
     */
    public UserDto convertUserToUserDto(User user){
        UserDto userDto = new UserDto();

        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setAddressName(user.getAddressName());
        userDto.setZipCode(user.getZipCode());
        userDto.setCity(user.getCity());

        return userDto;
    }
}
