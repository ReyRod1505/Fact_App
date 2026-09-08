package ni.edu.uam.fast_app.application;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.net.URL;

public class FacturacionApplicacion extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Cargar el archivo FXML
        URL fxmlLocation = getClass().getResource("/ni.edu.uam.fast_app/fxml/menu-principal.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle("Facturación Fast App");
        stage.setScene(scene);
        stage.show();
    }
}
