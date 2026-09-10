package ni.edu.uam.facturacion.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FacturacionApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
            "/ni/edu/uam/facturacion/fxml/menu-principal.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        stage.setTitle("Sistema de Facturación");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
