package com.example.buysell.repositories;

import com.example.buysell.models.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {
    // Здесь можно добавить дополнительные методы, если это необходимо
}