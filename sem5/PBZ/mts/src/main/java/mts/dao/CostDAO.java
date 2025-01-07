package mts.dao;

import mts.config.DatabaseConnection;
import mts.models.Cost;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static mts.config.DatabaseConnection.getConnection;

public class CostDAO {

    // Метод для получения всех записей из таблицы Cost
    public List<Cost> getAllCosts() {
        List<Cost> costs = new ArrayList<>();
        String query = "SELECT * FROM cost";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Cost cost = new Cost(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("settlement_name"),
                        resultSet.getBigDecimal("cost_per_min"),
                        resultSet.getBigDecimal("preferential_cost")
                );
                costs.add(cost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return costs;
    }

    public Cost getCostByCity(String city) {
        String query = "SELECT * FROM Cost WHERE settlement_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, city);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Если запись найдена, создаем объект Cost и заполняем его
                Cost cost = new Cost();
                cost.setId(resultSet.getInt("id"));
                cost.setSettlementName(resultSet.getString("settlement_name"));
                cost.setCostPerMin(resultSet.getBigDecimal("regular_cost"));
                cost.setPreferentialCost(resultSet.getBigDecimal("discounted_cost"));
                return cost;
            } else {
                // Если записи не найдены, можно вернуть null или выбросить исключение
                return null; // Или выбрасываем исключение, если город не найден
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cost> getCostsByDate(LocalDate date) {
        List<Cost> costs = new ArrayList<>();
        String query = "SELECT * FROM Cost WHERE date = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {


            // Устанавливаем дату в запрос
            stmt.setDate(1, Date.valueOf(date));

            // Выполняем запрос и обрабатываем результат
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cost cost = new Cost(
                            rs.getInt("id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getString("settlement_name"),
                            rs.getBigDecimal("cost_per_min"),
                            rs.getBigDecimal("preferential_cost")
                    );
                    costs.add(cost);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return costs;
    }

    // Метод для получения записи по ID
    public Cost getCostById(int costId) {
        String query = "SELECT * FROM cost WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, costId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Cost(
                        resultSet.getInt("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("settlement_name"),
                        resultSet.getBigDecimal("cost_per_min"),
                        resultSet.getBigDecimal("preferential_cost")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Метод для добавления новой записи о стоимости
    public void createCost(Cost cost) throws IllegalArgumentException {
        String query = "INSERT INTO Cost (date, settlement_name, cost_per_min, preferential_cost) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Проверяем, что дата не равна null перед тем, как передавать в запрос
            if (cost.getDate() == null) {
                throw new IllegalArgumentException("Дата не может быть null");
            }

            stmt.setDate(1, Date.valueOf(cost.getDate()));  // Преобразуем LocalDate в java.sql.Date
            stmt.setString(2, cost.getSettlementName());
            stmt.setBigDecimal(3, cost.getCostPerMin());
            stmt.setBigDecimal(4, cost.getPreferentialCost());

            stmt.executeUpdate();

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();  // Можно заменить на более специфичную обработку ошибок
        }
    }

    // Метод для обновления существующей записи о стоимости
    public void updateCost(Cost cost) {
        String query = "UPDATE cost SET date = ?, settlement_name = ?, cost_per_min = ?, preferential_cost = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, Date.valueOf(cost.getDate()));
            preparedStatement.setString(2, cost.getSettlementName());
            preparedStatement.setBigDecimal(3, cost.getCostPerMin());
            preparedStatement.setBigDecimal(4, cost.getPreferentialCost());
            preparedStatement.setInt(5, cost.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для удаления записи по ID
    public void deleteCost(int costId) {
        String query = "DELETE FROM cost WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, costId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}