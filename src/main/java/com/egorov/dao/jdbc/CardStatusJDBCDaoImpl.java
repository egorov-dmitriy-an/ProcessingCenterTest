package com.egorov.dao.jdbc;

import com.egorov.configuration.ConnectionManager;
import com.egorov.dao.Dao;
import com.egorov.dao.exeption.DaoException;
import com.egorov.model.CardStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardStatusJDBCDaoImpl implements Dao<Long, CardStatus> {
    private static final CardStatusJDBCDaoImpl INSTANCE = new CardStatusJDBCDaoImpl();

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS card_status (
                id SERIAL PRIMARY KEY,
                card_status_name VARCHAR(255))
            """;

    private static final String DELETE_TABLE_SQL = """
            Drop TABLE IF EXISTS card_status;
            """;

    private static final String DELETE_ALL_SQL = """
            DELETE FROM card_status
            """;

    private static final String UPDATE_SQL = """
            UPDATE card_status
            SET card_status_name = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ALL_SQL = """
            SELECT id,
                   card_status_name
            FROM card_status
            """;

    private static final String FIND_BY_ID_SQL = FIND_BY_ALL_SQL + """
             WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM card_status
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO card_status (
            card_status_name)
            VALUES (?)
            """;

    private CardStatusJDBCDaoImpl() {
    }

    public static CardStatusJDBCDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(CREATE_TABLE_SQL);
            System.out.println("Создана таблица статусов карт");
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
            System.out.println("Удалена таблица статусов карт");
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
            System.out.println("Удалены все статусы карт");

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(CardStatus cardStatus) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, cardStatus.getCardStatusName());
            preparedStatement.executeUpdate();
            System.out.println("Обновлен статус карты: " + cardStatus.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public CardStatus findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            CardStatus cardStatus = null;
            if (resultSet.next()) {
                cardStatus = buildCardStatus(resultSet);
            }
            return cardStatus;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<CardStatus> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CardStatus> cardsStatusEntitiesList = new ArrayList<>();
            while (resultSet.next()) {
                cardsStatusEntitiesList.add(buildCardStatus(resultSet));
            }
            return cardsStatusEntitiesList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static CardStatus buildCardStatus(ResultSet resultSet) throws SQLException {
        return new CardStatus(
                resultSet.getLong("id"),
                resultSet.getString("card_status_name"));
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Удален статус карты: " + id);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(CardStatus cardStatus) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, cardStatus.getCardStatusName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                cardStatus.setId(generatedKeys.getLong("id"));
            }
            System.out.println("Создан статус карты " + cardStatus.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}