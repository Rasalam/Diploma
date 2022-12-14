package ru.netology.journey.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class RequestSQL {
    private static final String datasourceURL = System.getProperty("datasource.url");

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(datasourceURL, "app", "pass");
    }

    @SneakyThrows
    public static void clearDataBase() {
        QueryRunner runner = new QueryRunner();
        try (var connection = getConnection()) {
            runner.execute(connection, "DELETE FROM credit_request_entity");
            runner.execute(connection, "DELETE FROM order_entity");
            runner.execute(connection, "DELETE FROM payment_entity");
        }
    }

    @SneakyThrows
    public static String getPaymentEntityStatus() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConnection()) {
            var result = runner.query(connection, SqlStatus, new ScalarHandler<>());
            return (String) result;
        }
    }

    @SneakyThrows
    public static String getCreditRequestEntityStatus() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConnection()) {
            var result = runner.query(connection, SqlStatus, new ScalarHandler<>());
            return (String) result;
        }
    }

    @SneakyThrows
    public static long getAmountPaymentEntity() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT amount FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConnection()) {
            return Long.parseLong(runner.query(connection, SqlStatus, new ScalarHandler<>()).toString());
        }


    }
}
