package com.kianchart.kianchart.api.users;

import com.kianchart.kianchart.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @RequestMapping(value = "/user/show", method = RequestMethod.GET)
    public Optional<User> getOneUser(Long id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity<UserDTO.Response> createUser(@RequestBody UserDTO.CreateRequest user) {
        UserDTO.Response response = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.PUT)
    public User updateUser(Long id, User user) {
        return userService.updateUser(id, user);
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
