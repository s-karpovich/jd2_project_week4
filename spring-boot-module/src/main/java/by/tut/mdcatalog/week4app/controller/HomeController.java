package by.tut.mdcatalog.week4app.controller;

import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.ABOUTUS_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.HOME_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.ERROR_403_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.LOGIN_PAGE;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("homeController")
public class HomeController {

    @GetMapping(value = {HOME_PAGE, ABOUTUS_PAGE})
    public String getHomepage() {
        return "aboutus";
    }

    @GetMapping(LOGIN_PAGE)
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(ERROR_403_PAGE)
    public String getErrorPage() {
        return "errors/403";
    }
}
