package by.tut.mdcatalog.week4app.service.impl;

import by.tut.mdcatalog.week4app.repository.UserRepository;
import by.tut.mdcatalog.week4app.repository.model.User;
import by.tut.mdcatalog.week4app.service.UserService;
import by.tut.mdcatalog.week4app.repository.connection.ConnectionHandler;
import by.tut.mdcatalog.week4app.service.converter.UserConverter;
import by.tut.mdcatalog.week4app.service.exceptions.ServiceException;
import by.tut.mdcatalog.week4app.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final ConnectionHandler connectionHandler;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(ConnectionHandler connectionHandler, UserRepository userRepository, UserConverter userConverter) {
        this.connectionHandler = connectionHandler;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserDTO> getUsers() {
        try (Connection connection = connectionHandler.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<User> Users = userRepository.getUsers(connection);
                List<UserDTO> convertedUsers = new ArrayList<>();
                Users.forEach(User -> convertedUsers.add(userConverter.fromUser(User)));
                connection.commit();
                return convertedUsers;
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException("Database request problem");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Database request problem");
        }
    }

    @Override
    public void add(UserDTO userDTO) {

        try (Connection connection = connectionHandler.getConnection()) {
            try {
                connection.setAutoCommit(false);
                User user = userConverter.fromUserDTO(userDTO);
                user.setDeleted(false);
                userRepository.add(connection, user);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException("Database request problem");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Database request problem");
        }
    }
}