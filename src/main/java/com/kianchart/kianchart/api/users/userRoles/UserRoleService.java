package com.kianchart.kianchart.api.users.userRoles;

import com.kianchart.kianchart.core.enums.SortDirection;
import com.kianchart.kianchart.core.exception.NotFoundException;
import com.kianchart.kianchart.database.entity.users.UserRole;
import com.kianchart.kianchart.database.repository.users.UserRoleRepository;
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

    public List<UserRoleDTO.Response> getAllUserRole(SortDirection sort, int skip, int limit) {
        List<UserRole> userRoles = sort == SortDirection.asc ?
                userRoleRepository.getAllUserRoleASC() : userRoleRepository.getAllUserRoleDESC();
        return UserRoleDTO.Response.mapToDtoList(userRoles, skip, limit);
    }

    public Long countAllUserRole() {
        return userRoleRepository.count();
    }

    public UserRoleDTO.Response createUserRole(UserRoleDTO.CreateUserRoleRequest createRequest) {
        createRequest.validate(userRoleRepository);
        UserRole userRole = createRequest.mapToEntity(userRoleRepository);
        UserRole savedUserRole = userRoleRepository.save(userRole);
        return UserRoleDTO.Response.mapToDto(savedUserRole);
    }

    public void deleteUserRole(Long id) {
        if (!userRoleRepository.existsById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        userRoleRepository.deleteById(id);
    }
}
