package com.kianchart.kianchart.controller;

import com.kianchart.kianchart.model.RoleModel;
import com.kianchart.kianchart.service.RoleService;
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
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/role/list", method = RequestMethod.GET)
    public CustomResponseEntity<List<RoleModel.Response>> getAllRole(
            @RequestParam(defaultValue = "asc") SortDirection sort,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean total
            ) {
        Long totalValue = total ? roleService.countAllRole() : null;
        List<RoleModel.Response> roles = roleService.getAllRole(sort,skip,limit);
        return CustomResponseEntity.showList(roles,totalValue);
    }

    @RequestMapping(value = "/role/{id}/show", method = RequestMethod.GET)
    public CustomResponseEntity<RoleModel.Response> getOneRole(@PathVariable Long id) {
        RoleModel.Response role = roleService.getOneRole(id);
        return CustomResponseEntity.showDetail(role);
    }

    @RequestMapping(value = "/role/create", method = RequestMethod.POST)
    public CustomResponseEntity<RoleModel.Response> createRole(
            @Valid @RequestBody RoleModel.CreateRoleRequest createRequest)
    {
        RoleModel.Response role = roleService.createRole(createRequest);
        return CustomResponseEntity.showDetail(role);
    }

    @RequestMapping(value = "/role/{id}/update", method = RequestMethod.PUT)
    public CustomResponseEntity<RoleModel.Response> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleModel.UpdateRoleRequest updateRequest)
    {
        RoleModel.Response role = roleService.updateRole(id, updateRequest);
        return CustomResponseEntity.showDetail(role);
    }

    @RequestMapping(value = "/role/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).body("data successfully deleted");
    }
}
