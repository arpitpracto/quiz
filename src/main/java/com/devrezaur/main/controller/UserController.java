package com.devrezaur.main.controller;

import com.devrezaur.main.model.User;
import com.devrezaur.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {


    @GetMapping("/userDashboard")
    public String adminLogin() {

        return "userDashboard";
    }
    @GetMapping("/testDetails")
    public String testDetails(){
        return  "testDetails";
    }



}
