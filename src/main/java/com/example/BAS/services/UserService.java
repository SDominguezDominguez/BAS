package com.example.BAS.services;


import com.example.BAS.dtos.UserDto;
import com.example.BAS.exceptions.UsernameNotFoundException;
import com.example.BAS.models.Authority;
import com.example.BAS.models.User;
import com.example.BAS.repositories.UserRepository;
import com.example.BAS.utils.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserDto> getUsers() {

        List<UserDto> collection = new ArrayList<>();

        List<User> list = userRepository.findAll();

        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {

        UserDto dto;

        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()){

            dto = fromUser(user.get());

        } else {

            throw new UsernameNotFoundException(username);
        }

        return dto;
    }

    public boolean userExists(String username) {

        return userRepository.existsById(username);
    }

    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);

        userDto.setApiKey(randomString);

        User newUser = userRepository.save(toUser(userDto));

        return newUser.getUsername();
    }

    public void deleteUser(String username) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);

        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserDto newUser) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);

        User user = userRepository.findById(username).get();

        user.setPassword(newUser.getPassword());

        userRepository.save(user);
    }

    public Set<Authority> getAuthorities(String username) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);

        User user = userRepository.findById(username).get();

        UserDto userDto = fromUser(user);

        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);

        User user = userRepository.findById(username).get();

        user.addAuthority(new Authority(username, authority));

        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);

        User user = userRepository.findById(username).get();

        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();

        user.removeAuthority(authorityToRemove);

        userRepository.save(user);
    }

    public static UserDto fromUser(User user){

        var dto = new UserDto();

        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setApiKey(user.getApiKey());
        dto.setEmail(user.getEmail());
        dto.setAuthorities(user.getAuthorities());

        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setApiKey(userDto.getApiKey());
        user.setEmail(userDto.getEmail());

        return user;
    }

}
