package by.tut.mdcatalog.week4app.service;

import by.tut.mdcatalog.week4app.service.model.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsers();

    void add(UserDTO user);
}
