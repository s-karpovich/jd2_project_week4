package by.tut.mdcatalog.week4app.repository;

import by.tut.mdcatalog.week4app.repository.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository {
    List<Item> getItems(Connection connection);
}
