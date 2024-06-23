package com.example.soporte;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(classes = SoporteApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SoporteApplicationTests{

    @Test
    void contextLoads() {
    }

}