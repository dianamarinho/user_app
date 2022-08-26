package com.api.poi.api;

import com.api.user.api.UserController;
import com.api.user.converters.UserDtoToUser;
import com.api.user.converters.UserToUserDto;
import com.api.user.domain.User;
import com.api.user.dto.UserDto;
import com.api.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {


    @Mock
    private UserService userService;

    @Mock
    private UserDtoToUser userDtoToUser;

    @Mock
    private UserToUserDto userToUserDto;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

    }

    @Test
    void getMethod_want_accessAllTheUsers() throws Exception {

        Integer id = 1;
        String name = "Diana";
        String addressName = "Rua";
        String city = "Porto";

        User user = new User();

        user.setId(id);
        user.setName(name);
        user.setCity(city);
        user.setAddressName(addressName);

        UserDto userDto = new UserDto();

        userDto.setId(id);
        userDto.setName(name);
        userDto.setCity(city);
        userDto.setAddressName(addressName);

        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userService.list()).thenReturn(userList);
        when(userToUserDto.convertUserToUserDto(user)).thenReturn(userDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/consult/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(id))
                .andExpect(jsonPath("$.[0].name").value(name))
                .andExpect(jsonPath("$.[0].city").value(city))
                .andExpect(jsonPath("$.[0].addressName").value(addressName));


        verify(userToUserDto, times(1)).convertUserToUserDto(user);
    }

    @Test
    @Disabled
    void addPoi_when_wantToAddOneUser() throws Exception {
        Integer id = 1;
        String name = "Diana";
        String addressName = "Rua";
        String city = "Porto";

        User user = new User();

        user.setId(id);
        user.setName(name);
        user.setCity(city);
        user.setAddressName(addressName);

        UserDto userDto = new UserDto();

        userDto.setId(id);
        userDto.setName(name);
        userDto.setCity(city);
        userDto.setAddressName(addressName);


        when(userDtoToUser.convertUserDtoToUser(ArgumentMatchers.any(UserDto.class))).thenReturn(user);
        when(userService.addUser(id, user)).thenReturn(user);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/adding/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location´", containsString("localhost:53658/api/adding/"+ user.getId())));

        ArgumentCaptor<UserDto> boundUserDto = ArgumentCaptor.forClass(UserDto.class);

        verify(userDtoToUser, times(1)).convertUserDtoToUser(boundUserDto.capture());
        verify(userService, times(1)).addUser(id, user);

        assertEquals(null, boundUserDto.getValue().getId());
        assertEquals(name, boundUserDto.getValue().getName());
        assertEquals(city, boundUserDto.getValue().getCity());
        assertEquals(addressName, boundUserDto.getValue().getAddressName());

    }

    @Test
    void tryAddNewUser_then_badRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/adding/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void tryDeleteAUser_then_deleteIt() throws Exception {

        Integer id = 1;
        String name = "Diana";
        String addressName = "Rua";
        String city = "Porto";

        User user = new User();

        user.setId(id);
        user.setName(name);
        user.setCity(city);
        user.setAddressName(addressName);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/deleting/{id}", user.getId()))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).removeUser(user.getId());
    }

    @Test
    void editUser_then_hadUserEdited() throws Exception {
        Integer id = 1;
        String name = "Diana";
        String addressName = "Rua";
        String city = "Porto";

        User user = new User();

        user.setId(id);
        user.setName(name);
        user.setCity(city);
        user.setAddressName(addressName);

        UserDto userDto = new UserDto();

        userDto.setId(id);
        userDto.setName(name);
        userDto.setCity(city);
        userDto.setAddressName(addressName);

        when(userDtoToUser.convertUserDtoToUser(ArgumentMatchers.any(UserDto.class))).thenReturn(user);
        when(userService.save(user)).thenReturn(user);
        when(userService.get(id)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/update/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isOk());

        ArgumentCaptor<UserDto> boundUserDto = ArgumentCaptor.forClass(UserDto.class);

        verify(userDtoToUser, times(1)).convertUserDtoToUser(boundUserDto.capture());
        verify(userService, times(1)).save(user);

        assertEquals(id, boundUserDto.getValue().getId());
        assertEquals(name, boundUserDto.getValue().getName());
        assertEquals(city, boundUserDto.getValue().getCity());
        assertEquals(addressName, boundUserDto.getValue().getAddressName());
    }

    @Test
    void tryEditUser_then_notFound() throws Exception {

        Integer invalidId = 2;
        Integer id = 1;
        String name = "Diana";
        String addressName = "Rua";
        String city = "Porto";

        UserDto userDto = new UserDto();

        userDto.setId(id);
        userDto.setName(name);
        userDto.setCity(city);
        userDto.setAddressName(addressName);


        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/updating/{id}", invalidId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isNotFound());
    }
}