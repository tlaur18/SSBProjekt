package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ssb.domain_layer.person.EmployeeManager;
import ssb.domain_layer.callbacks.LoginCallBack;
import ssb.domain_layer.logger.EmployeeLoggerManager;

public class LoginLayoutController implements Initializable {

    @FXML
    private TextField userNameTxtField;
    @FXML
    private PasswordField passwordTxtField;
    @FXML
    private ProgressIndicator progressIndicator;

    private String enteredUsername;
    private String enteredPassword;
    private final EmployeeManager loginManager = EmployeeManager.getInstance();
    @FXML
    private ImageView successImage;
    @FXML
    private ImageView errorImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        enteredUsername = userNameTxtField.getText();
        enteredPassword = passwordTxtField.getText();
        progressIndicator.setVisible(true);
        errorImage.setVisible(false);
        new Thread(new Task() {
            @Override
            protected Boolean call() throws Exception {
                return loginManager.checkValidInput(enteredUsername, enteredPassword, new LoginCallBackImpl());
            }

            @Override
            protected void succeeded() {
                if ((Boolean) getValue()) {
                    progressIndicator.setVisible(false);
                    successImage.setVisible(true);
                } else {
                    errorImage.setVisible(true);
                }
            }
        }).start();
    }

    private void changeStage() {
        Platform.runLater(() -> {
            FXMLLoader loader = null;
            try {
                URL url3 = new File("src/ssb/presentation_layer/fxml_documents/main_layout.fxml").toURL();
                loader = new FXMLLoader(url3);
                Parent root = (Parent) loader.load();
                Stage mainStage = new Stage();
                mainStage.setMinHeight(450);
                mainStage.setMinWidth(820);
                mainStage.setScene(new Scene(root));
                ((Stage) successImage.getScene().getWindow()).close(); //close login stage
                mainStage.show();
            } catch (IOException e) {
                System.out.println("LoginLayoutController - changestage - message: " + e.getMessage());
            }
        });
    }

    private class LoginCallBackImpl implements LoginCallBack {

        @Override
        public void handleMultipleHomes(List<String> homeNames) {
            Platform.runLater(() -> {
                progressIndicator.setVisible(false);
                ChoiceDialog<String> dialog = new ChoiceDialog<>(homeNames.get(0), homeNames);
                dialog.setTitle("Vælg bosted");
                dialog.setHeaderText("Hvilket bosted vil du logge ind på?");
                dialog.setContentText("Bosted:");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    progressIndicator.setVisible(true);
                    loginManager.fillHomeData(result.get());
                    login();
                }
            });

        }

        @Override
        public void wrongInput() {
            Platform.runLater(() -> {
                progressIndicator.setVisible(false);
                errorImage.setVisible(true);
            });
        }

        @Override
        public void adminLogin() {
            changeStage();
        }

        @Override
        public void login() {
            changeStage();
        }

    }

}
