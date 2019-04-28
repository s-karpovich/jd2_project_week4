package by.tut.mdcatalog.week4app.repository;

import by.tut.mdcatalog.week4app.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository {
    List<User> getUsers(Connection connection);

    void add(Connection connection, User user);
}
