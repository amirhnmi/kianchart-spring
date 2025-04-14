package com.kianchart.kianchart.service;

import com.kianchart.kianchart.model.UserModel;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.mapper.UserMapper;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.entity.UserEntity;
import com.kianchart.kianchart.repository.UserRepository;
import com.kianchart.kianchart.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    public List<UserModel.Response> getAllUser(SortDirection sort, int skip, int limit) {
        List<UserEntity> userEntities = sort == SortDirection.asc ?
                userRepository.findAllUsersASC() : userRepository.findAllUsersDESC();
        return userMapper.toUserModelList(userEntities);
    }

    public Long countAllUser() {
        return userRepository.countAllActiveUser();
    }

    public UserModel.Response getUserById(Long id) {
        UserEntity userEntity = userRepository.findOneUser(id);
        if (userEntity == null)
            throw new NotFoundException("data not found with id " + id);
        return userMapper.toUserModel(userEntity);
    }

    public UserModel.Response createUser(UserModel.Create createUser) {
        userValidator.createValidate(createUser);
        UserEntity userEntity = userMapper.toUserEntity(createUser);
        UserEntity saveedUserEntity = userRepository.save(userEntity);
        return userMapper.toUserModel(saveedUserEntity);
    }

    public UserModel.Response updateUser(Long id, UserModel.Update updateUser) {
        UserEntity existingUserEntity = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Data not found with id " + id));
        userValidator.updateValidate(updateUser);
        userMapper.toUserEntityForUpdate(updateUser);
        UserEntity updatedUserEntity = userRepository.save(existingUserEntity);
        return userMapper.toUserModel(updatedUserEntity);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)){
            throw new NotFoundException("data not found with id " + id);
        }
        userRepository.deleteUser(id);
    }
}
