package com.example.buysell.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("original_file_name")
    private String originalFileName;

    @Field("size")
    private Long size;

    @Field("content_type")
    private String contentType;

    @Field("is_preview_image")
    private boolean isPreviewImage;

    @Field("bytes")
    private byte[] bytes;

    // Удален DBRef для связи с Product, если вы хотите сохранить идентификатор продукта вместо этого
    @Field("product_id")
    private String productId;

    // Можно добавить метод для установки продукта, если это необходимо
    public void setProduct(Product product) {
        this.productId = product.getId(); // Устанавливаем идентификатор продукта
    }
}