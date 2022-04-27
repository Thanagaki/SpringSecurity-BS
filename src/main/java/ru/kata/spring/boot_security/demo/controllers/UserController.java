package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }


    @GetMapping("/user/registry")
    public String registry(@ModelAttribute("newUser") User user) {
        return "registry-form";
    }

    @PostMapping()
    public String completeRegistry(@ModelAttribute("newUser") User user) {
        //Проверка правильности пароля
        //Проверка уникальности юзернейма
        // (без этой проверки просто
        // перекидывает обратно на "/admin" без
        //добавления
        if(!userService.saveUser(user)) {
            return "registry-form";
        }

        return "redirect:/user";
    }

    @GetMapping()
    public String userPage(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUserName(principal.getName()));
        return "user";
    }

}


