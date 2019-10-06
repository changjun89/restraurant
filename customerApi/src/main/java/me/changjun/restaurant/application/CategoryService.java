package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Category;
import me.changjun.restaurant.domain.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository regionRepository;

    public CategoryService(CategoryRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Category> getCategories() {
        return regionRepository.findAll();
    }
}
