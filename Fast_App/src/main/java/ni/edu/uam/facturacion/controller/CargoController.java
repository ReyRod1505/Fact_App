package ni.edu.uam.facturacion.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.facturacion.model.Cargo;
import ni.edu.uam.facturacion.util.DataStore;

public class CargoController {

    @FXML private TextField         txtNombre;
    @FXML private TextField         txtDescripcion;
    @FXML private TableView<Cargo>  tblCargos;

    @FXML private TableColumn<Cargo, Integer> colId;
    @FXML private TableColumn<Cargo, String>  colNombre;
    @FXML private TableColumn<Cargo, String>  colDescripcion;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        // Usar la lista compartida del DataStore
        tblCargos.setItems(DataStore.getCargos());
    }

    @FXML
    private void guardar() {
        if (txtNombre.getText().isBlank()) {
            alerta(Alert.AlertType.WARNING, "El nombre del cargo es obligatorio.");
            return;
        }
        int nextId = DataStore.getCargos().size() + 1;
        DataStore.getCargos().add(new Cargo(
            nextId,
            txtNombre.getText().trim(),
            txtDescripcion.getText().trim()
        ));
        alerta(Alert.AlertType.INFORMATION, "Cargo registrado correctamente.");
        txtNombre.clear();
        txtDescripcion.clear();
    }

    private void alerta(Alert.AlertType tipo, String mensaje) {
        new Alert(tipo, mensaje, ButtonType.OK).showAndWait();
    }
}
