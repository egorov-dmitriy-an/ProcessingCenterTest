package com.egorov.dao.jdbc;

import com.egorov.configuration.ConnectionManager;
import com.egorov.dao.Dao;
import com.egorov.dao.exeption.DaoException;
import com.egorov.model.PaymentSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentSystemJDBCDaoImpl implements Dao <Long, PaymentSystem> {
    private static final PaymentSystemJDBCDaoImpl INSTANCE = new PaymentSystemJDBCDaoImpl();

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS payment_system (
                id SERIAL PRIMARY KEY,
                payment_system_name VARCHAR(50))
            """;

    private static final String DELETE_TABLE_SQL = """
            Drop TABLE IF EXISTS payment_system;
            """;

    private static final String DELETE_ALL_SQL = """
            DELETE FROM payment_system
            """;

    private static final String UPDATE_SQL = """
            UPDATE payment_system
            SET payment_system_name = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ALL_SQL = """
            SELECT id,
                   payment_system_name
            FROM payment_system
            """;

    private static final String FIND_BY_ID_SQL = FIND_BY_ALL_SQL + """
             WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM payment_system
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO payment_system (
            payment_system_name)
            VALUES (?)
            """;

    private PaymentSystemJDBCDaoImpl() {
    }

    public static PaymentSystemJDBCDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(CREATE_TABLE_SQL);
            System.out.println("Создана таблица платежных систем");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE_TABLE_SQL);
            System.out.println("Удалена таблица платежных систем");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteAllFromTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement();
        ) {
            statement.execute(DELETE_ALL_SQL);
            System.out.println("Удалены все платежные системы");

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(PaymentSystem paymentSystem) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, paymentSystem.getPaymentSystemName());
            preparedStatement.executeUpdate();
            System.out.println("Обновлена платежная система: " + paymentSystem.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public PaymentSystem findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            PaymentSystem paymentSystem = null;
            if (resultSet.next()) {
                paymentSystem = buildPaymentSystem(resultSet);
            }
            return paymentSystem;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<PaymentSystem> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PaymentSystem> paymentSystemsEntitiesList = new ArrayList<>();
            while (resultSet.next()) {
                paymentSystemsEntitiesList.add(buildPaymentSystem(resultSet));
            }
            return paymentSystemsEntitiesList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static PaymentSystem buildPaymentSystem(ResultSet resultSet) throws SQLException {
        return new PaymentSystem(
                resultSet.getLong("id"),
                resultSet.getString("payment_system_name"));
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Удалена платежная система: " + id);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(PaymentSystem paymentSystem) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, paymentSystem.getPaymentSystemName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                paymentSystem.setId(generatedKeys.getLong("id"));
            }
            System.out.println("Создана платежная система " + paymentSystem.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
