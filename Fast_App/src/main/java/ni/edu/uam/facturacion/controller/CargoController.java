package ni.edu.uam.facturacion.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ni.edu.uam.facturacion.model.Cargo;

public class CargoController {

    @FXML private TextField         txtNombre;
    @FXML private TextField         txtDescripcion;
    @FXML private TableView<Cargo>  tblCargos;

    @FXML private TableColumn<Cargo, Integer> colId;
    @FXML private TableColumn<Cargo, String>  colNombre;
    @FXML private TableColumn<Cargo, String>  colDescripcion;

    private final ObservableList<Cargo> cargos = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tblCargos.setItems(cargos);
    }

    @FXML
    private void guardar() {
        if (txtNombre.getText().isBlank()) {
            alerta(Alert.AlertType.WARNING, "El nombre del cargo es obligatorio.");
            return;
        }
        cargos.add(new Cargo(
            cargos.size() + 1,
            txtNombre.getText().trim(),
            txtDescripcion.getText().trim()
        ));
        alerta(Alert.AlertType.INFORMATION, "Cargo registrado correctamente.");
        limpiar();
    }

    @FXML
    private void cerrar() {
        ((Stage) txtNombre.getScene().getWindow()).close();
    }

    private void limpiar() {
        txtNombre.clear();
        txtDescripcion.clear();
    }

    private void alerta(Alert.AlertType tipo, String mensaje) {
        new Alert(tipo, mensaje, ButtonType.OK).showAndWait();
    }
}
