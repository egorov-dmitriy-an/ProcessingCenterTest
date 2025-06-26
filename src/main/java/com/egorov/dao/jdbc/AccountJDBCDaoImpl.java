package com.egorov.dao.jdbc;

import com.egorov.configuration.ConnectionManager;
import com.egorov.dao.Dao;
import com.egorov.dao.exeption.DaoException;
import com.egorov.model.Account;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountJDBCDaoImpl implements Dao<Long, Account> {
    private static final AccountJDBCDaoImpl INSTANCE = new AccountJDBCDaoImpl();
    private static final CurrencyJDBCDaoImpl currencyJdbcDao = CurrencyJDBCDaoImpl.getInstance();
    private static final IssuingBankJDBCDaoImpl issuingBankJDBCDao = IssuingBankJDBCDaoImpl.getInstance();

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS account (
                   id SERIAL PRIMARY KEY,
                    account_number VARCHAR(50),
                    balance DECIMAL,
                    currency_id BIGINT,
                    issuing_bank_id BIGINT,
                FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE SET NULL ON UPDATE CASCADE,
                FOREIGN KEY (issuing_bank_id) REFERENCES issuing_bank(id) ON DELETE SET NULL ON UPDATE CASCADE)
            """;

    private static final String DELETE_TABLE_SQL = """
            Drop TABLE IF EXISTS account;
            """;

    private static final String DELETE_ALL_SQL = """
            DELETE FROM account;
            """;

    private static final String UPDATE_SQL = """
            UPDATE account
            SET account_number = ?,
                balance = ?,
                currency_id = ?,
                issuing_bank_id = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ALL_SQL = """
            SELECT id,
                account_number,
                balance,
                currency_id,
                issuing_bank_id
            FROM account
            """;

    private static final String FIND_BY_ID_SQL = FIND_BY_ALL_SQL + """
             WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM account
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO account (
                account_number,
                balance,
                currency_id,
                issuing_bank_id )
            VALUES (?, ?, ?, ?)
            """;


    private AccountJDBCDaoImpl() {
    }

    public static AccountJDBCDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_SQL);
            System.out.println("Создана таблица аккаунтов");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_TABLE_SQL);
            System.out.println("Удалена таблица аккаунтов");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteAllFromTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement();) {
            statement.execute(DELETE_ALL_SQL);
            System.out.println("Удалены все аккаунты");

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Account account) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setBigDecimal(2, account.getBalance());
            preparedStatement.setLong(3, account.getCurrencyId().getId());
            preparedStatement.setLong(4, account.getIssuingBankId().getId());

            preparedStatement.executeUpdate();
            System.out.println("Обновлен аккаунт: " + account.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Account findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Account account = null;
            if (resultSet.next()) {
                account = buildAccount(resultSet);
            }
            return account;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Account> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ALL_SQL)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Account> accountsEntitiesList = new ArrayList<>();
            while (resultSet.next()) {
                accountsEntitiesList.add(buildAccount(resultSet));
            }
            return accountsEntitiesList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Account buildAccount(ResultSet resultSet) throws SQLException {
        return new Account(resultSet.getLong("id"), resultSet.getString("account_number"), resultSet.getBigDecimal("balance"), currencyJdbcDao.findById(resultSet.getLong("currency_id")), issuingBankJDBCDao.findById(resultSet.getLong("issuing_bank_id")));
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Удален аккаунт: " + id);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(Account account) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setBigDecimal(2, account.getBalance());
            preparedStatement.setLong(3, account.getCurrencyId().getId());
            preparedStatement.setLong(4, account.getIssuingBankId().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                account.setId(generatedKeys.getLong("id"));
            }
            System.out.println("Создан аккаунт " + account.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

