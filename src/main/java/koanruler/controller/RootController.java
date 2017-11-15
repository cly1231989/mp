package koanruler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by hose on 2017/8/3 */
@Controller
public class RootController {

    @GetMapping("/")
    public String index() {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("username", user.getName());
        return "/index.html";
    }

//    @RequestMapping("/login")
//    public String login(){
//        return "login";
//    }
//
//    @RequestMapping("/patient/index")
//    public String patientIndex(){ return "patient/index"; }
}
