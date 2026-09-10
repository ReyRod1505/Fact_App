package ni.edu.uam.fast_app.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ni.edu.uam.fast_app.utils.SceneManager;
import java.io.IOException;

public class MenuPrincipalController {

    @FXML private MenuItem mnuProductos;
    @FXML private MenuItem mnuSalir;
    @FXML private Button btnProductos;
    @FXML private Button btnSalir;

    @FXML
    private void abrirProductos() {
        try {
            SceneManager.abrirVentana(
                "/ni.edu.uam.fast_app/fxml/producto-view.fxml",
                "Gestión de productos");
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                "No fue posible abrir Productos.").showAndWait();
        }
    }

    @FXML
    private void salir() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
            "¿Desea cerrar la aplicación?", ButtonType.OK, ButtonType.CANCEL);
        if (a.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK)
            Platform.exit();
    }
}
