package com.kianchart.kianchart.service;

import com.kianchart.kianchart.model.UserModel;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.mapper.UserMapper;
import com.kianchart.kianchart.restriction.UserRestriction;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.entity.UserEntity;
import com.kianchart.kianchart.repository.UserRepository;
import com.kianchart.kianchart.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Autowired
    public UserService(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Transactional(readOnly = true)
    public List<UserModel.Response> getAllUser(UserModel.Search search,SortDirection sort, int skip, int limit) {
        UserRestriction restriction = new UserRestriction(search);
        Sort sortObj = sort == SortDirection.asc ? Sort.by("id").ascending() : Sort.by("id").descending();
        Pageable pageable = PageRequest.of(skip, limit, sortObj);
        Page<UserEntity> users = userRepository.findAllUsers(restriction, pageable);
        return UserMapper.INSTANCE.toUserModelList(users.getContent());
    }

    @Transactional(readOnly = true)
    public Long countAllUser() {
        return userRepository.countAllActiveUser();
    }

    @Transactional(readOnly = true)
    public UserModel.Response getUserById(Long id) {
        UserEntity userEntity = userRepository.findOneUser(id);
        if (userEntity == null)
            throw new NotFoundException("data not found with id " + id);
        return UserMapper.INSTANCE.toUserModel(userEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserModel.Response createUser(UserModel.Create createUser) {
        userValidator.createValidate(createUser);
        UserEntity userEntity = UserMapper.INSTANCE.toUserEntity(createUser);
        UserEntity saveedUserEntity = userRepository.save(userEntity);
        return UserMapper.INSTANCE.toUserModel(saveedUserEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserModel.Response updateUser(Long id, UserModel.Update updateUser) {
        UserEntity existingUserEntity = userRepository.findById(id).orElseThrow(()-> new NotFoundException("Data not found with id " + id));
        userValidator.updateValidate(updateUser);
        UserMapper.INSTANCE.toUserEntityForUpdate(updateUser,existingUserEntity);
        UserEntity updatedUserEntity = userRepository.save(existingUserEntity);
        return UserMapper.INSTANCE.toUserModel(updatedUserEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)){
            throw new NotFoundException("data not found with id " + id);
        }
        userRepository.deleteUser(id);
    }
}
