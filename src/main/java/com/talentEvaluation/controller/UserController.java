
package com.talentEvaluation.controller;

import com.talentEvaluation.dto.UserResponse;
import com.talentEvaluation.dto.UserUpdateDto;
import com.talentEvaluation.entity.User;
import com.talentEvaluation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    // @GetMapping("/all")
    // public ResponseEntity<List<UserResponse>> getAllUsers() {
    //     return ResponseEntity.ok(userService.getAllUsers());
    // }

    // @GetMapping("/evaluators")
    // public ResponseEntity<List<UserResponse>> getEvaluators() {
    //     return ResponseEntity.ok(userService.getAllEvaluators());
    // }

    @GetMapping("/id/{associateId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long associateId) {
        return ResponseEntity.ok(userService.getUserById(associateId));
    }

    @DeleteMapping("/{associateId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long associateId) {
        userService.deleteUser(associateId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.updateUser(userUpdateDto));
    }
}
