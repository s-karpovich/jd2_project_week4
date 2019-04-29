package by.tut.mdcatalog.week4app.controller;

import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.API_403;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeAPIController {

    @GetMapping(API_403)
    public ResponseEntity forbidenCode() {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
