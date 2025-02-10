package com.devrezaur.main.controller;

import com.devrezaur.main.model.Invite;
import com.devrezaur.main.model.Test;
import com.devrezaur.main.model.User;
import com.devrezaur.main.repository.InviteRepo;
import com.devrezaur.main.repository.TestRepo;
import com.devrezaur.main.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.Optional;

@Controller
public class InviteController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TestRepo testRepo;

    @Autowired
    private InviteRepo inviteRepo;
    @PostMapping("/validateDetails")
    public String validateTest(@RequestParam String username, @RequestParam String testname, Model model) {
        System.out.println("Inside InviteController - validateTest()");

        // Find User by username
        Optional<User> userOpt = userRepo.findByUsername(username);
        if (!userOpt.isPresent()) {
            System.out.println("User not found: " + username);
            model.addAttribute("error", "User not found.");
            return "index";
        }
        User user = userOpt.get();

        // Find Test by testName
        Optional<Test> testOpt = testRepo.findByTestName(testname);
        if (!testOpt.isPresent()) {
            System.out.println("Test not found: " + testname);
            model.addAttribute("error", "Test not found.");
            return "index";
        }
        Test test = testOpt.get();

        // Check if user is invited
        boolean isInvited = inviteRepo.existsByTestAndUser(test, user);
        System.out.println("User invited status: " + isInvited);

        if (isInvited) {
            System.out.println("Redirecting to /testPage with testId: " + test.getTestId() + " and userId: " + user.getUserId());
            return "redirect:/testPage?testId=" + test.getTestId() + "&userId=" + user.getUserId();
        } else {
            System.out.println("User is not invited.");
            model.addAttribute("error", "You are not invited to this test.");
            return "index";
        }
    }

}
