package com.dreamtea.Controller;

import com.dreamtea.Domain.World;
import com.dreamtea.Service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
public class ActionController {
    @Autowired
    RedisService redisService;

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

    @GetMapping("/getToken")
    public String getToken(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("token");
    }

    @GetMapping("/testQuery")
    public String testQuery(@RequestParam("remoteToken") String remoteToken) {
        /*
        TODO
        增加一个拦截器拦截登录
        由于房间列表页面和游戏页面还需要拆分,暂时放在这里
         */
        System.out.println(remoteToken);
        String getName = (String) redisService.get(remoteToken);
        System.out.println(getName);
        if(getName != null) {
            System.out.println(redisService.get(getName));
        }
        return "";
    }
}
