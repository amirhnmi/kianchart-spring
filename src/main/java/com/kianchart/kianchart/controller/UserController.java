package com.kianchart.kianchart.controller;

import com.kianchart.kianchart.model.UserModel;
import com.kianchart.kianchart.service.UserService;
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
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/list")
    public CustomResponseEntity<List<UserModel.Response>> getAllUsers(
            @RequestParam(defaultValue = "asc") SortDirection sort,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean total,
            @RequestBody @Valid UserModel.Search search
    ) {
        List<UserModel.Response> users = userService.getAllUser(search,sort, skip, limit);
        Long totalCount = total ? userService.countAllUser() : null;
        return CustomResponseEntity.showList(users, totalCount);
    }

    @PostMapping(value = "/user/{id}/show")
    public CustomResponseEntity<UserModel.Response> getOneUser(@PathVariable Long id) {
        UserModel.Response user = userService.getUserById(id);
        return CustomResponseEntity.showDetail(user);
    }

    @PostMapping(value = "/user/create")
    public CustomResponseEntity<UserModel.Response> createUser(
            @Valid @RequestBody UserModel.Create user) {
        UserModel.Response response = userService.createUser(user);
        return CustomResponseEntity.showDetail(response);
    }

    @PostMapping(value = "/user/{id}/update")
    public CustomResponseEntity<UserModel.Response> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserModel.Update user) {
        UserModel.Response response = userService.updateUser(id, user);
        return CustomResponseEntity.showDetail(response);
    }

    @PostMapping(value = "/user/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("data successfully deleted");
    }
}
