package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.CategoryService;
import me.changjun.restaurant.domain.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping("/api/categories")
    public ResponseEntity create(@RequestBody Category resource) throws URISyntaxException {
        Category newCategory = categoryService.addCategory(resource);
        URI uri = new URI("/api/categories/" + newCategory.getId());
        return ResponseEntity.created(uri).body("{}");
    }
}
