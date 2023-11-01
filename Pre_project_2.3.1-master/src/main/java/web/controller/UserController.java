package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "allUsers";
    }

    @GetMapping("/new")
    public String addUser(@ModelAttribute("user") User user) {
        return "addUser";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addUser";
        }

        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editUserById(@RequestParam("id") int id, Model model) {
        model.addAttribute(userService.getUserById(id));
        return "editUser";
    }

    @PostMapping("/user")
    public String saveEdit(@RequestParam("id") int id,
                           @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editUser";
        }

        userService.updateUser(user, id);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.removeUserById(id);
        return "redirect:/";
    }

}
