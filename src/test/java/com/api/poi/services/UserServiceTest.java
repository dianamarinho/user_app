package com.api.poi.services;

import com.api.user.domain.User;
import com.api.user.repositories.UserRepository;
import com.api.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listUser_showAllUserSaved(){
        User user = new User();

        user.setId(1);
        user.setName("Diana");
        user.setCity("Porto");
        user.setAddressName("Rua");
        user.setDescription("lalalalal");

        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userRepository.findAll()).thenReturn(userList);

    }

    @Test
    void user_matchesUserExpected(){
        User user = new User();

        user.setId(1);
        user.setName("Diana");
        user.setCity("Porto");
        user.setAddressName("Rua");
        user.setDescription("lalalalal");

        when(userRepository.getById(any())).thenReturn(user);

        User user1 = userService.get(user.getId());
        assertEquals(user.hashCode(), user1.hashCode());
    }

    @Test
    void given_user_when_wantToAddNewUser_then_persistIt(){
        User user = new User();

        user.setId(1);
        user.setName("Diana");
        user.setCity("Porto");
        user.setAddressName("Rua");
        user.setDescription("lalalalal");

        when(userRepository.getById(user.getId())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);


        User user1 = userService.addUser(user.getId(), user);

        assertEquals(user.getName(), user1.getName());
        assertEquals(user.getCity(), user1.getCity());
        assertEquals(user.getAddressName(), user1.getAddressName());
        assertEquals(user.getDescription(), user1.getDescription());


    }

    @Test
    void given_user_when_wantToRemoveUser_then_removeUser(){
        User user = new User();

        user.setId(1);
        user.setName("Diana");
        user.setCity("Porto");
        user.setAddressName("Rua");
        user.setDescription("lalalalal");

        when(userRepository.getById(user.getId())).thenReturn(user);

        userService.removeUser(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());

    }


}