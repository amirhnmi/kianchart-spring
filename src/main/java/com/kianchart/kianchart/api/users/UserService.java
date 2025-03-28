package com.kianchart.kianchart.api.users;

import com.kianchart.kianchart.database.entity.User;
import com.kianchart.kianchart.database.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserDTO.Response createUser(@Valid UserDTO.CreateRequest userDTO) {
        userDTO.validate(userRepository);
        User user = userDTO.toEntity();
        User saveedUser = userRepository.save(user);
        return UserDTO.Response.toDto(saveedUser);
    }

    public User updateUser(Long id, User updateUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            existingUser.setUsername(updateUser.getUsername());
            existingUser.setFullname(updateUser.getFullname());
            existingUser.setEmail(updateUser.getEmail());
            existingUser.setDateOfBirth(updateUser.getDateOfBirth());
            existingUser.setGender(updateUser.getGender());
            existingUser.setPassword(updateUser.getPassword());
            existingUser.setIsActive(updateUser.getIsActive());

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
