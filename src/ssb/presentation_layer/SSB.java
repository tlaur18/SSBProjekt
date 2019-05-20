package ssb.presentation_layer;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ssb.data_layer.logger.AdminLoggerManager;
import ssb.data_layer.logger.EmployeeLoggerManager;

public class SSB extends Application {
    private final static Logger employeeLogr = Logger.getLogger(EmployeeLoggerManager.class.getName() );   
    private final static Logger adminLogr = Logger.getLogger(AdminLoggerManager.class.getName());
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.setTitle("Log ind");
        Parent root = FXMLLoader.load(getClass().getResource("fxml_documents/login_layout.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
