package com.dreamtea.Controller;

import com.dreamtea.Domain.User;
import com.dreamtea.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {
    @Autowired
    @Qualifier("userService")
    UserServiceImpl userService;

    @RequestMapping("/game")
    public String gamePage() {
        return "game";
    }

    @GetMapping({"/","/login"})
    public String loginPage() {
        return "login";
    }

    @PostMapping("/refuseLogin")
    public String refuseLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam(name = "user_name", required = false) String userName,@RequestParam(name = "password", required = false) String password, HttpSession session) {
        User user = userService.login(userName, password);
        if(user != null) {
            //TODO change user's login state and automatically remove it when user closed the window
            session.setAttribute("user", user);
            return "redirect:/game";
        } else {
//            model.addAttribute("login_msg", "incorrect credentials");
            return "redirect:/login";
        }
    }
}
