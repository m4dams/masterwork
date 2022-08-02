package com.gfa.library.login.controllers;

import com.gfa.library.TestNoSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
class LoginControllerITest {

  @Autowired
  private MockMvc mvc;

  @BeforeEach
  public void setup() {
  }

  @Test
  @DisplayName("POST login without body")
  public void login_without_body() throws Exception {

    RequestBuilder request = MockMvcRequestBuilders.post("/login");
    MvcResult result = mvc.perform(request).andReturn();

    String expected = "";
    String actual = result.getResponse().getContentAsString();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("POST login with empty JSON")
  public void login_with_empty_body() throws Exception {
    String credential = "{}";

    String expected = "{\"status\":\"error\",\"errors\": [{\"field\":\"username\", "
        + "\"messages\": [\"Username is required.\"]},"
        + "{\"field\":\"password\",\"messages\": [\"Password is required.\"]}]}";
    postTestWithRequestBody(400, credential, expected);
  }

  @Test
  @DisplayName("POST login with valid credentials")
  public void when_validUserAndPassword_returnToken() throws Exception {
    String credential = "{ \"username\" : \"admin\",  \"password\" : \"password\" }";
    String expected = "{\"status\":\"ok\",\"token\":\"\"}";

    mvc.perform(MockMvcRequestBuilders
            .post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(credential))
        .andExpect(status().is(200))
        .andExpect(result -> {
          String response = result.getResponse().getContentAsString();
          String[] splitted = response.split("[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*");
          String actual = splitted[0] + splitted[1];
          assertEquals(expected, actual);
        });
  }


  private void postTestWithRequestBody(int code, String requestBody, String expected) throws Exception {
    mvc.perform(MockMvcRequestBuilders
            .post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().is(code))
        .andExpect(content().json(expected));
  }


}