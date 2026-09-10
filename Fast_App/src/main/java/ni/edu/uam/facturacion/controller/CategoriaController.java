package ni.edu.uam.facturacion.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.facturacion.model.Categoria;
import ni.edu.uam.facturacion.util.DataStore;

public class CategoriaController {

    @FXML private TextField            txtNombre;
    @FXML private CheckBox             chkActiva;
    @FXML private TableView<Categoria> tblCategorias;

    @FXML private TableColumn<Categoria, Integer> colId;
    @FXML private TableColumn<Categoria, String>  colNombre;
    @FXML private TableColumn<Categoria, Boolean> colActiva;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colActiva.setCellValueFactory(new PropertyValueFactory<>("activa"));
        // Usar la lista compartida del DataStore
        tblCategorias.setItems(DataStore.getCategorias());
        chkActiva.setSelected(true);
    }

    @FXML
    private void guardar() {
        if (txtNombre.getText().isBlank()) {
            alerta(Alert.AlertType.WARNING, "El nombre de la categoría es obligatorio.");
            return;
        }
        int nextId = DataStore.getCategorias().size() + 1;
        DataStore.getCategorias().add(new Categoria(
            nextId,
            txtNombre.getText().trim(),
            chkActiva.isSelected()
        ));
        alerta(Alert.AlertType.INFORMATION, "Categoría registrada correctamente.");
        txtNombre.clear();
        chkActiva.setSelected(true);
    }

    private void alerta(Alert.AlertType tipo, String mensaje) {
        new Alert(tipo, mensaje, ButtonType.OK).showAndWait();
    }
}
