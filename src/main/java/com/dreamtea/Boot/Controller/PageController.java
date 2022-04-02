package com.dreamtea.Boot.Controller;

import com.dreamtea.Boot.Domain.User;
import com.dreamtea.Boot.Service.RedisService;
import com.dreamtea.Boot.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {
    @Autowired
    @Qualifier("userService")
    UserServiceImpl userService;

    @Autowired
    RedisService redisService;

    @GetMapping({"/","/login"})
    public String loginPage() {
        return "login";
    }

    @GetMapping("/roomselect")
    public String roomSelectPage() {
        return "roomselect";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam(name = "user_name", required = false) String userName, @RequestParam(name = "password", required = false) String password, HttpServletRequest request) {
        User user = userService.login(userName, password);
        if(user != null) {
            String token = (String) redisService.get("name-" + user.getUserName());
            request.getSession().setAttribute("token", token);
            return "redirect:/roomselect";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/chatroom")
    public String roomPage(@RequestParam("roomId") int roomId) {
        return "chatroom";
    }
}
