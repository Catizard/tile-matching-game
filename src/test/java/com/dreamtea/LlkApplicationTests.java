package com.dreamtea;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class LlkApplicationTests {

    @Test
    void contextLoads() throws JsonProcessingException {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(list));
    }

}
