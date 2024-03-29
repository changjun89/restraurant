package me.changjun.restaurant.interfaces;


import me.changjun.restaurant.application.EmailNotExistedException;
import me.changjun.restaurant.application.PasswordWrongException;
import me.changjun.restaurant.application.UserService;
import me.changjun.restaurant.domain.User;
import me.changjun.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void createWithValidAttribute() throws Exception {
        long id = 1L;
        String name = "changjun";
        String email = "leechang0423@naver.com";
        String password = "password";
        User mockUser = User.builder()
                .id(id)
                .name(name)
                .build();
        given(userService.authenticate(email, password)).willReturn(mockUser);

        given(jwtUtil.createToken(id, name, null)).willReturn("header.payload.signature");

        mockMvc.perform(post("/api/session/")
                .content("{\"email\":\"leechang0423@naver.com\",\"name\":\"changjun\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/session"))
                .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"")));

        verify(userService).authenticate(eq(email), eq(password));
    }

    @Test
    public void createRestaurantId() throws Exception {
        long id = 1L;
        String name = "changjun";
        String email = "leechang0423@naver.com";
        String password = "password";
        long restaurantId = 50L;
        User mockUser = User.builder()
                .id(id)
                .name(name)
                .level(50)
                .restaurantId(restaurantId)
                .build();
        given(userService.authenticate(email, password)).willReturn(mockUser);

        given(jwtUtil.createToken(id, name, restaurantId)).willReturn("header.payload.signature");

        mockMvc.perform(post("/api/session/")
                .content("{\"email\":\"leechang0423@naver.com\",\"name\":\"changjun\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/session"))
                .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"")));

        verify(userService).authenticate(eq(email), eq(password));
    }

    @Test
    public void createWithNotExistedEmail() throws Exception {
        given(userService.authenticate("X@naver.com", "password"))
                .willThrow(EmailNotExistedException.class);

        mockMvc.perform(post("/api/session/")
                .content("{\"email\":\"X@naver.com\",\"name\":\"changjun\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("X@naver.com"), eq("password"));
    }

    @Test
    public void createWithWrongPassword() throws Exception {
        given(userService.authenticate("leechang0423@naver.com", "X"))
                .willThrow(PasswordWrongException.class);

        mockMvc.perform(post("/api/session/")
                .content("{\"email\":\"leechang0423@naver.com\",\"name\":\"changjun\",\"password\":\"X\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("leechang0423@naver.com"), eq("X"));
    }
}