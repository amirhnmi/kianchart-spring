package com.kianchart.kianchart.api.users;

import com.kianchart.kianchart.core.exception.NotFoundException;
import com.kianchart.kianchart.database.entity.users.User;
import com.kianchart.kianchart.database.repository.users.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO.Response> getAllUser() {
        List<User> users = userRepository.findAllUsers();
        return UserDTO.Response.toDtoList(users);
    }

    public UserDTO.Response getUserById(Long id) {
        User user = userRepository.findOneUser(id);
        if (user == null)
            throw new NotFoundException("data not found with id " + id);
        return UserDTO.Response.toDto(user);
    }

    public UserDTO.Response createUser(@Valid UserDTO.CreateRequest userDTO) {
        userDTO.validate(userRepository);
        User user = userDTO.toEntity();
        User saveedUser = userRepository.save(user);
        return UserDTO.Response.toDto(saveedUser);
    }

    public UserDTO.Response updateUser(Long id, UserDTO.UpdateRequest updateUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Data not found with id " + id));
        updateUser.validate(userRepository);
        updateUser.updateEntity(existingUser);
        User updatedUser = userRepository.save(existingUser);
        return UserDTO.Response.toDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)){
            throw new NotFoundException("data not found with id " + id);
        }
        userRepository.deleteUser(id);
    }
}
