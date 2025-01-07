package mts.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;
import mts.dao.CostDAO;
import mts.models.Cost;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
public class CostController {

    private Stage primaryStage;

    @FXML
    private HBox datePickerBox; // Контейнер для DatePicker, используем для выбора даты
    @FXML
    private DatePicker dateField; // Один DatePicker для обеих целей
    // Поле для даты в форме добавления стоимости
    @FXML
    private TableView<Cost> costTable; // Таблица для отображения стоимости

    @FXML
    private TableColumn<Cost, Integer> idColumn; // Колонка ID
    @FXML
    private TableColumn<Cost, LocalDate> dateColumn; // Колонка даты
    @FXML
    private TableColumn<Cost, String> settlementNameColumn; // Колонка наименования населенного пункта
    @FXML
    private TableColumn<Cost, String> costPerMinColumn; // Колонка стоимости за минуту
    @FXML
    private TableColumn<Cost, String> preferentialCostColumn; // Колонка льготной стоимости

    @FXML
    private TextField settlementNameField; // Поле для наименования населенного пункта
    @FXML
    private TextField costPerMinField; // Поле для стоимости за минуту
    @FXML
    private TextField preferentialCostField; // Поле для льготной стоимости

    @FXML
    private VBox addCostForm; // Форма для добавления/редактирования стоимости

    private final CostDAO costDAO = new CostDAO();

    @FXML
    private void initialize() {
        // Привязка колонок к соответствующим свойствам объекта Cost
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        settlementNameColumn.setCellValueFactory(new PropertyValueFactory<>("settlementName"));
        costPerMinColumn.setCellValueFactory(new PropertyValueFactory<>("costPerMin"));
        preferentialCostColumn.setCellValueFactory(new PropertyValueFactory<>("preferentialCost"));

        loadCosts(); // Загружаем все стоимости в таблицу
    }

    private void loadCosts() {
        List<Cost> costs = costDAO.getAllCosts(); // Получаем все записи о стоимости из базы
        ObservableList<Cost> costList = FXCollections.observableArrayList(costs);
        costTable.setItems(costList); // Устанавливаем элементы в таблицу
    }

    @FXML
    private void addCost() {
        // Показываем форму для добавления стоимости
        addCostForm.setVisible(true);

        // Очищаем поля формы
        settlementNameField.clear();
        dateField.setValue(null);
        costPerMinField.clear();
        preferentialCostField.clear();

        // Убираем данные о текущей стоимости, так как это новая запись
        addCostForm.setUserData(null);
    }

    @FXML
    private void saveCost() {
        // Получаем данные из формы
        LocalDate date = dateField.getValue();  // Получаем выбранную дату из addDateField
        String settlementName = settlementNameField.getText();
        String costPerMinString = costPerMinField.getText();
        String preferentialCostString = preferentialCostField.getText();

        // Проверка на пустые поля
        if (costPerMinString.isEmpty() || preferentialCostString.isEmpty() || settlementName.isEmpty() || date == null) {
            showAlert("Ошибка", "Все поля должны быть заполнены и дата выбрана.");
            return;
        }

        // Преобразуем строки в BigDecimal
        BigDecimal costPerMin;
        BigDecimal preferentialCost;
        try {
            costPerMin = new BigDecimal(costPerMinString);
            preferentialCost = new BigDecimal(preferentialCostString);
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректный формат стоимости.");
            return;
        }

        // Создаем объект стоимости для сохранения
        Cost costToSave;
        if (addCostForm.getUserData() == null) {
            // Здесь дата используется при создании нового объекта
            costToSave = new Cost(0, date, settlementName, costPerMin, preferentialCost);
            costDAO.createCost(costToSave);
        } else {
            // Обновляем существующую стоимость
            costToSave = (Cost) addCostForm.getUserData();
            costToSave.setDate(date);
            costToSave.setSettlementName(settlementName);
            costToSave.setCostPerMin(costPerMin);
            costToSave.setPreferentialCost(preferentialCost);
            costDAO.updateCost(costToSave);
        }

        // Скрываем форму после сохранения
        addCostForm.setVisible(false);

        // Обновляем список стоимости
        loadCosts();

        // Показываем сообщение об успехе
        showAlert("Успех", "Данные стоимости сохранены.");
    }



    @FXML
    private void showDateField() {
        // Показываем поле выбора даты для просмотра стоимости
        datePickerBox.setVisible(true);
    }

    // Метод для обработки нажатия на кнопку "Показать"
    @FXML
    private void showCostByDate() {
        // Проверяем, выбрана ли дата
        if (dateField.getValue() == null) {
            showAlert("Ошибка", "Выберите дату.");
            return;
        }

        // Получаем выбранную дату
        LocalDate selectedDate = dateField.getValue();

        // Получаем стоимость за минуту для разных населённых пунктов на выбранную дату
        List<Cost> costs = getCostByDate(selectedDate);

        if (costs.isEmpty()) {
            showAlert("Информация", "Нет данных о стоимости на выбранную дату.");
            return;
        }

        // Отображаем данные в таблице
        ObservableList<Cost> costData = FXCollections.observableArrayList(costs);
        costTable.setItems(costData);
    }

    // Реализация метода для получения данных из базы данных через DAO
    private List<Cost> getCostByDate(LocalDate date) {
        return costDAO.getCostsByDate(date);
    }

    @FXML
    private void deleteCost() {
        // Получаем выбранную запись о стоимости
        Cost selectedCost = costTable.getSelectionModel().getSelectedItem();
        if (selectedCost != null) {
            costDAO.deleteCost(selectedCost.getId()); // Удаляем запись из базы данных
            loadCosts(); // Перезагружаем таблицу
        } else {
            showAlert("Ошибка", "Выберите запись о стоимости для удаления.");
        }
    }

    @FXML
    private void editCost() {
        // Получаем выбранную запись о стоимости
        Cost selectedCost = costTable.getSelectionModel().getSelectedItem();
        if (selectedCost != null) {
            // Открываем форму для редактирования
            addCostForm.setVisible(true);

            // Заполняем поля данными выбранной записи
            settlementNameField.setText(selectedCost.getSettlementName());
            dateField.setValue(selectedCost.getDate());
            costPerMinField.setText(String.valueOf(selectedCost.getCostPerMin()));
            preferentialCostField.setText(String.valueOf(selectedCost.getPreferentialCost()));

            // Устанавливаем объект стоимости для редактирования
            addCostForm.setUserData(selectedCost);
        } else {
            showAlert("Ошибка", "Выберите запись о стоимости для редактирования.");
        }
    }

    @FXML
    private void cancel() {
        addCostForm.setVisible(false); // Скрыть форму добавления/редактирования
    }

    private void showAlert(String title, String content) {
        // Оповещение об ошибке
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void goBackToMainMenu() {
        switchToMainMenu();
    }

    private void switchToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();
            mainController.setPrimaryStage(primaryStage);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Главное меню");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
