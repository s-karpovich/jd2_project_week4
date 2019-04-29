package by.tut.mdcatalog.week4app.controller;

import by.tut.mdcatalog.week4app.service.UserService;
import by.tut.mdcatalog.week4app.service.converter.UserConverter;
import by.tut.mdcatalog.week4app.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserConverter.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String findAllUsers(Model model) {
        List<UserDTO> users = userService.getUsers();
        model.addAttribute("users", users);
        logger.debug("Got users method");
        return "users";
    }

    @PostMapping("/add")
    public String addUser(
            @Valid UserDTO userDTO,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "add";
        }
        userService.add(userDTO);
        logger.debug("Posted add method");
        return "redirect:/result";
    }
}



