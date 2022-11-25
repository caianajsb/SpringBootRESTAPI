package com.example.school;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = SchoolApplication.class)
public class SchoolApplicationTests {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
          .apply(springSecurity())
          .build();
    }

    @Test
    @WithUserDetails(value = "admin")
    public void whenAdminAccessUserEndpoint_thenOk() throws Exception {
        mvc.perform(get("/school/students"))
          .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "admin")
    public void whenAdminAccessDeleteSecuredEndpoint_thenIsOk() throws Exception {
        mvc.perform(delete("/school/students/{id}").content("{}"))
          .andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails(value = "admin")
    public void whenUserAccessRestrictedEndpoint_thenOk() throws Exception {
        mvc.perform(post("/school/students"))
          .andExpect(status().isOk());
    }


    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessRestrictedEndpoint_thenIsUnauthorized() throws Exception {
        mvc.perform(get("/students"))
          .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails()
    public void whenUserAccessUserSecuredEndpoint_thenOk() throws Exception {
        mvc.perform(get("/user"))
          .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails()
    public void whenUserAccessRestrictedEndpoint_thenIsForbidden() throws Exception {
        mvc.perform(post("/students"))
          .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails()
    public void whenUserAccessAdminSecuredEndpoint_thenIsForbidden() throws Exception {
        mvc.perform(put("/students"))
          .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails()
    public void whenUserAccessDeleteSecuredEndpoint_thenIsForbidden() throws Exception {
        mvc.perform(delete("/students"))
          .andExpect(status().isForbidden());
    }
}
