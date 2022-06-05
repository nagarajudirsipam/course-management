package com.emeritus.course.controller;

import com.emeritus.course.CourseManagemengtApplication;
import com.emeritus.course.config.WebSecurityConfig;
import com.emeritus.course.model.EmeritusUser;
import com.emeritus.course.repository.UserRepository;
import com.emeritus.course.service.LoginService;
import com.emeritus.course.service.UserService;
import com.emeritus.course.util.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {LoginController.class, LoginService.class, UserService.class, UserRepository.class})
@SpringBootTest(classes = {CourseManagemengtApplication.class})
@Import(WebSecurityConfig.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    @Test
    public void registerWithInvalidTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(mapper.writeValueAsString(getUser()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertNotNull(response);
    }

    @Test
    public void registerWithSystemAdminTest() throws Exception {
        EmeritusUser user = getUser();
        user.setRole(Roles.SYSTEM_ADMIN.getRole());
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertNotNull(response);
    }

    private EmeritusUser getUser(){
        EmeritusUser emeritusUser = new EmeritusUser();
        emeritusUser.setUserName("nagarajudirsipam");
        emeritusUser.setFirstName("Nagaraju");
        emeritusUser.setLastName("Dirsipam");
        emeritusUser.setRole("STUDENT");
        emeritusUser.setPhone("9494551784");
        emeritusUser.setEmail("nagarajudirsipam@gmail.com");
        return emeritusUser;
    }

}
