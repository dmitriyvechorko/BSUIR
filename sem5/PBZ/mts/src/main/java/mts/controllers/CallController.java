package mts.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;
import mts.dao.ClientDAO;
import mts.dao.CallDAO;
import mts.models.Client;
import mts.models.Call;

import java.io.IOException;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.List;

@Setter
public class CallController {

    private Stage primaryStage;

    @FXML
    private TextField cityForCalculationField; // Поле для ввода города
    @FXML
    private ComboBox<String> monthForCalculationComboBox; // ComboBox для выбора месяца
    @FXML
    private Label subscriberCountLabel; // Метка для отображения количества абонентов
    @FXML
    private VBox subscriberCalculationForm;
    @FXML
    private TableView<Call> callTable; // Таблица звонков
    @FXML
    private TableColumn<Call, Integer> idColumn; // Колонка ID звонка
    @FXML
    private TableColumn<Call, String> cityCalledToColumn; // Колонка города вызова
    @FXML
    private TableColumn<Call, String> phoneNumberColumn; // Колонка номера телефона
    @FXML
    private TableColumn<Call, LocalDateTime> dateOfCallColumn; // Колонка даты вызова
    @FXML
    private TableColumn<Call, String> callDurationColumn; // Колонка длительности вызова
    @FXML
    private ComboBox<Client> clientComboBox; // Комбо-бокс для выбора клиента
    @FXML
    private TextField cityCalledToField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private DatePicker dateOfCallField;
    @FXML
    private TextField timeOfCallField;
    @FXML
    private TextField callDurationField;
    @FXML
    private VBox addCallForm;

    private final ClientDAO clientDAO = new ClientDAO();
    private final CallDAO callDAO = new CallDAO();

    @FXML
    private void initialize() {
        // Привязка колонок к соответствующим свойствам объекта Call
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cityCalledToColumn.setCellValueFactory(new PropertyValueFactory<>("cityCalledTo"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        dateOfCallColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfCall"));
        callDurationColumn.setCellValueFactory(new PropertyValueFactory<>("callDuration"));
        loadClientsForComboBox();
        // Загружаем все звонки в таблицу
        loadCalls();
    }


    @FXML
    private void showSubscriberCalculationForm() {
        // Отображаем форму для выбора города и месяца
        subscriberCalculationForm.setVisible(true);
    }

    @FXML
    private void calculateSubscriberCount() {
        // Получаем город и месяц из формы
        String city = cityForCalculationField.getText();
        String monthString = monthForCalculationComboBox.getValue();

        // Проверка на пустые значения
        if (city.isEmpty() || monthString == null) {
            showAlert("Ошибка", "Пожалуйста, укажите город и месяц.");
            return;
        }

        // Преобразуем строковое значение месяца в объект Month
        Month month = Month.valueOf(monthString.toUpperCase());

        // Получаем количество абонентов с помощью DAO
        int subscriberCount = callDAO.getSubscriberCountForCityAndMonth(city, month);

        // Отображаем результат в метке
        subscriberCountLabel.setText("Количество абонентов: " + subscriberCount);
        subscriberCountLabel.setVisible(true); // Показываем метку с результатом
    }


    private void loadClientsForComboBox() {
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.getAllClients(); // Получаем всех клиентов из базы
        ObservableList<Client> clientList = FXCollections.observableArrayList(clients);
        clientComboBox.setItems(clientList); // Устанавливаем элементы в ComboBox

        // Устанавливаем отображаемое значение в ComboBox, например, полное имя клиента
        clientComboBox.setCellFactory(param -> new ListCell<Client>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getFullName()); // Отображаем полное имя клиента в ComboBox
            }
        });
    }

    @FXML
    private void addCall() {
        // Показываем форму для добавления звонка
        addCallForm.setVisible(true);

        // Очищаем поля формы
        cityCalledToField.clear();
        phoneNumberField.clear();
        dateOfCallField.setValue(null);
        callDurationField.clear();

        // Очищаем выбор клиента
        clientComboBox.getSelectionModel().clearSelection();

        // Убираем данные о текущем звонке, так как это новая запись
        addCallForm.setUserData(null);
    }


    @FXML
    private void saveCall() {
        // Получаем данные из формы
        Client selectedClient = clientComboBox.getValue();  // Получаем выбранного клиента из ComboBox
        String cityCalledTo = cityCalledToField.getText();
        String phoneNumber = phoneNumberField.getText();
        LocalDate date = dateOfCallField.getValue();
        String callDurationString = callDurationField.getText();

        // Проверка на пустые поля
        if (selectedClient == null || cityCalledTo.isEmpty() || phoneNumber.isEmpty() || date == null || callDurationString.isEmpty()) {
            showAlert("Ошибка", "Все поля должны быть заполнены.");
            return;
        }

        // Проверяем корректность формата времени
        LocalTime time;
        try {
            time = LocalTime.parse(timeOfCallField.getText());
        } catch (DateTimeParseException e) {
            showAlert("Ошибка", "Некорректный формат времени. Используйте формат ЧЧ:ММ.");
            return;
        }

        LocalDateTime dateTimeOfCall = LocalDateTime.of(date, time);

        LocalTime callDuration;
        try {
            // Преобразуем строку в LocalTime. Строка формата "HH:mm:ss"
            callDuration = LocalTime.parse(callDurationString);
        } catch (DateTimeParseException e) {
            showAlert("Ошибка", "Некорректный формат времени вызова. Ожидаемый формат: HH:mm:ss.");
            return;
        }

        // Объединяем дату и время в LocalDateTime


        // Создание нового звонка
        Call newCall = new Call(0, selectedClient.getId(), dateTimeOfCall, cityCalledTo, phoneNumber, callDuration);
        CallDAO callDAO = new CallDAO();
        callDAO.createCall(newCall);

        // Скрытие формы после добавления
        addCallForm.setVisible(false);

        // Обновление таблицы звонков
        loadCalls();

        // Сообщение об успешном добавлении
        showAlert("Успех", "Звонок успешно добавлен.");
    }


    @FXML
    private void deleteCall() {
        Call selectedCall = callTable.getSelectionModel().getSelectedItem();
        if (selectedCall != null) {
            callDAO.deleteCall(selectedCall.getId());
            loadCalls();
        } else {
            showAlert("Ошибка", "Выберите звонок для удаления.");
        }
    }
    private void showAlert(String title, String content) {
        // Оповещение об ошибке
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadCalls() {
        CallDAO callDAO = new CallDAO();
        List<Call> calls = callDAO.getAllCalls();
        ObservableList<Call> callList = FXCollections.observableArrayList(calls);
        callTable.setItems(callList);
    }

    @FXML
    private void cancel() {
        addCallForm.setVisible(false); // Скрыть форму добавления звонка
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

    @FXML
    private void editCall() {
        // Получаем выбранный звонок
        Call selectedCall = callTable.getSelectionModel().getSelectedItem();

        if (selectedCall != null) {
            // Открываем форму для редактирования
            addCallForm.setVisible(true);

            // Заполняем поля данными выбранного звонка
            cityCalledToField.setText(selectedCall.getCityCalledTo());
            phoneNumberField.setText(selectedCall.getPhoneNumber());
            dateOfCallField.setValue(selectedCall.getDateOfCall().toLocalDate());
            callDurationField.setText(selectedCall.getCallDuration().toString());

            // Устанавливаем объект звонка для редактирования
            addCallForm.setUserData(selectedCall);
        } else {
            showAlert("Ошибка", "Выберите звонок для редактирования.");
        }
    }
}
