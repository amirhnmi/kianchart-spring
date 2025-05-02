package com.kianchart.kianchart.validation;

import com.kianchart.kianchart.model.RolePermissionModel;
import com.kianchart.kianchart.repository.RolePermissionRepository;
import com.kianchart.kianchart.utils.exception.DuplicationException;
import com.kianchart.kianchart.utils.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


// این فقط یه تغیی برای اینکه بفهمم اطلاعات از مستر به استیبل چطور میره
@Component
public class RolePermissionValidator {
    Map<String, String> errors = new HashMap<>();

    private final RolePermissionRepository repository;

    public RolePermissionValidator(RolePermissionRepository repository) {
        this.repository = repository;
    }

    public void createValidate(RolePermissionModel.CreateRolePermissionRequest createData){
        if (!repository.existsByRoleId(createData.getRoleId())) {
            errors.put("roleId", "roleId not found with id " + createData.getRoleId());
        }
        for (Long permissionId : createData.getPermissionIds()) {
            if (!repository.existsByPermissionId(permissionId)) {
                errors.put("permissionId", "permissionId not found with id " + permissionId);
            }
//            if (repository.existsByPermissionIdRoleId(permissionId, this.roleId)) {
//                throw new DuplicationException(
//                        "roleId with id " + this.roleId + " and permissionId with id " + permissionId + " already exist"
//                );
//            }
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
