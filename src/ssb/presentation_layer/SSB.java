package ssb.presentation_layer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SSB extends Application {
    
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
