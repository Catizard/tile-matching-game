package com.dreamtea.Controller;

import com.dreamtea.Domain.World;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        int prevInhand = world.getInhand();
        int num = world.tryAddBlock(id);
        if(num == -1 || num != prevInhand) {
            return "[]";
        }
        return "[" + id + "," + num + "]";
    }
}
