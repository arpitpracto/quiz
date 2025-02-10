package com.devrezaur.main.controller;

import com.devrezaur.main.model.Invite;
import com.devrezaur.main.model.Test;
import com.devrezaur.main.model.User;
import com.devrezaur.main.repository.InviteRepo;
import com.devrezaur.main.repository.TestRepo;
import com.devrezaur.main.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TestAssignmentController {

    @Autowired
    private TestRepo testRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private InviteRepo inviteRepo;

    @GetMapping("/inviteUser")
    public String showAssignTestPage(@RequestParam(required = false) Integer testId, Model model) {
        List<Test> tests = testRepo.findAll();
        model.addAttribute("tests", tests);

        if (testId != null) {
            Test test = testRepo.findById(testId).orElse(null);
            if (test != null) {
                // Get assigned users from Invite table
                List<Invite> invites = inviteRepo.findByTest(test);
                Set<User> assignedUsers = invites.stream().map(Invite::getUser).collect(Collectors.toSet());

                // Get available users
                List<User> availableUsers = userRepo.findByUserRole("user")
                        .stream()
                        .filter(user -> !assignedUsers.contains(user))
                        .collect(Collectors.toList());

                model.addAttribute("assignedUsers", assignedUsers);
                model.addAttribute("users", availableUsers);
                model.addAttribute("selectedTest", test);
            }
        } else {
            model.addAttribute("users", userRepo.findByUserRole("users"));
        }

        return "assignTest";
    }
    @PostMapping("/assignTest")
    public String assignTest(@RequestParam Integer testId, @RequestParam List<Integer> userIds) {
        Test test = testRepo.findById(testId).orElse(null);
        if (test != null) {
            // ✅ Convert userIds to List<Long>
            List<Long> userIdsLong = userIds.stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            List<User> users = userRepo.findAllById(userIdsLong);

            for (User user : users) {
                if (!inviteRepo.existsByTestAndUser(test, user)) {
                    inviteRepo.save(new Invite(test, user));
                }
            }
        }

        return "redirect:/inviteUser?testId=" + testId;
    }
    @GetMapping("/exitPage")
    public String exitPage(){
        return  "exitPage";
    }


}
