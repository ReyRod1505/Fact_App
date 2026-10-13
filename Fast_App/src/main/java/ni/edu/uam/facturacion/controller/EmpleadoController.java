package ni.edu.uam.facturacion.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.facturacion.model.Cargo;
import ni.edu.uam.facturacion.model.Empleado;
import ni.edu.uam.facturacion.util.DataStore;

import java.time.LocalDate;

public class EmpleadoController {

    @FXML private TextField           txtNombres;
    @FXML private TextField           txtApellidos;
    @FXML private ComboBox<Cargo>     cmbCargo;
    @FXML private DatePicker          dpFechaContratacion;
    @FXML private CheckBox            chkActivo;
    @FXML private TableView<Empleado> tblEmpleados;

    @FXML private TableColumn<Empleado, String>    colNombres;
    @FXML private TableColumn<Empleado, String>    colApellidos;
    @FXML private TableColumn<Empleado, Cargo>     colCargo;
    @FXML private TableColumn<Empleado, LocalDate> colFecha;
    @FXML private TableColumn<Empleado, Boolean>   colActivo;

    @FXML
    private void initialize() {
        // Usa la lista compartida de cargos
        cmbCargo.setItems(DataStore.getCargos());

        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        // Usar la lista compartida de empleados del DataStore para que persistan al navegar
        tblEmpleados.setItems(DataStore.getEmpleados());
        dpFechaContratacion.setValue(LocalDate.now());

        // Restringir el DatePicker para que no se puedan seleccionar fechas futuras en el calendario
        dpFechaContratacion.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date != null && date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #fee2e2;"); // Color rojo claro sutil para días deshabilitados
                }
            }
        });

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

        // Validación: La fecha de contratación no puede ser mayor a la fecha actual
        if (dpFechaContratacion.getValue().isAfter(LocalDate.now())) {
            alerta(Alert.AlertType.WARNING, "La fecha de contratación no puede ser posterior a la fecha actual.");
            return;
        }

        int nextId = DataStore.getEmpleados().size() + 1;
        DataStore.getEmpleados().add(new Empleado(
            nextId,
            txtNombres.getText().trim(),
            txtApellidos.getText().trim(),
            cmbCargo.getValue(),
            dpFechaContratacion.getValue(),
            chkActivo.isSelected()
        ));
        alerta(Alert.AlertType.INFORMATION, "Empleado registrado correctamente.");
        limpiar();
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
