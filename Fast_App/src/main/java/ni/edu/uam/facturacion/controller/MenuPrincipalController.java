package ni.edu.uam.facturacion.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import java.io.IOException;

public class MenuPrincipalController {

    @FXML private MenuItem mnuProductos;
    @FXML private MenuItem mnuEmpleados;
    @FXML private MenuItem mnuCargos;
    @FXML private MenuItem mnuCategorias;
    @FXML private MenuItem mnuSalir;

    @FXML private Button btnInicio;
    @FXML private Button btnProductos;
    @FXML private Button btnEmpleados;
    @FXML private Button btnCargos;
    @FXML private Button btnCategorias;
    @FXML private Button btnSalir;

    @FXML private StackPane contenido;

    @FXML
    private void initialize() {
        mostrarInicio();
    }

    @FXML
    private void mostrarInicio() {
        VBox bienvenida = new VBox(16);
        bienvenida.setAlignment(Pos.CENTER);
        bienvenida.setStyle("-fx-background-color: #f4f6f9;");

        Label titulo = new Label("Sistema de Facturación");
        titulo.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label sub = new Label("Seleccione una opción del menú o la barra de herramientas");
        sub.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        bienvenida.getChildren().addAll(titulo, sub);
        contenido.getChildren().setAll(bienvenida);
    }

    @FXML private void abrirProductos()   { cargarVista("/ni/edu/uam/facturacion/fxml/producto-view.fxml");  }
    @FXML private void abrirEmpleados()   { cargarVista("/ni/edu/uam/facturacion/fxml/empleado-view.fxml");  }
    @FXML private void abrirCargos()      { cargarVista("/ni/edu/uam/facturacion/fxml/cargo-view.fxml");     }
    @FXML private void abrirCategorias()  { cargarVista("/ni/edu/uam/facturacion/fxml/categoria-view.fxml"); }

    @FXML
    private void salir() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
            "¿Desea cerrar la aplicación?", ButtonType.OK, ButtonType.CANCEL);
        if (a.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK)
            Platform.exit();
    }

    private void cargarVista(String fxml) {
        try {
            Node vista = FXMLLoader.load(getClass().getResource(fxml));
            contenido.getChildren().setAll(vista);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                "No se pudo cargar la vista:\n" + fxml, ButtonType.OK).showAndWait();
        }
    }
}
