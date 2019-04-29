package by.tut.mdcatalog.week4app.controller;

import by.tut.mdcatalog.week4app.controller.exceptions.ControllerException;
import by.tut.mdcatalog.week4app.service.UserService;
import by.tut.mdcatalog.week4app.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.API_USERS;
import javax.validation.Valid;

@RestController
public class UserAPIController {

    private static final Logger logger = LoggerFactory.getLogger(UserAPIController.class);

    private final UserService userService;

    @Autowired
    public UserAPIController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(API_USERS)
    public ResponseEntity saveUser(
            @RequestBody @Valid UserDTO userDTO
    ) {
        try {
            userService.add(userDTO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ControllerException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
