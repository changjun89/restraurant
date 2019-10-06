package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.CategoryService;
import me.changjun.restaurant.domain.Category;
import me.changjun.restaurant.domain.Region;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;

    @Test
    public void list() throws Exception {
        //given
        List<Category> mockCategories = new ArrayList<>();
        Category mockCategory = Category.builder()
                .name("한식")
                .build();
        mockCategories.add(mockCategory);

        //when
        given(categoryService.getCategories()).willReturn(mockCategories);

        //then
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("한식")));
    }


    @Test
    public void create() throws Exception {
        Category category = Category.builder()
                .id(1L)
                .name("한식")
                .build();

        given(categoryService.addCategory(any())).willReturn(category);

        mockMvc.perform(post("/api/categories")
                .content("{\"name\":\"한식\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION,"/api/categories/"+category.getId()))
                .andExpect(content().string("{}"));

        verify(categoryService).addCategory(any());
    }
}
