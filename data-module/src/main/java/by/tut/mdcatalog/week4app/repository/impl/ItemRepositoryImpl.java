package by.tut.mdcatalog.week4app.repository.impl;

import by.tut.mdcatalog.week4app.repository.model.ItemStatusEnum;
import by.tut.mdcatalog.week4app.repository.model.Item;
import by.tut.mdcatalog.week4app.repository.ItemRepository;
import by.tut.mdcatalog.week4app.repository.exception.DatabaseConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(ItemRepositoryImpl.class);

    @Override
    public List<Item> getItems(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM t_item";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    items.add(getItem(resultSet));
                }
                return items;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException("Database connection error", e);
        }
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getLong("F_ID"),
                resultSet.getString("F_NAME"),
                ItemStatusEnum.valueOf(resultSet.getString("F_STATUS")),
                resultSet.getBoolean("F_DELETED")
        );
    }
}
