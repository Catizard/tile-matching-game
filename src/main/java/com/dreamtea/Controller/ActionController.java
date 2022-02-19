package com.dreamtea.Controller;

import com.dreamtea.Domain.World;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ActionController {

    @Autowired
    World world;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/getMap")
    public String getMap() throws JsonProcessingException {
        //TODO spilt first in this page and after
        ArrayList<Integer> map = world.genMap("test.txt");
        return objectMapper.writeValueAsString(map);
    }

    @GetMapping("/addBlock")
    public String addBlock(@RequestParam("blockId") int id) throws JsonProcessingException {
        System.out.print(id / 10);
        System.out.print(" " + id % 10);
        System.out.println("");
        ArrayList<Integer> result = world.addBlock(id);
        String resultJSON = objectMapper.writeValueAsString(result);
        System.out.println(resultJSON);
        if(result.size() == 2) {
            world.clearStk();
        }
        return resultJSON;
    }
}
