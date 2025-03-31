package com.kianchart.kianchart.api.users.users;

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
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public CustomResponseEntity<List<UserDTO.Response>> getAllUsers(
            @RequestParam(defaultValue = "asc") SortDirection sort,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean total
    ) {
        List<UserDTO.Response> users = userService.getAllUser(sort, skip, limit);
        Long totalCount = total ? userService.countAllUser() : null;
        return CustomResponseEntity.showList(users, totalCount);
    }

    @RequestMapping(value = "/user/{id}/show", method = RequestMethod.GET)
    public CustomResponseEntity<UserDTO.Response> getOneUser(@PathVariable Long id) {
        UserDTO.Response user = userService.getUserById(id);
        return CustomResponseEntity.showDetail(user);
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public CustomResponseEntity<UserDTO.Response> createUser(
            @Valid @RequestBody UserDTO.CreateRequest user) {
        UserDTO.Response response = userService.createUser(user);
        return CustomResponseEntity.showDetail(response);
    }

    @RequestMapping(value = "/user/{id}/update", method = RequestMethod.PUT)
    public CustomResponseEntity<UserDTO.Response> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO.UpdateRequest user) {
        UserDTO.Response response = userService.updateUser(id, user);
        return CustomResponseEntity.showDetail(response);
    }

    @RequestMapping(value = "/user/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("data successfully deleted");
    }
}
