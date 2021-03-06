package ru.kata.spring.boot_security.demo.controllers;

import org.hibernate.loader.collection.PaddedBatchingCollectionInitializerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currentUser", userService.findByUserName(principal.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "bs-admin-page";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("newRoles") String [] roles) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleService.getByName(roles));
        userService.saveUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/delete/user/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }


    @PostMapping("/edit/user/{id}")
    // ?????????? ???????????????? ????????
    public String update(User user, @RequestParam("userRoles") String[] roles) {
        if(roles != null) {
            user.setRoles(roleService.getByName(roles));
        } else{
            user.setRoles(userService.getById(user.getId()).getRoles());
        }
        //???????? ???? ??????????????????, ???? ???????????? ?????? ???? ?????????? ???????????????????????????? ???????????? ?????????? ???????????? ????????????, ?????????????? ??????????
        // ?? ???????????????? ??????
        if ( user.getPassword().equals("")) {
            user.setPassword(userService.getById(user.getId()).getPassword());

        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.edit(user);
        return "redirect:/admin";

    }
}

