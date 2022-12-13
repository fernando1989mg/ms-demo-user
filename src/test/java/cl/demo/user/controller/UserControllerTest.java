package cl.demo.user.controller;

import cl.demo.user.domain.dto.UserDto;
import cl.demo.user.domain.exception.ExceptionResponseMessage;
import cl.demo.user.fixture.FixtureUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static cl.demo.user.util.JsonHelper.fromJson;
import static cl.demo.user.util.JsonHelper.toJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerTest(
            MockMvc mockMvc,
            ObjectMapper objectMapper){

        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testCreateSuccess() throws Exception {
        UserDto request = FixtureUserDto.createUserDto();

        MvcResult createResult = this.mockMvc
                .perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isCreated())
                .andReturn();

        UserDto userDto = fromJson(objectMapper, createResult.getResponse().getContentAsString(), UserDto.class);
        assertNotNull(userDto.getEmail(), "lol!");
        assertEquals(userDto.getName(), request.getName());
    }

    @Test
    public void testCreatePasswordError() throws Exception {
        UserDto request = FixtureUserDto.createInvalidPasswordUserDto();

        MvcResult createResult = this.mockMvc
                .perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ExceptionResponseMessage[] msgError = fromJson(objectMapper, createResult.getResponse().getContentAsString(), ExceptionResponseMessage[].class);
        ExceptionResponseMessage msgExpected = FixtureUserDto.createExceptionResponseMessage();

        assertNotNull(msgError[0].getTime());
        assertEquals(msgError[0].getError(), msgExpected.getError());
        assertEquals(msgError[0].getStatus(), msgExpected.getStatus());
        assertEquals(msgError[0].getMessage(), msgExpected.getMessage());
    }
}
