package ni.edu.uam.facturacion.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import ni.edu.uam.facturacion.util.SceneManager;
import java.io.IOException;

public class MenuPrincipalController {

    @FXML private MenuItem mnuProductos;
    @FXML private MenuItem mnuEmpleados;
    @FXML private MenuItem mnuCargos;
    @FXML private MenuItem mnuSalir;
    @FXML private Button btnProductos;
    @FXML private Button btnEmpleados;
    @FXML private Button btnCargos;
    @FXML private Button btnSalir;

    @FXML
    private void abrirProductos() {
        abrir("/ni/edu/uam/facturacion/fxml/producto-view.fxml", "Gestión de Productos");
    }

    @FXML
    private void abrirEmpleados() {
        abrir("/ni/edu/uam/facturacion/fxml/empleado-view.fxml", "Gestión de Empleados");
    }

    @FXML
    private void abrirCargos() {
        abrir("/ni/edu/uam/facturacion/fxml/cargo-view.fxml", "Gestión de Cargos");
    }

    @FXML
    private void salir() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
            "¿Desea cerrar la aplicación?", ButtonType.OK, ButtonType.CANCEL);
        if (a.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK)
            Platform.exit();
    }

    private void abrir(String fxml, String titulo) {
        try {
            SceneManager.abrirVentana(fxml, titulo);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                "No fue posible abrir: " + titulo, ButtonType.OK).showAndWait();
        }
    }
}
