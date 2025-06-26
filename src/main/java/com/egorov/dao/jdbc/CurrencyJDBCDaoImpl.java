package com.egorov.dao.jdbc;

import com.egorov.configuration.ConnectionManager;
import com.egorov.dao.Dao;
import com.egorov.dao.exeption.DaoException;
import com.egorov.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyJDBCDaoImpl implements Dao<Long, Currency> {
    private static final CurrencyJDBCDaoImpl INSTANCE = new CurrencyJDBCDaoImpl();

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS currency (
                id SERIAL PRIMARY KEY,
                currency_digital_code VARCHAR(3),
                currency_letter_code VARCHAR(3),
                currency_name VARCHAR(255)
                )
            """;

    private static final String DELETE_TABLE_SQL = """
            Drop TABLE IF EXISTS currency;
            """;

    private static final String DELETE_ALL_SQL = """
            DELETE FROM currency
            """;

    private static final String UPDATE_SQL = """
            UPDATE currency
            SET currency_digital_code = ?,
                currency_letter_code = ?,
                currency_name = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ALL_SQL = """
            SELECT id,
                   currency_digital_code,
                   currency_letter_code,
                   currency_name
            FROM currency
            """;

    private static final String FIND_BY_ID_SQL = FIND_BY_ALL_SQL + """
             WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM currency
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO currency (
                currency_digital_code,
                currency_letter_code,
                currency_name)
            VALUES (?, ?, ?)
            """;


    private CurrencyJDBCDaoImpl() {
    }

    public static CurrencyJDBCDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(CREATE_TABLE_SQL);
            System.out.println("Создана таблица валют");
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
            System.out.println("Удалена таблица валют");
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
            System.out.println("Удалены все валюты");

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Currency currency) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, currency.getCurrencyDigitalCode());
            preparedStatement.setString(2, currency.getCurrencyLetterCode());
            preparedStatement.setString(3, currency.getCurrencyName());
            preparedStatement.executeUpdate();
            System.out.println("Обновлена валюта: " + currency.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Currency findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Currency currency = null;
            if (resultSet.next()) {
                currency = buildCurrency(resultSet);
            }
            return currency;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Currency> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ALL_SQL)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Currency> currencysEntitiesList = new ArrayList<>();
            while (resultSet.next()) {
                currencysEntitiesList.add(buildCurrency(resultSet));
            }
            return currencysEntitiesList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Currency buildCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getLong("id"),
                resultSet.getString("currency_digital_code"),
                resultSet.getString("currency_letter_code"),
                resultSet.getString("currency_name"));
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Удалена валюта: " + id);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(Currency currency) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, currency.getCurrencyDigitalCode());
            preparedStatement.setString(2, currency.getCurrencyLetterCode());
            preparedStatement.setString(3, currency.getCurrencyName());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                currency.setId(generatedKeys.getLong("id"));
            }
            System.out.println("Создана валюта " + currency.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
