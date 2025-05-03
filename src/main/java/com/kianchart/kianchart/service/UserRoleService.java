package com.kianchart.kianchart.service;

import com.kianchart.kianchart.mapper.UserRoleMapper;
import com.kianchart.kianchart.model.UserRoleModel;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.entity.UserRoleEntity;
import com.kianchart.kianchart.repository.UserRoleRepository;
import com.kianchart.kianchart.validation.UserRoleValidator;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleService {
    private final UserRoleRepository repository;
    private final UserRoleValidator validator;

    @Autowired
    public UserRoleService(UserRoleRepository repository, UserRoleValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Transactional(readOnly = true)
    public List<UserRoleModel.Response> getAllUserRole(SortDirection sort, int skip, int limit) {
        List<UserRoleEntity> userRoleEntities = sort == SortDirection.asc ?
                repository.getAllUserRoleASC() : repository.getAllUserRoleDESC();
        return UserRoleMapper.INSTANCE.entitiesToModel(userRoleEntities);
    }

    @Transactional(readOnly = true)
    public Long countAllUserRole() {
        return repository.count();
    }

    @Transactional(rollbackFor = Exception.class)
    public UserRoleModel.Response createUserRole(UserRoleModel.CreateUserRoleRequest createRequest) {
        validator.createValidate(createRequest);
        UserRoleEntity userRoleEntity = UserRoleMapper.INSTANCE.modelToEntity(createRequest);
        UserRoleEntity savedUserRoleEntity = repository.save(userRoleEntity);
        return UserRoleMapper.INSTANCE.entityToModel(savedUserRoleEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRole(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        repository.deleteById(id);
    }
}
