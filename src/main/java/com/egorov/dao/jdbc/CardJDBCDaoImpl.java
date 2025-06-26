package com.egorov.dao.jdbc;

import com.egorov.configuration.ConnectionManager;
import com.egorov.dao.Dao;
import com.egorov.dao.exeption.DaoException;
import com.egorov.model.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardJDBCDaoImpl implements Dao<Long, Card> {
    private static final CardJDBCDaoImpl INSTANCE = new CardJDBCDaoImpl();
    private static final CardStatusJDBCDaoImpl cardStatusJDBCDao = CardStatusJDBCDaoImpl.getInstance();
    private static final AccountJDBCDaoImpl accountJDBCDao = AccountJDBCDaoImpl.getInstance();
    private static final PaymentSystemJDBCDaoImpl paymentSystemJDBCDao = PaymentSystemJDBCDaoImpl.getInstance();

    private static final String DELETE_SQL = """
            DELETE FROM card
            WHERE id = ?
            """;

    private static final String DELETE_ALL_SQL = """
            DELETE FROM card
            """;

    private static final String SAVE_SQL = """
            INSERT INTO card (
            card_number,
            expiration_date,
            holder_name,
            card_status_id,
            payment_system_id,
            account_id,
            received_from_issuing_bank,
            sent_to_issuing_bank)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE card
            SET card_number = ?,
                expiration_date = ?,
                holder_name = ?,
                card_status_id = ?,
                payment_system_id = ?,
                account_id = ?,
                received_from_issuing_bank = ?,
                sent_to_issuing_bank = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ALL_SQL = """
            SELECT id,
                   card_number,
                   expiration_date,
                   holder_name,
                   card_status_id,
                   payment_system_id,
                   account_id,
                   received_from_issuing_bank,
                   sent_to_issuing_bank
            FROM card
            """;

    private static final String FIND_BY_ID_SQL = FIND_BY_ALL_SQL + """
             WHERE id = ?
            """;

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS card (
                id SERIAL PRIMARY KEY,
                card_number VARCHAR(50),
                expiration_date DATE,
                holder_name VARCHAR(50),
                card_status_id BIGINT,
                payment_system_id BIGINT,
                account_id BIGINT,
                received_from_issuing_bank TIMESTAMP,
                sent_to_issuing_bank TIMESTAMP,
                FOREIGN KEY (card_status_id) REFERENCES card_status(id) ON DELETE SET NULL ON UPDATE CASCADE,
                FOREIGN KEY (payment_system_id) REFERENCES payment_system(id) ON DELETE SET NULL ON UPDATE CASCADE,
                FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE SET NULL ON UPDATE CASCADE);
            """;

    private static final String DELETE_TABLE_SQL = """
            Drop TABLE IF EXISTS card;
            """;

    public CardJDBCDaoImpl() {
    }

    public static CardJDBCDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(CREATE_TABLE_SQL);
            System.out.println("Создана таблица карт");
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
            System.out.println("Удалена таблица карт");
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
            System.out.println("Удалены все карты");

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Card card) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(card.getExpirationDate()));
            preparedStatement.setString(3, card.getHolderName());
            preparedStatement.setLong(4, card.getCardStatusId().getId());
            preparedStatement.setLong(5, card.getPaymentSystemId().getId());
            preparedStatement.setLong(6, card.getAccountId().getId());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(card.getReceivedFromIssuingBank()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(card.getSentToIssuingBank()));
            preparedStatement.setLong(9, card.getId());

            preparedStatement.executeUpdate();
            System.out.println("Обновлена карта: " + card.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Card findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Card card = null;
            if (resultSet.next()) {
                card = buildCard(resultSet);
            }
            return card;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Card> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Card> cardsEntitiesList = new ArrayList<>();
            while (resultSet.next()) {
                cardsEntitiesList.add(buildCard(resultSet));
            }
            return cardsEntitiesList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Удалена карта: " + id);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(Card card) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(card.getExpirationDate()));
            preparedStatement.setString(3, card.getHolderName());
            preparedStatement.setLong(4, card.getCardStatusId().getId());
            preparedStatement.setLong(5, card.getPaymentSystemId().getId());
            preparedStatement.setLong(6, card.getAccountId().getId());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(card.getReceivedFromIssuingBank()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(card.getSentToIssuingBank()));

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                card.setId(generatedKeys.getLong("id"));
            }
            System.out.println("Создана карта " + card.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Card buildCard(ResultSet resultSet) throws SQLException {
        return new Card(
                resultSet.getLong("id"),
                resultSet.getString("card_number"),
                resultSet.getDate("expiration_date").toLocalDate(),
                resultSet.getString("holder_name"),
                cardStatusJDBCDao.findById(resultSet.getLong("card_status_id")),
                paymentSystemJDBCDao.findById(resultSet.getLong("payment_system_id")),
                accountJDBCDao.findById(resultSet.getLong("account_id")),
                resultSet.getTimestamp("received_from_issuing_bank").toLocalDateTime(),
                resultSet.getTimestamp("received_from_issuing_bank").toLocalDateTime());
    }
}

