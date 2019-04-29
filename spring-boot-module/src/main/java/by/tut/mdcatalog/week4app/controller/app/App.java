package by.tut.mdcatalog.week4app.controller.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "by.tut.mdcatalog.week4app")

public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
