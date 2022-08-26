package com.api.user.api;

import com.api.user.converters.UserDtoToUser;
import com.api.user.converters.UserToUserDto;
import com.api.user.domain.User;
import com.api.user.dto.UserDto;
import com.api.user.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller responsible for {@link User} related CRUD operations
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    private UserDtoToUser userDtoToUser;
    private UserToUserDto userToUserDto;

    /**
     * Sets the params
     * @param userService the Service to set
     * @param userToUserDto the converter to set
     * @param userDtoToUser the converter to set
     */
    public UserController(UserService userService, UserToUserDto userToUserDto, UserDtoToUser userDtoToUser){
        this.userService = userService;
        this.userToUserDto = userToUserDto;
        this.userDtoToUser = userDtoToUser;
    }


    /**
     * Retrieves a representation of the list of User
     *
     * @return the response entity
     */
    @GetMapping("/consult")
    public ResponseEntity<List<UserDto>> accessingAllUsers(){
        List<UserDto> userDtos = userService.list().stream()
                .map(user -> userToUserDto.convertUserToUserDto(user)).collect(Collectors.toList());

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    /**
     * Adds a User
     *
     * @param userDto the User DTO
     * @param bindingResult the binding result object
     * @param uriComponentsBuilder the uri component builder
     * @return the response entity
     */
    @PostMapping("/adding")
    public ResponseEntity<?> addingNewPoi(@RequestBody UserDto userDto, BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder){
        if(bindingResult.hasErrors() || userDto.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.save(userDtoToUser.convertUserDtoToUser(userDto));

        UriComponents uriComponents = uriComponentsBuilder.path("api/adding/" + user.getId()).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    /**
     * Deletes a User
     *
     * @param id the User id
     * @return the response entity
     */
    @DeleteMapping("/deleting/{id}")
    public ResponseEntity<UserDto> deletingInformation(@PathVariable Integer id){

        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Edits a User
     *
     * @param userDto the User DTO
     * @param bindingResult the binding result
     * @param id the User ID
     * @return the response entity
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updatingInformation(@RequestBody UserDto userDto, BindingResult bindingResult, @PathVariable Integer id){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userDto.getId() != null && !userDto.getId().equals(id)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userService.get(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDto.setId(id);

        userService.save(userDtoToUser.convertUserDtoToUser(userDto));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
