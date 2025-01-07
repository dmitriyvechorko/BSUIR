package mts.dao;

import lombok.SneakyThrows;
import mts.models.Call;
import mts.models.Client;
import mts.config.DatabaseConnection;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static mts.config.DatabaseConnection.getConnection;

public class CallDAO {

    private final Connection connection;

    @SneakyThrows
    public CallDAO() {
        connection = getConnection();
    }

    public List<Call> getAllCalls() {
        List<Call> calls = new ArrayList<>();
        String query = "SELECT ca.id, client_id, date_of_call, city_called_to, phone_number, call_duration " +
                "FROM call ca JOIN client cl ON ca.client_id = cl.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Call call = new Call(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getTimestamp("date_of_call").toLocalDateTime(),
                        rs.getString("city_called_to"),
                        rs.getString("phone_number"),
                        // Преобразование времени в Duration
                        (rs.getTime("call_duration")).toLocalTime()
                );
                calls.add(call);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calls;
    }

    // Получить звонок по id
    public Call getCallById(int callId) {
        Call call = null;
        String query = "SELECT ca.id, client_id, date_of_call, city_called_to, phone_number, call_duration " +
                "FROM call ca JOIN client cl ON ca.client_id = cl.id WHERE ca.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, callId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    call = new Call(
                            rs.getInt("id"),
                            rs.getInt("client_id"),
                            rs.getTimestamp("date_of_call").toLocalDateTime(),
                            rs.getString("city_called_to"),
                            rs.getString("phone_number"),
                            // Преобразование времени в Duration
                            (rs.getTime("call_duration")).toLocalTime()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return call;
    }

    // Добавить новый звонок
    public void createCall(Call call) {
        String query = "INSERT INTO call (client_id, date_of_call, city_called_to, phone_number, call_duration) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, call.getClientId());
            stmt.setTimestamp(2, Timestamp.valueOf(call.getDateOfCall()));
            stmt.setString(3, call.getCityCalledTo());
            stmt.setString(4, call.getPhoneNumber());
            // Преобразование Duration в Time для хранения в базе
            stmt.setTime(5, Time.valueOf((call.getCallDuration())));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSubscriberCountForCityAndMonth(String city, Month month) {
        int subscriberCount = 0;
        String query = "SELECT COUNT(DISTINCT client_id) " +
                "FROM Call " +
                "WHERE city_called_to = ? " +
                "AND EXTRACT(MONTH FROM date_of_call) = ? " +
                "AND EXTRACT(YEAR FROM date_of_call) = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Устанавливаем параметры запроса
            stmt.setString(1, city);
            stmt.setInt(2, month.getValue());;  // Используем month.getValue() для получения числового значения месяца
            stmt.setInt(3, LocalDate.now().getYear());  // Используем текущий год для EXTRACT(YEAR)

            // Выполняем запрос
            ResultSet rs = stmt.executeQuery();

            // Получаем результат
            if (rs.next()) {
                subscriberCount = rs.getInt(1);  // Получаем количество абонентов
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Логирование ошибки
        }

        return subscriberCount;
    }


    // Обновить звонок
    public void updateCall(Call call) {
        String query = "UPDATE call SET client_id = ?, date_of_call = ?, city_called_to = ?, phone_number = ?, call_duration = ? " +
                "WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, call.getClientId());
            stmt.setTimestamp(2, Timestamp.valueOf(call.getDateOfCall()));
            stmt.setString(3, call.getCityCalledTo());
            stmt.setString(4, call.getPhoneNumber());
            // Преобразование Duration в Time для хранения в базе
            stmt.setTime(5, Time.valueOf((call.getCallDuration())));
            stmt.setInt(6, call.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Удалить звонок
    public void deleteCall(int callId) {
        String query = "DELETE FROM call WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, callId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
