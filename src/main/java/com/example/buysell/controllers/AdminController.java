package com.example.buysell.controllers;

import com.example.buysell.models.User;
import com.example.buysell.models.enums.Role;
import com.example.buysell.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/admin")
    public String admin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.info("Authenticated user: {}", authentication.getName());
            logger.info("Authorities: {}", authentication.getAuthorities());
        }

        List<User> users = userService.list();
        logger.info("Number of users retrieved: {}", users != null ? users.size() : 0);
        model.addAttribute("users", users);
        logger.info("Users type: {}", users != null ? users.getClass().getName() : "null");

        return "admin";
    }

    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") String id) {
        try {
            userService.banUser(id);
            logger.info("User with ID {} has been banned.", id);
        } catch (Exception e) {
            logger.error("Failed to ban user with ID {}: {}", id, e.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String userEdit(@PathVariable("id") String id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            logger.warn("User with ID {} not found.", id);
            return "redirect:/admin"; // Перенаправление на главную страницу админа, если пользователь не найден
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping("/admin/user/edit")
    public String userEdit(@RequestParam("userId") String userId, @RequestParam Map<String, String> form) {
        User user = userService.findById(userId);

        if (user == null) {
            return "redirect:/admin"; // Обработка случая, когда пользователь не найден
        }

        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }
}