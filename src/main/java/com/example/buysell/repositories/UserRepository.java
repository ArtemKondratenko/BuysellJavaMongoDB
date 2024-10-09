package com.example.buysell.repositories;

import com.example.buysell.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email); // Метод поиска по email


}