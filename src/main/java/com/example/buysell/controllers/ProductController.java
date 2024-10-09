package com.example.buysell.controllers;

import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title,
                           Principal principal, Model model) {
        model.addAttribute("products", productService.listProducts(title));

        User user = principal != null ? productService.getUserByPrincipal(principal) : null;
        model.addAttribute("user", user);

        String avatarUrl = (user != null && user.getAvatar() != null) ?
                "/images/" + user.getAvatar().getId() : "/images/default-avatar.png";
        model.addAttribute("avatarUrl", avatarUrl);

        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable String id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/error"; // Обработка случая, когда товар не найден
        }
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("files") MultipartFile[] files,
                                @ModelAttribute Product product,
                                Principal principal) {
        try {
            productService.saveProduct(principal, product, files);
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении товара: " + e.getMessage());
            return "error"; // Обработка ошибок
        }
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}