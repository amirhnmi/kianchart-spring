package com.kianchart.kianchart.controller;

import com.kianchart.kianchart.model.UserRoleModel;
import com.kianchart.kianchart.service.UserRoleService;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.CustomResponseEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserRoleController {
    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping(value = "/user-role/list")
    public CustomResponseEntity<List<UserRoleModel.Response>> getAllUserRole(
            @RequestParam(defaultValue = "asc") SortDirection sort,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean total
    ) {
        List<UserRoleModel.Response> userRoles = userRoleService.getAllUserRole(sort, skip, limit);
        Long totalCount = total ? userRoleService.countAllUserRole() : null;
        return CustomResponseEntity.showList(userRoles, totalCount);
    }

    @PostMapping(value = "/user-role/create")
    public CustomResponseEntity<UserRoleModel.Response> createUserRole(
            @Valid @RequestBody UserRoleModel.CreateUserRoleRequest createRequest
    ) {
        UserRoleModel.Response userRole = userRoleService.createUserRole(createRequest);
        return CustomResponseEntity.showDetail(userRole);
    }

    @PostMapping(value = "/user-role/{id}/delete")
    public ResponseEntity<String> deleteUserRole(@PathVariable Long id) {
        userRoleService.deleteUserRole(id);
        return ResponseEntity.status(HttpStatus.OK).body("data successfully deleted");
    }
}
