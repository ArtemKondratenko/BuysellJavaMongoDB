package com.example.buysell.services;

import com.example.buysell.models.Image;
import com.example.buysell.models.User;
import com.example.buysell.models.enums.Role;
import com.example.buysell.repositories.ImageRepository;
import com.example.buysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    public boolean createUser(User user) {
        String email = user.getEmail();

        // Проверка на существующего пользователя
        if (userRepository.findByEmail(email) != null) return false;

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Установка роли в зависимости от email
        if ("artem-osteo@yandex.ru".equals(email)) {
            user.getRoles().add(Role.ROLE_ADMIN); // Устанавливаем роль администратора
        } else {
            user.getRoles().add(Role.ROLE_USER); // Устанавливаем роль пользователя по умолчанию
        }

        log.info("Saving new User with email: {}", email);
        userRepository.save(user);
        return true;
    }

    public List<User> list() {
        List<User> users = userRepository.findAll();
        log.info("Retrieved {} users from the database.", users.size());
        return users;
    }

    public void banUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(!user.isActive());
            log.info("{} user with id = {}; email: {}", user.isActive() ? "Unban" : "Ban", user.getId(), user.getEmail());
            userRepository.save(user);
        }
    }

    public void saveUserAvatar(User user, MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName("Avatar for " + user.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setSize(file.getSize());
        image.setContentType(file.getContentType());
        image.setBytes(file.getBytes());
        image.setPreviewImage(true);
        imageRepository.save(image);
        user.setAvatar(image);
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void changeUserRoles(String userId, String[] selectedRoles) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.getRoles().clear(); // Очищаем текущие роли
            if (selectedRoles != null) {
                for (String role : selectedRoles) {
                    user.getRoles().add(Role.valueOf(role)); // Добавляем новую роль
                }
            }
            userRepository.save(user); // Сохраняем изменения
        }
    }

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}