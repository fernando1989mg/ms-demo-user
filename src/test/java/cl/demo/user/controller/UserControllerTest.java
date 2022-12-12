package cl.demo.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    public UserControllerTest(MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }

    @Test
    public void testCreateSuccess(){

    }
}
