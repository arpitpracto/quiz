package com.devrezaur.main.controller;

import com.devrezaur.main.model.*;
import com.devrezaur.main.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TestController {

    @Autowired  // Injects the repository
    private QuestionRepo qRepo;
    @Autowired
    private TestRepo testRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private  ResponseRepo userResponseRepo;
    @Autowired
    private ResultRepo rRepo;

    @GetMapping("/createTest")
    public String createTest(Model model) {
        List<Question> questions = qRepo.findAll();  // Fetch all available questions
        model.addAttribute("questions", questions);
        return "createTest";  // Ensure createTest.html exists in src/main/resources/templates/
    }


    @PostMapping("/saveTest")
    public String saveTest(
            @RequestParam("testName") String testName,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam("selectedQuestionIds") List<String> selectedQuestionIds,
            Model model) {
            System.out.println(("Inside the test controller"));

        // Convert string times to LocalDateTime

        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);

        // Fetch selected questions
        List<Integer> questionIds = selectedQuestionIds.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        Set<Question> selectedQuestions = new HashSet<>(qRepo.findAllById(questionIds));

        // Create and save the test
        Test test = new Test(testName, start, end);
        test.setQuestions(selectedQuestions);
//        Set<User> users = new HashSet<>(userRepo.findAll());
        testRepo.save(test);

        model.addAttribute("message", "Test created successfully!");
        return  "loginPage";

    }
    @GetMapping("/testPage")
    public String showTestPage(@RequestParam Integer testId, @RequestParam Integer userId, Model model) {
        System.out.println("inside the Test COntroller");
        Test test = testRepo.findById(testId).orElse(null);

        if (test == null) {
            return "error"; // Handle error
        }

        List<Question> questions = qRepo.findQuestionsByTestId(testId);

        model.addAttribute("testId", testId);
        model.addAttribute("userId", userId);
        model.addAttribute("testName", test.getTestName());
        model.addAttribute("questions", questions);

        return "testPage";
    }




    @PostMapping("/saveUserResponse")
    public String saveUserResponse(@RequestParam Integer testId,
                                   @RequestParam Long userId,
                                   HttpServletRequest request,
                                   Model model) {
        System.out.println("Saving user response...");

        // Fetch user and test from DB
        Optional<User> userOpt = userRepo.findById(userId);
        Optional<Test> testOpt = testRepo.findById(testId);

        if (!userOpt.isPresent() || !testOpt.isPresent()) {
            return "error";  // Redirect to an error page if data is missing
        }

        User user = userOpt.get();
        Test test = testOpt.get();

        int correctAnswers = 0;
        int totalQuestions = 0;

        // Process user responses
        for (String paramName : request.getParameterMap().keySet()) {
            if (paramName.startsWith("answer-")) {
                Integer questionId = Integer.parseInt(paramName.replaceAll("\\D+", ""));
                String selectedAnswer = request.getParameter(paramName);

                Optional<Question> questionOpt = qRepo.findById(questionId);
                if (questionOpt.isPresent()) {
                    Question question = questionOpt.get();
                    totalQuestions++;

                    // Check correctness
                    if (selectedAnswer.equals(question.getCorrectAnswer())) {
                        correctAnswers++;
                    }

                    // Save response
                    Response response = new Response(test, question, user, selectedAnswer);
                    userResponseRepo.save(response);
                }
            }
        }

        // Save test result
        Result result = new Result(test, user, correctAnswers, totalQuestions);
        rRepo.save(result);

        // Add attributes to the model
        model.addAttribute("test", test);
        model.addAttribute("user", user);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("scorePercentage", result.getScorePercentage());

        return "viewResult";  // Return the result page
    }

}













