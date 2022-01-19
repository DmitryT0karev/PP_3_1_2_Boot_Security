package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userServiceImpl;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, UserValidator userValidator) {
        this.userServiceImpl = userServiceImpl;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String showUsers(ModelMap model) {
        model.addAttribute("users", userServiceImpl.getAllUsers());
        return "users";
    }

    @GetMapping(value = "/addUser")
    public String addUser(Model model) {
        List<Role> listRoles = userServiceImpl.getAllRoles();
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", listRoles);
        return "newUser";
    }

    @PostMapping("/addUser")
    public String createUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "newUser";
        }
        userServiceImpl.save(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/editUser")
    public String editUser(@RequestParam("id") Long id, Model model) {
        List<Role> listRoles = userServiceImpl.getAllRoles();
        model.addAttribute("user", userServiceImpl.getUserById(id));
        model.addAttribute("listRoles", listRoles);
        return "editUser";
    }

    @PostMapping("/editUser")
    public String update(@RequestParam("id") Long id, @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editUser";
        }
        userServiceImpl.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin";
    }

}
