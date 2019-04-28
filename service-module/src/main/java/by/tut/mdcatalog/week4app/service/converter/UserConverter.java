package by.tut.mdcatalog.week4app.service.converter;

import by.tut.mdcatalog.week4app.repository.model.User;
import by.tut.mdcatalog.week4app.service.model.UserDTO;

public interface UserConverter {

    User fromUserDTO(UserDTO userDTO);

    UserDTO fromUser(User user);
}
