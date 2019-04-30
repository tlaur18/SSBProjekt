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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.InformationBridge;

public class NotificationsController implements Initializable {

    @FXML
    private TextArea notificationTextArea;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox notificationVbox;

    Employee loggedInEmployee;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedInEmployee = InformationBridge.getInstance().getLoggedInEmployee();

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

    }

    @FXML
    private void sendNotificationHandler(ActionEvent event) {
        String message = notificationTextArea.getText();
        notificationTextArea.clear();
        String employeeFirstName = loggedInEmployee.getFirstName().substring(0,1).toUpperCase() + loggedInEmployee.getFirstName().substring(1);
        String employeeLastName = loggedInEmployee.getLastName().substring(0, 1).toUpperCase() + loggedInEmployee.getLastName().substring(1);
        String employeeName = employeeFirstName + " " + employeeLastName;

        GridPane newNotification = assembleNotification(employeeName, message);
        notificationVbox.getChildren().add(newNotification);

        //Makes the scrollPane go to the bottom
        scrollPane.vvalueProperty().bind(notificationVbox.heightProperty());
    }

    private GridPane assembleNotification(String name, String message) {
        GridPane gridPane = new GridPane();

        gridPane.addColumn(1);
        gridPane.addRow(1);
        gridPane.gridLinesVisibleProperty().set(true);
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

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String dateString = dateFormat.format(date);
        Label dateLabel = new Label(dateString);
        dateLabel.setPadding(new Insets(5, 5, 5, 5));
        gridPane.add(dateLabel, 1, 0);
        GridPane.setHalignment(dateLabel, HPos.RIGHT);

        return gridPane;
    }
}
