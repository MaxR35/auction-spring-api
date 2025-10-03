package fr.rougeux.projet.auction.controller;

import fr.rougeux.projet.auction.dto.CategoryDTO;
import fr.rougeux.projet.auction.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/categories")
    public List<CategoryDTO> getCategories() {
        return categoryService.readAllCategories();
    }
}
