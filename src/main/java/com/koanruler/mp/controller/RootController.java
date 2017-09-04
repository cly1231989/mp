package com.koanruler.mp.controller;

import com.koanruler.mp.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hose on 2017/8/3 */
@Controller
public class RootController {

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", user.getName());
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
