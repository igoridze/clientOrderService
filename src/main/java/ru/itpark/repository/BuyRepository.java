package ru.itpark.repository;

import ru.itpark.domain.Buy;

import java.sql.*;

public class BuyRepository {

    private String url;

    public BuyRepository(String url) {
        this.url = url;
        orderInit();
    }

    private void orderInit() {

        try (Connection connection = DriverManager.getConnection(url)) {
            try (Statement statement =
                         connection.createStatement()) {

                statement.execute("CREATE TABLE IF NOT EXISTS buy (\n" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "clientId INTEGER NOT NULL ,\n" +
                        "orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL  ,\n" +
                        "orderSum INTEGER NOT NULL ,\n" +
                        "status TEXT NOT NULL,\n" +
                        "total INTEGER CHECK (total >=0) DEFAULT 0," +
                        "FOREIGN KEY (clientId) REFERENCES clients(id)\n" +
                        ");");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void add(Buy buy) {

        try (Connection connection = DriverManager.getConnection(url)) {
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 "INSERT INTO buy(id, clientId, orderDate, orderSum, status, total) VALUES (?,?,?,?,?,?);")) {

                statement.setInt(1, buy.getId());
                statement.setInt(2,buy.getClientId());
                statement.setString(3, buy.getOrderDate());
                statement.setInt(4, buy.getOrderSum());
                statement.setString(5, buy.getStatus());
                statement.setInt(6, buy.getTotal());

                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
