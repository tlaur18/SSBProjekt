package ssb.presentation_layer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author olive
 */
public class SSB extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Log ind");
        Parent root = FXMLLoader.load(getClass().getResource("fxml_documents/login_layout.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
