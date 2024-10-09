package com.example.buysell.repositories;

import com.example.buysell.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByTitle(String title); // Метод поиска по заголовку


}