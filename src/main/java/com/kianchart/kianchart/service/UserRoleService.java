package com.kianchart.kianchart.service;

import com.kianchart.kianchart.model.UserRoleModel;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.entity.UserRoleEntity;
import com.kianchart.kianchart.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRoleModel.Response> getAllUserRole(SortDirection sort, int skip, int limit) {
        List<UserRoleEntity> userRoleEntities = sort == SortDirection.asc ?
                userRoleRepository.getAllUserRoleASC() : userRoleRepository.getAllUserRoleDESC();
        return UserRoleModel.Response.mapToDtoList(userRoleEntities, skip, limit);
    }

    public Long countAllUserRole() {
        return userRoleRepository.count();
    }

    public UserRoleModel.Response createUserRole(UserRoleModel.CreateUserRoleRequest createRequest) {
        createRequest.validate(userRoleRepository);
        UserRoleEntity userRoleEntity = createRequest.mapToEntity(userRoleRepository);
        UserRoleEntity savedUserRoleEntity = userRoleRepository.save(userRoleEntity);
        return UserRoleModel.Response.mapToDto(savedUserRoleEntity);
    }

    public void deleteUserRole(Long id) {
        if (!userRoleRepository.existsById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        userRoleRepository.deleteById(id);
    }
}
