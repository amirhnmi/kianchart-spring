package com.kianchart.kianchart.api.permission.roles;

import com.kianchart.kianchart.core.enums.SortDirection;
import com.kianchart.kianchart.core.response.CustomResponseEntity;
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
    public CustomResponseEntity<List<RoleDTO.Response>> getAllRole(
            @RequestParam(defaultValue = "asc") SortDirection sort,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean total
            ) {
        Long totalValue = total ? roleService.countAllRole() : null;
        List<RoleDTO.Response> roles = roleService.getAllRole(sort,skip,limit);
        return CustomResponseEntity.showList(roles,totalValue);
    }

    @RequestMapping(value = "/role/{id}/show", method = RequestMethod.GET)
    public CustomResponseEntity<RoleDTO.Response> getOneRole(@PathVariable Long id) {
        RoleDTO.Response role = roleService.getOneRole(id);
        return CustomResponseEntity.showDetail(role);
    }

    @RequestMapping(value = "/role/create", method = RequestMethod.POST)
    public CustomResponseEntity<RoleDTO.Response> createRole(
            @Valid @RequestBody RoleDTO.CreateRoleRequest createRequest)
    {
        RoleDTO.Response role = roleService.createRole(createRequest);
        return CustomResponseEntity.showDetail(role);
    }

    @RequestMapping(value = "/role/{id}/update", method = RequestMethod.PUT)
    public CustomResponseEntity<RoleDTO.Response> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleDTO.UpdateRoleRequest updateRequest)
    {
        RoleDTO.Response role = roleService.updateRole(id, updateRequest);
        return CustomResponseEntity.showDetail(role);
    }

    @RequestMapping(value = "/role/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).body("data successfully deleted");
    }
}
