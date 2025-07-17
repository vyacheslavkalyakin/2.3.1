package hiber.controller;

import hiber.model.User;
import hiber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String username, @RequestParam String password,
                          @RequestParam String email) {
        User user = new User(username, password, email);
        userService.addUser(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam int id) {
        userService.deleteUser((int) id);
        return "redirect:/users";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam int id, @RequestParam String username,
                           @RequestParam String password, @RequestParam String email) {
        User user = userService.getUserById((int) id);
        if (user != null) {
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            userService.updateUser(user);
        }
        return "redirect:/users";
    }
}