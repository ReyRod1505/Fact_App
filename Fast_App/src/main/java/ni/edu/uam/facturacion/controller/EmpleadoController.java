package ni.edu.uam.facturacion.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ni.edu.uam.facturacion.model.Cargo;
import ni.edu.uam.facturacion.model.Empleado;

import java.time.LocalDate;

public class EmpleadoController {

    @FXML private TextField            txtNombres;
    @FXML private TextField            txtApellidos;
    @FXML private ComboBox<Cargo>      cmbCargo;
    @FXML private DatePicker           dpFechaContratacion;
    @FXML private CheckBox             chkActivo;
    @FXML private TableView<Empleado>  tblEmpleados;

    @FXML private TableColumn<Empleado, String>    colNombres;
    @FXML private TableColumn<Empleado, String>    colApellidos;
    @FXML private TableColumn<Empleado, Cargo>     colCargo;
    @FXML private TableColumn<Empleado, LocalDate> colFecha;
    @FXML private TableColumn<Empleado, Boolean>   colActivo;

    private final ObservableList<Empleado> empleados = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        cmbCargo.setItems(FXCollections.observableArrayList(
            new Cargo(1, "Gerente",   "Gerente general"),
            new Cargo(2, "Cajero",    "Atiende la caja"),
            new Cargo(3, "Bodeguero", "Maneja el inventario")
        ));

        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        tblEmpleados.setItems(empleados);
        dpFechaContratacion.setValue(LocalDate.now());
        chkActivo.setSelected(true);
    }

    @FXML
    private void guardar() {
        if (txtNombres.getText().isBlank() || txtApellidos.getText().isBlank()
                || cmbCargo.getValue() == null
                || dpFechaContratacion.getValue() == null) {
            alerta(Alert.AlertType.WARNING, "Complete todos los campos obligatorios.");
            return;
        }
        empleados.add(new Empleado(
            empleados.size() + 1,
            txtNombres.getText().trim(),
            txtApellidos.getText().trim(),
            cmbCargo.getValue(),
            dpFechaContratacion.getValue(),
            chkActivo.isSelected()
        ));
        alerta(Alert.AlertType.INFORMATION, "Empleado registrado correctamente.");
        limpiar();
    }

    @FXML
    private void cerrar() {
        ((Stage) txtNombres.getScene().getWindow()).close();
    }

    private void limpiar() {
        txtNombres.clear();
        txtApellidos.clear();
        cmbCargo.getSelectionModel().clearSelection();
        dpFechaContratacion.setValue(LocalDate.now());
        chkActivo.setSelected(true);
    }

    private void alerta(Alert.AlertType tipo, String mensaje) {
        new Alert(tipo, mensaje, ButtonType.OK).showAndWait();
    }
}
