package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Category;
import me.changjun.restaurant.domain.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CategoryServiceTest {
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    public void list() {
        //given
        List<Category> mockCategories = new ArrayList<>();
        Category mockCategory = Category.builder()
                .name("한식")
                .build();
        mockCategories.add(mockCategory);

        //when
        given(categoryRepository.findAll()).willReturn(mockCategories);

        //then
        List<Category> categories = categoryService.getCategories();
        Category category = categories.get(0);

        assertThat(category.getName()).isEqualTo("한식");
    }
}
