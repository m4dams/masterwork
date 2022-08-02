package com.gfa.library.users.controllers;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerITest {

  @Autowired
  private MockMvc mvc;


  private User user;
  private Authentication auth;

  @Autowired
  private UserService userService;

  @BeforeEach
  void setup() {
    user = userService.getByUsername("admin");
    auth = new UsernamePasswordAuthenticationToken(user, null, null);
  }

  @Test
  @DisplayName("POST Successful registration")
  public void when_CorrectFields_ReturnExpected() throws Exception {
    String body = "{\"username\":\"user99\",\"email\":\"email99\",\"firstName\":\"firstName\"," +
        "\"lastName\":\"lastName\",\"dateOfBirth\":\"1975-05-23\",\"address\":{\"county\":\"county\",\"city\":\"city\"" +
        ",\"streetAndBuilding\":\"streetAndBuilding\",\"postalCode\":34567},\"password\":\"password\"}";
    String expected = "{\"id\":1,\"username\":\"user99\",\"email\":\"email99\",\"firstName\":\"firstName\"," +
        "\"lastName\":\"lastName\",\"dateOfBirth\":\"1975-05-23\",\"addressDto\":{\"id\":1,\"county\":\"county\"," +
        "\"city\":\"city\",\"postalCode\":34567,\"streetAndBuilding\":\"streetAndBuilding\"}}";

    registrationTestWithRequestBody(body, 201, expected);
  }

  @Test
  @DisplayName("POST Registration failure with field missing")
  public void when_MissingField_ThrowException() throws Exception {
    String missingUsername = "{\"username\": null,\"email\":\"email\",\"firstName\":\"firstName\"," +
        "\"lastName\":\"lastName\",\"dateOfBirth\":\"1975-05-23\",\"address\":{\"county\":\"county\",\"city\":\"city\"" +
        ",\"streetAndBuilding\":\"streetAndBuilding\",\"postalCode\":34567},\"password\":\"password\"}";

    register(missingUsername, 400);
  }

  @Test
  @DisplayName("POST Registration failure with embedded field missing")
  public void when_MissingAddressField_ThrowException() throws Exception {
    String missingPostalCode = "{\"username\":\"user199\",\"email\":\"email199\",\"firstName\":\"firstName\"," +
        "\"lastName\":\"lastName\",\"dateOfBirth\":\"1975-05-23\",\"address\":{\"county\":\"county\",\"city\":\"city\"" +
        ",\"streetAndBuilding\":\"streetAndBuilding\",\"postalCode\":null},\"password\":\"password\"}";

    register(missingPostalCode, 400);
  }

  @Test
  @DisplayName("POST Registration failure when username or email already registered")
  public void when_RegisterWithAlreadyExistingUsernameOrEmail_ThrowException() throws Exception {
    String takenUsername = "{\"username\":\"admin\",\"email\":\"email\",\"firstName\":\"firstName\"," +
        "\"lastName\":\"lastName\"" +
        ",\"dateOfBirth\":\"1975-05-23\",\"address\":{\"county\":\"county\",\"city\":\"city\",\"streetAndBuilding\"" +
        ":\"streetAndBuilding\",\"postalCode\":34567},\"password\":\"password\"}";
    String takenEmail = "{\"username\":\"user67\",\"email\":\"email\",\"firstName\":\"firstName\"," +
        "\"lastName\":\"lastName\"" +
        ",\"dateOfBirth\":\"1975-05-23\",\"address\":{\"county\":\"county\",\"city\":\"city\",\"streetAndBuilding\"" +
        ":\"streetAndBuilding\",\"postalCode\":34567},\"password\":\"password\"}";
    String expected1 = "{\"status\":\"error\",\"message\":\"Username already taken\"}";
    String expected2 = "{\"status\":\"error\",\"message\":\"Email already taken\"}";

    registrationTestWithRequestBody(takenUsername, 409, expected1);
    registrationTestWithRequestBody(takenEmail, 409, expected2);
  }

  @Test
  @DisplayName("GET Get all users as admin")
  public void when_GettingUsers_ReturnUsersDto() throws Exception {
    mvc.perform(MockMvcRequestBuilders
            .get("/users")
            .principal(auth))
        .andExpect(status().is(200));
  }

  @Test
  @DisplayName("GET Get all users as non admin")
  public void when_GettingUsers_ThrowException() throws Exception {
    user = userService.getByUsername("user1");
    auth = new UsernamePasswordAuthenticationToken(user, null, null);
    String expected = "{\"status\":\"error\",\"message\":\"Unauthorized user\"}";
    mvc.perform(MockMvcRequestBuilders
            .get("/users")
            .principal(auth))
        .andExpect(status().is(403))
        .andExpect(content().json(expected));
  }

  @Test
  @DisplayName("GET user by criteria with valid access level")
  public void when_GetUserWithCriteria_ReturnUserDto() throws Exception {
    String search = "/users/?id=103";
    String expected = "{\"id\":103,\"username\":\"user1\",\"email\":\"email3\",\"firstName\":\"firstName3\"," +
        "\"lastName\":\"lastName3\",\"dateOfBirth\":\"1992-04-17\",\"address\":{\"id\":103,\"county\":\"county2\"," +
        "\"city\":\"city3\",\"postalCode\":3,\"streetAndBuilding\":\"streetAndBuilding3\"},\"accessLevel\":\"USER\"," +
        "\"books\":[{\"id\":104,\"isbn\":\"123456789d\",\"title\":\"title1\",\"categories\":[{\"name\":\"that\"}," +
        "{\"name\":\"else\"}],\"authors\":[{\"name\":\"someone\"},{\"name\":\"Bob\"}],\"releaseYear\":1992," +
        "\"availability\":\"AVAILABLE\",\"returnTime\":\"2032-10-12\",\"userDto\":{\"id\":103,\"username\":\"user1\"," +
        "\"accessLevel\":\"USER\"}}]}";
    getTestForUsersWithCriteria(auth, search, 200, expected);
  }
  @Test
  @DisplayName(("GET user by criteria without criteria"))
  public void when_CriteriaNull_throwsException() throws Exception {
    String search = "/users/";
    String expected = "{\"status\":\"error\",\"message\":\"Search criteria not found\"}";
    getTestForUsersWithCriteria(auth, search, 404, expected);
  }

  @Test
  @DisplayName("GET get user by criteria with invalid access level")
  public void when_GetUserWithCriteria_ThrowException() throws Exception {
    user = userService.getByUsername("user1");
    auth = new UsernamePasswordAuthenticationToken(user, null, null);
    String search1 = "/users/?id=3";
    String search2 = "/users/?username=librarian";
    String search3 = "/users/?email=email1";
    String expected = "{\"status\":\"error\",\"message\":\"Unauthorized user\"}";
    getTestForUsersWithCriteria(auth, search1, 403, expected);
    getTestForUsersWithCriteria(auth, search2, 403, expected);
    getTestForUsersWithCriteria(auth, search3, 403, expected);
  }

  @Test
  @DisplayName("DELETE user with id")
  public void when_deleteUserWithValidAccessLevel_do_orThrowException() throws Exception {
    String body = "{\"username\":\"user299\",\"email\":\"email299\",\"firstName\":\"firstName\"," +
        "\"lastName\":\"lastName\"" +
        ",\"dateOfBirth\":\"1975-05-23\",\"address\":{\"county\":\"county\",\"city\":\"city\",\"streetAndBuilding\"" +
        ":\"streetAndBuilding\",\"postalCode\":34567},\"password\":\"password\"}";
    register(body, 201);
    User toDelete = userService.getByUsername("user299");
    String expected = "User with id: " + toDelete.getId() + " was deleted successfully";
    mvc.perform(MockMvcRequestBuilders
            .delete("/users/" + toDelete.getId())
            .principal(auth))
        .andExpect(status().is(200))
        .andExpect(content().string(expected));

  }

  @Test
  @DisplayName("POST Change user details")
  public void when_EverythingOk_ReturnExpected() throws Exception {
    String regUser = "{\"username\":\"user999\",\"email\":\"email999\",\"firstName\":\"firstName\"," +
        "\"lastName\":\"lastName\",\"dateOfBirth\":\"1975-05-23\",\"address\":{\"county\":\"county\"," +
        "\"city\":\"city\",\"streetAndBuilding\":\"streetAndBuilding\"," +
        "\"postalCode\":34567},\"password\":\"password\"}";
    String changingUser = "{\"username\":\"user55\",\"email\":\"email55\",\"dateOfBirth\":\"1985-04-02\"," +
        "\"address\":{\"county\":\"county\"," +
        "\"city\":\"aCity\",\"postalCode\":45639},\"accessLevel\":\"LIBRARIAN\"}";
    String expected = "{\"id\":1,\"username\":\"user55\",\"email\":\"email55\",\"firstName\":\"firstName\"," +
        "\"lastName\":\"lastName\",\"dateOfBirth\":\"1985-04-02\",\"addressDto\":{\"id\":1,\"county\":\"county\"," +
        "\"city\":\"aCity\",\"postalCode\":45639,\"streetAndBuilding\":\"streetAndBuilding\"}}";
    register(regUser, 201);
    User toChange = userService.getByUsername("user999");
    putTestChangingUserDetailsWithPathVariableAndRequestBody(auth,
        toChange.getId(), changingUser, 200, expected);

  }

  private void putTestChangingUserDetailsWithPathVariableAndRequestBody(
      Authentication auth, Integer id, String body, int code, String expected) throws Exception {
    mvc.perform(MockMvcRequestBuilders
            .put("/users/" + id)
            .principal(auth)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().is(code))
        .andExpect(content().json(expected));
  }

  private void registrationTestWithRequestBody(String body, int code, String expected) throws Exception {
    mvc.perform(MockMvcRequestBuilders
            .post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().is(code))
        .andExpect(content().json(expected));
  }

  private void getTestForUsersWithCriteria(Authentication auth,
                                           String search, int code, String expected) throws Exception {
    mvc.perform(MockMvcRequestBuilders
            .get(search)
            .principal(auth))
        .andExpect(status().is(code))
        .andExpect(content().json(expected));
  }
  private void register(String body, int code) throws Exception {
    mvc.perform(MockMvcRequestBuilders
            .post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().is(code));
  };
}
