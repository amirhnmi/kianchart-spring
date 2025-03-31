package com.kianchart.kianchart.api.users.users;

import com.kianchart.kianchart.core.enums.SortDirection;
import com.kianchart.kianchart.core.exception.NotFoundException;
import com.kianchart.kianchart.database.entity.users.User;
import com.kianchart.kianchart.database.repository.users.UserRepository;
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

    public List<UserDTO.Response> getAllUser(SortDirection sort, int skip, int limit) {
        List<User> users = sort == SortDirection.asc ?
                userRepository.findAllUsersASC() : userRepository.findAllUsersDESC();
        return UserDTO.Response.mapToDtoList(users, skip, limit);
    }

    public Long countAllUser() {
        return userRepository.countAllActiveUser();
    }

    public UserDTO.Response getUserById(Long id) {
        User user = userRepository.findOneUser(id);
        if (user == null)
            throw new NotFoundException("data not found with id " + id);
        return UserDTO.Response.mapToDto(user);
    }

    public UserDTO.Response createUser(UserDTO.CreateRequest userDTO) {
        userDTO.validate(userRepository);
        User user = userDTO.mapToEntity();
        User saveedUser = userRepository.save(user);
        return UserDTO.Response.mapToDto(saveedUser);
    }

    public UserDTO.Response updateUser(Long id, UserDTO.UpdateRequest updateUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Data not found with id " + id));
        updateUser.validate(userRepository);
        updateUser.mapToEntityForUpdate(existingUser);
        User updatedUser = userRepository.save(existingUser);
        return UserDTO.Response.mapToDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)){
            throw new NotFoundException("data not found with id " + id);
        }
        userRepository.deleteUser(id);
    }
}
