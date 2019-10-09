package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.UserService;
import me.changjun.restaurant.domain.User;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder().name("changjun").email("leechang0423@naver.com").level(2).build());
        given(userService.getUsers()).willReturn(users);

        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(Matchers.is("changjun")))
                .andExpect(jsonPath("$[0].email").value(Matchers.is("leechang0423@naver.com")))
                .andExpect(jsonPath("$[0].level").value(Matchers.is(2)));
    }

    @Test
    public void create() throws Exception {
        String email = "leechang0423@naver.com";
        String name = "changjun";
        User user = User.builder()
                .id(1L)
                .name(name)
                .email(email)
                .build();
        given(userService.addUser(email, name)).willReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"changjun\",\"email\":\"leechang0423@naver.com\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/user/1"));

        verify(userService).addUser(email, name);
    }
}
