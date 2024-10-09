package com.example.buysell.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("price")
    private int price;

    @Field("city")
    private String city;

    @Field("images")
    private List<Image> images = new ArrayList<>();

    private String previewImageId;

    @DBRef
    @Field("user_id")
    private User user;

    @Field("date_of_created")
    private LocalDateTime dateOfCreated;

    public void addImageToProduct(Image image) {
        image.setProduct(this); // Убедитесь, что изображение знает о продукте
        images.add(image);
    }
}