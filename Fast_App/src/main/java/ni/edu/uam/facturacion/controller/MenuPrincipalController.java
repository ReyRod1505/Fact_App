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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import java.io.IOException;

public class MenuPrincipalController {

    @FXML private MenuItem mnuProductos;
    @FXML private MenuItem mnuEmpleados;
    @FXML private MenuItem mnuCargos;
    @FXML private MenuItem mnuCategorias;
    @FXML private MenuItem mnuProcesos;
    @FXML private MenuItem mnuSalir;

    @FXML private Button btnInicio;
    @FXML private Button btnProductos;
    @FXML private Button btnEmpleados;
    @FXML private Button btnCargos;
    @FXML private Button btnCategorias;
    @FXML private Button btnProcesos;
    @FXML private Button btnSalir;

    @FXML private StackPane contenido;

    @FXML
    private void initialize() {
        mostrarInicio();
    }

    @FXML
    private void mostrarInicio() {
        VBox bienvenida = new VBox(20);
        bienvenida.setAlignment(Pos.CENTER);
        bienvenida.setStyle("-fx-background-color: #f8fafc; -fx-padding: 30;");

        Label titulo = new Label("Sistema de Facturación");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Label sub = new Label("Seleccione un módulo para continuar:");
        sub.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");

        FlowPane tarjetas = new FlowPane();
        tarjetas.setAlignment(Pos.CENTER);
        tarjetas.setHgap(16);
        tarjetas.setVgap(16);
        tarjetas.setStyle("-fx-padding: 10 0 0 0;");

        VBox cardProductos = crearTarjeta("📦", "Productos", "Gestión de catálogo", this::abrirProductos);
        VBox cardEmpleados = crearTarjeta("👤", "Empleados", "Registro de personal", this::abrirEmpleados);
        VBox cardCargos = crearTarjeta("🏷", "Cargos", "Puestos y áreas", this::abrirCargos);
        VBox cardCategorias = crearTarjeta("📁", "Categorías", "Clasificación de artículos", this::abrirCategorias);
        VBox cardProcesos = crearTarjeta("⚙️", "Procesos", "Monitor en segundo plano", this::abrirProcesos);

        tarjetas.getChildren().addAll(cardProductos, cardEmpleados, cardCargos, cardCategorias, cardProcesos);

        bienvenida.getChildren().addAll(titulo, sub, tarjetas);
        contenido.getChildren().setAll(bienvenida);
    }

    private VBox crearTarjeta(String icono, String tituloText, String descText, Runnable accion) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-padding: 22 18; " +
            "-fx-background-radius: 12; " +
            "-fx-border-color: #e2e8f0; " +
            "-fx-border-radius: 12; " +
            "-fx-border-width: 1.5; " +
            "-fx-cursor: hand; " +
            "-fx-min-width: 140; " +
            "-fx-pref-width: 155;"
        );

        Label lblIcono = new Label(icono);
        lblIcono.setStyle("-fx-font-size: 34px;");

        Label lblTitulo = new Label(tituloText);
        lblTitulo.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Label lblDesc = new Label(descText);
        lblDesc.setWrapText(true);
        lblDesc.setAlignment(Pos.CENTER);
        lblDesc.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748b; -fx-text-alignment: center;");

        card.getChildren().addAll(lblIcono, lblTitulo, lblDesc);
        card.setOnMouseClicked(e -> accion.run());

        card.setOnMouseEntered(e -> card.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-padding: 22 18; " +
            "-fx-background-radius: 12; " +
            "-fx-border-color: #0284c7; " +
            "-fx-border-radius: 12; " +
            "-fx-border-width: 1.5; " +
            "-fx-cursor: hand; " +
            "-fx-min-width: 140; " +
            "-fx-pref-width: 155;"
        ));

        card.setOnMouseExited(e -> card.setStyle(
            "-fx-background-color: white; " +
            "-fx-padding: 22 18; " +
            "-fx-background-radius: 12; " +
            "-fx-border-color: #e2e8f0; " +
            "-fx-border-radius: 12; " +
            "-fx-border-width: 1.5; " +
            "-fx-cursor: hand; " +
            "-fx-min-width: 140; " +
            "-fx-pref-width: 155;"
        ));

        return card;
    }

    @FXML private void abrirProductos() { cargarVista("/ni/edu/uam/facturacion/fxml/producto-view.fxml"); }
    @FXML private void abrirEmpleados() { cargarVista("/ni/edu/uam/facturacion/fxml/empleado-view.fxml"); }
    @FXML private void abrirCargos() { cargarVista("/ni/edu/uam/facturacion/fxml/cargo-view.fxml"); }
    @FXML private void abrirCategorias() { cargarVista("/ni/edu/uam/facturacion/fxml/categoria-view.fxml"); }
    @FXML private void abrirProcesos() { cargarVista("/ni/edu/uam/practicahilos/procesador-view.fxml"); }

    @FXML
    private void salir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea cerrar la aplicación?", ButtonType.OK, ButtonType.CANCEL);
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Platform.exit();
        }
    }

    private void cargarVista(String fxml) {
        try {
            Node vista = FXMLLoader.load(getClass().getResource(fxml));
            contenido.getChildren().setAll(vista);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "No se pudo cargar la vista: " + fxml, ButtonType.OK).showAndWait();
        }
    }
}
