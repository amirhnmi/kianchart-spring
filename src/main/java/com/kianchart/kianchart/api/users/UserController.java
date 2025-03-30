package com.kianchart.kianchart.api.users;

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
    public ResponseEntity<List<UserDTO.Response>> getAllUsers() {
        List<UserDTO.Response> data = userService.getAllUser();
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}/show", method = RequestMethod.GET)
    public ResponseEntity<UserDTO.Response> getOneUser(@PathVariable Long id) {
        UserDTO.Response user = userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity<UserDTO.Response> createUser(@RequestBody UserDTO.CreateRequest user) {
        UserDTO.Response response = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(value = "/user/{id}/update", method = RequestMethod.PUT)
    public ResponseEntity<UserDTO.Response> updateUser(@PathVariable Long id, @RequestBody  UserDTO.UpdateRequest user) {
        UserDTO.Response response = userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value = "/user/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("data successfully deleted.",HttpStatus.OK);
    }
}
