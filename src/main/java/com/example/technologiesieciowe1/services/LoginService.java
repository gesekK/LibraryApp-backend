package com.example.technologiesieciowe1.services;

import com.example.technologiesieciowe1.entities.LoginForm;
import com.example.technologiesieciowe1.entities.User;
import com.example.technologiesieciowe1.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class LoginService {

    @Value("${jwt.token.key}")
    private String key;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String userLogin(LoginForm loginForm){
        User user = userRepository.findByUsername(loginForm.getLogin());
        if(user != null && passwordEncoder.matches(loginForm.getPassword(), user.getPassword())){
            long currentTimeMillis = System.currentTimeMillis();
            return Jwts.builder()
                    .issuedAt(new Date(currentTimeMillis))
                    .expiration(new Date(currentTimeMillis + 5 * 60 * 100000))
                    .claim("id", user.getUserId())
                    .claim("role", user.getRole())
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
        } else {
            return null;
        }
    }
}
