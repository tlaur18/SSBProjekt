package ssb.presentation_layer;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ssb.domain_layer.logger.LoggerManager;

public class SSB extends Application {
    private final static Logger logr = Logger.getLogger( LoggerManager.class.getName() );   
    @Override
    public void start(Stage stage) throws Exception {
        LoggerManager.setupLogger();
        
        logr.warning("Logger Startet");
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
