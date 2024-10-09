package com.example.buysell.models;

import com.example.buysell.models.enums.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Document(collection = "users")
@Data
public class User implements UserDetails {
    @Id
    private String id;

    @Field("email")
    private String email;

    @Field("phone_number")
    private String phoneNumber;

    @Field("name")
    private String name;

    @Field("active")
    private boolean active;

    @DBRef
    @Field("image_id")
    private Image avatar;

    @Field("password")
    private String password;

    @Field("roles")
    private Set<Role> roles = new HashSet<>();

    @DBRef
    @Field("products")
    private List<Product> products = new ArrayList<>();

    @Field("date_of_created")
    private LocalDateTime dateOfCreated;

    public User() {
        dateOfCreated = LocalDateTime.now(); // Инициализация даты создания
    }

    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }

    public String getAvatarUrl() {
        if (avatar != null && avatar.getOriginalFileName() != null) {
            return "/images/avatars/" + avatar.getOriginalFileName();
        }
        return null; // Верните URL по умолчанию
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) () -> role.name())
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public void addProduct(Product product) {
        product.setUser(this); // Убедитесь, что продукт знает о пользователе
        products.add(product);
    }
}