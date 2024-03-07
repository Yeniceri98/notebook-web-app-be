package org.example.notebookwebappbe.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.notebookwebappbe.entity.User;
import org.example.notebookwebappbe.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Controller", description = "CRUD Operations")
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/basicAuth")
    public String basicAuthCheck() {
        return "Success";
    }

    @Tag(name = "Registering New User")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.addNewUser(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error on registering user: " + e.getMessage());
        }
    }

    @Tag(name = "Retrieving All Registered Users")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllRegisteredUsers();
        return ResponseEntity.ok(users);
    }

    @Tag(name = "Deleting a User")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch(Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
