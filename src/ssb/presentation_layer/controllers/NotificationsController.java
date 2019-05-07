package ssb.presentation_layer.controllers;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ssb.domain_layer.Department;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.Notification;
import ssb.domain_layer.NotificationManager;

public class NotificationsController implements Initializable {

    @FXML
    private TextArea notificationTextArea;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox notificationVbox;

    Employee loggedInEmployee;
    Department currentDepartment;
    NotificationManager notificationManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedInEmployee = InformationBridge.getInstance().getLoggedInEmployee();
        currentDepartment = InformationBridge.getInstance().getCurrentDepartment();
        notificationManager = NotificationManager.getInstance();

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        notificationManager.checkForNewNotifications(currentDepartment.getId());

        showNotifications();
    }

    @FXML
    private void sendNotificationHandler(ActionEvent event) {
        String message = notificationTextArea.getText();
        notificationTextArea.clear();
        String employeeFirstName = loggedInEmployee.getFirstName().substring(0, 1).toUpperCase() + loggedInEmployee.getFirstName().substring(1);
        String employeeLastName = loggedInEmployee.getLastName().substring(0, 1).toUpperCase() + loggedInEmployee.getLastName().substring(1);
        String employeeName = employeeFirstName + " " + employeeLastName;
        Date creationDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String dateString = dateFormat.format(creationDate);

        GridPane newNotification = assembleNotification(employeeName, message, dateString);
        notificationVbox.getChildren().add(newNotification);

        //Makes the scrollPane go to the bottom
        scrollPane.vvalueProperty().bind(notificationVbox.heightProperty());

        notificationManager.saveNewNotification(message, employeeName, dateString, currentDepartment.getId());
    }

    private GridPane assembleNotification(String name, String message, String creationDate) {
        String employeeFirstName = loggedInEmployee.getFirstName().substring(0, 1).toUpperCase() + loggedInEmployee.getFirstName().substring(1);
        String employeeLastName = loggedInEmployee.getLastName().substring(0, 1).toUpperCase() + loggedInEmployee.getLastName().substring(1);
        String employeeName = employeeFirstName + " " + employeeLastName;
        GridPane gridPane = new GridPane();

        gridPane.addColumn(1);
        gridPane.addRow(1);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().add(column1);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMaxWidth(110);
        column2.setMinWidth(110);
        gridPane.getColumnConstraints().add(column2);

        Label nameLabel = new Label(name);
        nameLabel.setPadding(new Insets(5, 5, 5, 5));
        gridPane.add(nameLabel, 0, 0);

        Label messageLabel = new Label(message);
        messageLabel.setPadding(new Insets(5, 5, 5, 5));
        messageLabel.setWrapText(true);
        gridPane.add(messageLabel, 0, 1);

        Label dateLabel = new Label(creationDate);
        dateLabel.setPadding(new Insets(5, 5, 5, 5));
        gridPane.add(dateLabel, 1, 0);
        GridPane.setHalignment(dateLabel, HPos.RIGHT);
        
        //css assembling

        notificationVbox.getStyleClass().add("vbox-notification");
        if (employeeName.equals(name)) {
            gridPane.getStyleClass().add("notification-currentUser");
            for (Node node : gridPane.getChildren()) {
                if (node instanceof Label) {
                    node.getStyleClass().add("notification-label");
                }
                messageLabel.setFont(new Font(14));
            }
        } else {
            gridPane.getStyleClass().add("notification-OtherUsers");
        }

        return gridPane;
    }

    private void showNotifications() {
        for (Notification notification : notificationManager.getNotifications()) {
            GridPane notificatonGridPane = assembleNotification(notification.getAuthorName(), notification.getMessage(), notification.getCreationDate());
            notificationVbox.getChildren().add(notificatonGridPane);

            //Makes the scrollPane go to the bottom
            scrollPane.vvalueProperty().bind(notificationVbox.heightProperty());
        }
    }
}
