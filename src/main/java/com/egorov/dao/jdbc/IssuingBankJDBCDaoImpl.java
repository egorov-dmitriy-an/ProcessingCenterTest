package com.egorov.dao.jdbc;

import com.egorov.configuration.ConnectionManager;
import com.egorov.dao.Dao;
import com.egorov.dao.exeption.DaoException;
import com.egorov.model.CardStatus;
import com.egorov.model.IssuingBank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssuingBankJDBCDaoImpl implements Dao<Long, IssuingBank> {
    private static final IssuingBankJDBCDaoImpl INSTANCE = new IssuingBankJDBCDaoImpl();

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS issuing_bank (
                id SERIAL PRIMARY KEY,
                bic VARCHAR(9),
                bin VARCHAR(5),
                abbreviated_name VARCHAR(255))
            """;

    private static final String DELETE_TABLE_SQL = """
            Drop TABLE IF EXISTS issuing_bank;
            """;

    private static final String DELETE_ALL_SQL = """
            DELETE FROM issuing_bank
            """;

    private static final String UPDATE_SQL = """
            UPDATE issuing_bank
            SET bic = ?,
                bin = ?,
                abbreviated_name = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ALL_SQL = """
            SELECT id,
                   bic,
                   bin,
                   abbreviated_name
            FROM issuing_bank
            """;

    private static final String FIND_BY_ID_SQL = FIND_BY_ALL_SQL + """
             WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM issuing_bank
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO issuing_bank (
            bic, bin, abbreviated_name)
            VALUES (?, ?, ?)
            """;



    public IssuingBankJDBCDaoImpl() {
    }



    public static IssuingBankJDBCDaoImpl getInstance() {
        return INSTANCE;
    }


    @Override
    public void createTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(CREATE_TABLE_SQL);
            System.out.println("Создана таблица банков-эмитентов");
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
            System.out.println("Удалена таблица банков-эмитентов");
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
            System.out.println("Удалены все банки-эмитенты");

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(IssuingBank issuingBank) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, issuingBank.getBic());
            preparedStatement.setString(2, issuingBank.getBin());
            preparedStatement.setString(3, issuingBank.getAbbreviatedName());
            preparedStatement.executeUpdate();
            System.out.println("Обновлен банк-эмитент: " + issuingBank.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public IssuingBank findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            IssuingBank issuingBank = null;
            if (resultSet.next()) {
                issuingBank = buildIssuingBank(resultSet);
            }
            return issuingBank;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<IssuingBank> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<IssuingBank> issuingBankEntitiesList = new ArrayList<>();
            while (resultSet.next()) {
                issuingBankEntitiesList.add(buildIssuingBank(resultSet));
            }
            return issuingBankEntitiesList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static IssuingBank buildIssuingBank(ResultSet resultSet) throws SQLException {
        return new IssuingBank(
                resultSet.getLong("id"),
                resultSet.getString("bic"),
                resultSet.getString("bin"),
                resultSet.getString("abbreviated_name"));
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Удален банк-эмитент: " + id);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(IssuingBank issuingBank) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, issuingBank.getBic());
            preparedStatement.setString(2, issuingBank.getBin());
            preparedStatement.setString(3, issuingBank.getAbbreviatedName());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                issuingBank.setId(generatedKeys.getLong("id"));
            }
            System.out.println("Создан банк-эмитент " + issuingBank.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
