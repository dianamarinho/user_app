package com.api.user.services;

import com.api.user.domain.User;
import com.api.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    /**
     * Sets the properties
     *
     * @param userRepository the User repository to set
     */
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    /**
     * @see UserService#get(Integer)
     *
     * @param id the User Id
     * @return the User
     */
    public User get(Integer id){
        return userRepository.getById(id);
    }


    /**
     * @see UserService#save(User)
     *
     * @param user the new User
     * @return the User
     */
    public User save(User user){
        return userRepository.save(user);
    }


    /**
     * @see UserService#list()
     *
     * @return the list of User
     */
    public List<User> list(){
        return userRepository.findAll();
    }


    /**
     * @see UserService#addUser(Integer, User)
     *
     * @param id the User id
     * @param user the User
     * @return the User
     */
    public User addUser(Integer id, User user){
        if(!user.getId().equals(id)){
           return userRepository.save(user);
        }
        return user;
    }


    /**
     * @see UserService#removeUser(Integer)
     *
     * @param id the User id
     */
    public void removeUser(Integer id){
        User user = Optional.ofNullable(userRepository.getById(id))
                .orElseThrow();

        if(user.getId().equals(id)){
            userRepository.deleteById(user.getId());
        }
    }
}
