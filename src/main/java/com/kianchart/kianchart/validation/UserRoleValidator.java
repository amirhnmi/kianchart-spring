package com.kianchart.kianchart.validation;

import com.kianchart.kianchart.model.UserRoleModel;
import com.kianchart.kianchart.repository.UserRoleRepository;
import com.kianchart.kianchart.utils.exception.DuplicationException;
import com.kianchart.kianchart.utils.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRoleValidator{
    Map<String, String> errors = new HashMap<>();

    private final UserRoleRepository repository;

    public UserRoleValidator(UserRoleRepository repository) {
        this.repository = repository;
    }

    public void createValidate(UserRoleModel.CreateUserRoleRequest createData) {
        if (!repository.existsByRoleId(createData.getRoleId())) {
            errors.put("roleId", "roleId not found with id " + createData.getRoleId());
        }
        if (!repository.existsByUserId(createData.getUserId())) {
            errors.put("userId", "userId not found with id " + createData.getUserId());
        }
        if (repository.existsByUserIdRoleId(createData.getUserId(), createData.getRoleId())) {
            throw new DuplicationException(
                    "roleId with id " + createData.getRoleId() + " and userId with id " + createData.getUserId() + " already exist"
            );
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
