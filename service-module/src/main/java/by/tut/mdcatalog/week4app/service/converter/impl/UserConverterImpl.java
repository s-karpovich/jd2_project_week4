package by.tut.mdcatalog.week4app.service.converter.impl;

import by.tut.mdcatalog.week4app.repository.model.User;
import by.tut.mdcatalog.week4app.service.converter.UserConverter;
import by.tut.mdcatalog.week4app.service.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {
    @Override
    public User fromUserDTO(UserDTO UserDTO) {
        return new User(
                UserDTO.getId(),
                UserDTO.getUsername(),
                UserDTO.getPassword(),
                UserDTO.getRole(),
                UserDTO.getDeleted()
        );
    }

    @Override
    public UserDTO fromUser(User User) {
        return new UserDTO(
                User.getId(),
                User.getUsername(),
                User.getPassword(),
                User.getRole(),
                User.getDeleted()
        );
    }
}
