package com.example.technologiesieciowe1.repositories;

import com.example.technologiesieciowe1.entities.Loan;
import com.example.technologiesieciowe1.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);
    void deleteById(Long userId);
}