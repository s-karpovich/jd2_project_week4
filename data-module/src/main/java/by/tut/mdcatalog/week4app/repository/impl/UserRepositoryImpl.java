package by.tut.mdcatalog.week4app.repository.impl;

import by.tut.mdcatalog.week4app.repository.UserRepository;
import by.tut.mdcatalog.week4app.repository.exception.DatabaseConnectionException;
import by.tut.mdcatalog.week4app.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(ItemRepositoryImpl.class);

    @Override
    public List<User> getUsers(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM t_user";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(getUser(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException("Database connection error", e);
        }
    }

    @Override
    public void add(Connection connection, User user) {
        String query = "INSERT INTO t_user (F_USERNAME, F_PASSWORD, F_ROLE, F_DELETED) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().toString());
            ps.setBoolean(4, user.getDeleted());
            int countItemsAdded = ps.executeUpdate();
            logger.info("Users added: {}", countItemsAdded);
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException("Database connection error", e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("F_ID"),
                resultSet.getString("F_USERNAME"),
                resultSet.getString("F_PASSWORD"),
                resultSet.getString("F_ROLE"),
                resultSet.getBoolean("F_DELETED")
        );
    }
}
