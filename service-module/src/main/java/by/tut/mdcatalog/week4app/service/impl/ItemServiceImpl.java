package by.tut.mdcatalog.week4app.service.impl;

import by.tut.mdcatalog.week4app.repository.ItemRepository;
import by.tut.mdcatalog.week4app.repository.model.Item;
import by.tut.mdcatalog.week4app.service.ItemService;
import by.tut.mdcatalog.week4app.repository.connection.ConnectionHandler;
import by.tut.mdcatalog.week4app.service.converter.ItemConverter;
import by.tut.mdcatalog.week4app.service.exceptions.ServiceException;
import by.tut.mdcatalog.week4app.service.model.ItemDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private final ConnectionHandler connectionHandler;
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    @Autowired
    public ItemServiceImpl(ConnectionHandler connectionHandler,
                           ItemRepository itemRepository,
                           ItemConverter itemConverter) {
        this.connectionHandler = connectionHandler;
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    public List<ItemDTO> getItems() {
        try (Connection connection = connectionHandler.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<Item> items = itemRepository.getItems(connection);
                List<ItemDTO> convertedItems = new ArrayList<>();
                items.forEach(item -> convertedItems.add(itemConverter.fromItem(item)));
                connection.commit();
                return convertedItems;
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
