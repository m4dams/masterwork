package com.gfa.library.books.controllers;

import com.gfa.library.TestNoSecurityConfig;
import com.gfa.library.users.models.entities.User;
import com.gfa.library.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
class BookControllerITest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserService userService;

  private User user;
  private Authentication auth;

  @BeforeEach
  void setup() {
    user = userService.getByUsername("librarian");
    auth = new UsernamePasswordAuthenticationToken(user, null, null);
  }

  @Test
  @DisplayName("GET book by id")
  public void when_bookIdValid_ReturnBookInfo() throws Exception {

    mvc.perform(MockMvcRequestBuilders
            .get("/books/103")
            .principal(auth))
        .andExpect(status().is(200));
  }

  @Test
  @DisplayName("GET book matching criteria")
  public void when_criteria_returnBooks() throws Exception {
    mvc.perform(MockMvcRequestBuilders
            .get("/books/?title=title1")
            .principal(auth))
        .andExpect(status().is(200));

  }


  @Test
  @DisplayName("DELETE book with id")
  public void when_ValidAccessLevel_deleteBookWithId() throws Exception {
    mvc.perform(MockMvcRequestBuilders
            .delete("/books/104")
            .principal(auth))
        .andExpect(status().is(200));
  }

  @Test
  @DisplayName("POST add new book without body")
  public void when_noBody_throwValidationException() throws Exception {
    String body = "{}";
    String expected = "{\"status\":\"error\",\"errors\":[{\"field\":\"authors\",\"messages\":[\"Author(s)" +
        " required\"]},{\"field\":\"availability\",\"messages\":[\"Please set availability\"]}," +
        "{\"field\":\"title\",\"messages\":[\"Title required\"]},{\"field\":\"releaseYear\"," +
        "\"messages\":[\"Release year required\"]},{\"field\":\"isbn\",\"messages\":[\"ISBN number required\"]}," +
        "{\"field\":\"categories\",\"messages\":[\"Category required\"]}]}";
    postTestWithRequestBody(auth, body, 400, expected);

  }

  @Test
  @DisplayName("POST add new book with valid user")
  public void when_newBook_withValidUser_returnBookDto() throws Exception {
    String body = "{\"isbn\":\"10DigtIsbn\",\"title\":\"title\",\"authors\":[{\"name\":\"1\"},{\"name\":\"2\"}]," +
        "\"categories\":[{\"name\":\"this\"},{\"name\":\"that\"}],\"releaseYear\":1984,\"availability\":\"COPYONLY\"}";
    String expected = "{\"id\":1,\"isbn\":\"10DigtIsbn\",\"title\":\"title\",\"categories\":[{\"name\":\"that\"}" +
        ",{\"name\":\"this\"}],\"authors\":[{\"name\":\"2\"},{\"name\":\"1\"}],\"releaseYear\":1984," +
        "\"availability\":\"COPYONLY\",\"returnTime\":null,\"userDto\":null}";

    postTestWithRequestBody(auth, body, 201, expected);
  }

  private void postTestWithRequestBody(Authentication auth, String body, int code, String expected) throws Exception {
    mvc.perform(MockMvcRequestBuilders
            .post("/books")
            .principal(auth)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().is(code))
        .andExpect(content().json(expected));
  }

}