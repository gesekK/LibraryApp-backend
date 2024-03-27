package com.example.technologiesieciowe1.controllers;

import com.example.technologiesieciowe1.entities.Loan;
import com.example.technologiesieciowe1.entities.User;
import com.example.technologiesieciowe1.exceptions.UserNotFoundException;
import com.example.technologiesieciowe1.services.LoanService;
import com.example.technologiesieciowe1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final LoanService loanService;

    @Autowired
    public UserController(UserService userService, LoanService loanService){
        this.userService = userService;
        this.loanService = loanService;
    }
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User addUser(@RequestBody User user) { return userService.addUser(user); }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return user;
    }
    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("No users found");
        }
        return user;
    }
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email){
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("No users found");
        }
        return user;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<User> updateUserPartially(@PathVariable Long id, @RequestBody Map<String, Object> updates){
        User user = userService.partiallyUpdateUser(id, updates);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/all/{id}")
    public ResponseEntity<User> updateUserCompletely(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/history/{userId}")
    public ResponseEntity<Iterable<Loan>> getLoanHistory(@PathVariable Long userId) {
        Iterable<Loan> loanHistory = loanService.getLoanHistoryByUserId(userId);
        return ResponseEntity.ok(loanHistory);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }


}
