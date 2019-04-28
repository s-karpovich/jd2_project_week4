package by.tut.mdcatalog.week4app.repository.connection;

import by.tut.mdcatalog.week4app.repository.exception.DatabaseConnectionException;
import by.tut.mdcatalog.week4app.repository.exception.FileNotFoundException;

import by.tut.mdcatalog.week4app.repository.properties.DataBaseProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class ConnectionHandler {
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);
    private final DataBaseProperties dataBaseProperties;

    public ConnectionHandler(DataBaseProperties dataBaseProperties) {
        try {
            Class.forName(dataBaseProperties.getDriver());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException("Database connection problem", e);
        }
        this.dataBaseProperties = dataBaseProperties;
    }

    public Connection getConnection() {
        Properties properties = new Properties();
        properties.setProperty("user", dataBaseProperties.getUsername());
        properties.setProperty("password", dataBaseProperties.getPassword());
        try {
            return DriverManager.getConnection(dataBaseProperties.getUrl(), properties);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException("Database connection problem", e);
        }
    }

    @PostConstruct
    public void createCapacityTables() {
        String tablesFileName = this.getClass().getResource(dataBaseProperties.getTablesFile()).getPath();
        List<String> listQueries = new ArrayList<>();
        readFileQueries(tablesFileName, listQueries);
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                for (String query : listQueries) {
                    statement.executeUpdate(query);
                }
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new DatabaseConnectionException("Database connection problem", e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException("Database connection problem", e);
        }
    }

    private void readFileQueries(String fileName, List<String> listQueries) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                listQueries.add(line);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new FileNotFoundException("File not found", e);
        }
    }
}
