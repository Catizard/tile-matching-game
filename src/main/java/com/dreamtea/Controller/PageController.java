package com.dreamtea.Controller;

import com.dreamtea.Domain.User;
import com.dreamtea.Service.RedisService;
import com.dreamtea.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PageController {
    @Autowired
    @Qualifier("userService")
    UserServiceImpl userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/game")
    public String gamePage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getSession().getId());
        return "game";
    }

    @GetMapping({"/","/login"})
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam(name = "user_name", required = false) String userName, @RequestParam(name = "password", required = false) String password, HttpServletRequest request , HttpServletResponse response) {
        User user = userService.login(userName, password);
        if(user != null) {
            //TODO change user's login state and automatically remove it when user closed the window
            String token = (String) redisService.get("name-" + user.getUserName());
            if(token == null) {
                return "redirect:/login";
            }
            /*
            TODO
            此处有一个比较严重的问题:
            场景:
                一个浏览器同时开启多个页面,此时由于cookie的共享性质会导致多个页面可以同时登陆同一个帐号
            我不知道怎么解决,我觉得不能使用cookie/session作为共享
             */
            Cookie cookie_token = new Cookie("token", token);
            cookie_token.setMaxAge(30 * 24 * 60 * 60);
            cookie_token.setPath(request.getContextPath());
            response.addCookie(cookie_token);
            return "redirect:/game";
        } else {
//            model.addAttribute("login_msg", "incorrect credentials");
            return "redirect:/login";
        }
    }
}
