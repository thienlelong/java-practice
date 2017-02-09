package com.agilityio.todo.web;

import com.agilityio.todo.domain.User;
import com.agilityio.todo.repository.UserRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Project Name: todo-app
 * Created on 2/9/17.
 */
public class UserResourceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResourceTest.class);
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserResource userResource;

    private User user;

    JSONObject userInput;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
        user = new User("userdemo", "Haleigh Robel", "userdemo@gmail.com", "123456");
        userInput = new JSONObject();
        userInput.put("userName", user.getUserName());
        userInput.put("fullName", user.getFullName());
        userInput.put("email", user.getEmail());
        userInput.put("password", user.getPassword());
    }


    @Test
    public void registerUser() throws Exception {
//        Mockito.when(userRepository.findUsersByUserName("userdemo")).thenReturn(null);
//        Mockito.when(userRepository.findUserByEmail("userdemo@gmail.com")).thenReturn(null);
        Mockito.when(userRepository.save(Matchers.isA(User.class))).thenReturn(user);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(userInput.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("userName").value("userdemo"))
                .andExpect(jsonPath("fullName").value("Haleigh Robel"))
                .andExpect(jsonPath("email").value("userdemo@gmail.com"))
                .andExpect(jsonPath("password").value(passwordEncoder.encode("123456")));

    }

    @Test
    public void testGetUserInvalid() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().is4xxClientError());
    }

}