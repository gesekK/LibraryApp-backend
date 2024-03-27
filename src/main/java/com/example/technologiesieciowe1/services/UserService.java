package com.example.technologiesieciowe1.services;

import com.example.technologiesieciowe1.entities.User;
import com.example.technologiesieciowe1.exceptions.BookNotFoundException;
import com.example.technologiesieciowe1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public User getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof User user) {
//                return user;
//            }
//        }
//        // Jeśli użytkownik nie jest uwierzytelniony lub informacje o użytkowniku nie są dostępne, rzuć odpowiedni wyjątek
//        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User is not authenticated or information about user is not available") {
//        };
//    }
    public User addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with username " + user.getUsername() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("User with id: " + id + " not found."));
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("User with id " + id + " not found"));
        userRepository.delete(user);
    }
    public User updateUser(Long id, User updatedUser){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " not found"));

        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        user.setRole(updatedUser.getRole());
        user.setUsername(updatedUser.getUsername());
        user.setFullName(updatedUser.getFullName());

        return userRepository.save(user);
    }
    public User partiallyUpdateUser(Long id, Map<String, Object> updates){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("User with id " + id + " not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "email" -> user.setEmail((String) value);
                case "username" -> user.setUsername((String) value);
                case "password" -> user.setPassword(passwordEncoder.encode((String) value));
                case "fullName" -> user.setFullName((String) value);
                case "role" -> user.setRole((String) value);
            }
        });

        return userRepository.save(user);
    }

}

