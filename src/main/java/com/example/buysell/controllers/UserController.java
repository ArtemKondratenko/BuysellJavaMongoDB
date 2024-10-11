package com.example.buysell.controllers;

import com.example.buysell.models.User;
import com.example.buysell.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/hello")
    public String securityUrl() {
        return "hello";
    }

    @PostMapping("/user/upload-avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file, Principal principal) {
        User user = userService.findByEmail(principal.getName());

        if (user == null) {
            return "redirect:/login";
        }

        try {
            userService.saveUserAvatar(user, file);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/products?error=upload";
        }

        return "redirect:/products";
    }

    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable String id, Model model) {
        try {
            User user = userService.findById(id);
            model.addAttribute("user", user);
            model.addAttribute("avatarUrl", user.getAvatarUrl()); // Передаем URL аватара
            return "user-info"; // Название вашего шаблона
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Произошла ошибка при загрузке информации о пользователе.");
            return "error";
        }
    }
}