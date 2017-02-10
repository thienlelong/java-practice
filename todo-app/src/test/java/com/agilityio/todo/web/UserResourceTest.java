package com.agilityio.todo.web;

import com.agilityio.todo.domain.User;
import com.agilityio.todo.repository.UserRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Project Name: todo-app
 * Test class for the UserResource REST controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserResourceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResourceTest.class);

    JSONObject userInput;

    @MockBean
    UserRepository userRepository;

    private User user;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter).findAny().get();
    }

    @SuppressWarnings("unchecked")
    protected String toJsonString(Object obj) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(obj, MediaType.APPLICATION_JSON, mockHttpOutputMessage);

        return mockHttpOutputMessage.getBodyAsString();
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User(1, "userdemo", "Haleigh Robel", "userdemo@gmail.com", "123456");
        userInput = new JSONObject();
        userInput.put("userName", user.getUserName());
        userInput.put("fullName", user.getFullName());
        userInput.put("email", user.getEmail());
        userInput.put("password", user.getPassword());
    }

    @Test
    public void testRegisterUserInValid() throws Exception {
        userInput.put("email", "invalid_email");
        mockMvc.perform(post("/api/register")
                .contentType(contentType)
                .content(userInput.toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testRegisterUserValid() throws Exception {
        Mockito.when(userRepository.save(Matchers.isA(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/register")
                .contentType(contentType)
                .content(userInput.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("userName").value("userdemo"))
                .andExpect(jsonPath("fullName").value("Haleigh Robel"))
                .andExpect(jsonPath("email").value("userdemo@gmail.com"));
    }

    @Test
    public void testRegisterUserUserNameExist() throws Exception {
        Mockito.when(userRepository.findUsersByUserName(user.getUserName())).thenReturn(user);
        mockMvc.perform(post("/api/register")
                .contentType(contentType)
                .content(userInput.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User Name already in use"));
    }

    @Test
    public void testRegisterUserEExist() throws Exception {
        Mockito.when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
        mockMvc.perform(post("/api/register")
                .contentType(contentType)
                .content(userInput.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email already in use"));
    }

}