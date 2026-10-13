package ni.edu.uam.facturacion.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.facturacion.model.Categoria;
import ni.edu.uam.facturacion.model.Producto;
import ni.edu.uam.facturacion.util.DataStore;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductoController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtExistencia;
    @FXML private ComboBox<Categoria> cmbCategoria;
    @FXML private CheckBox chkActivo;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    @FXML private TextField txtBuscar;
    @FXML private ComboBox<Categoria> cmbFiltroCategoria;
    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private Label lblTotalRegistros;

    @FXML private TableView<Producto> tblProductos;
    @FXML private TableColumn<Producto, String> colCodigo;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Categoria> colCategoria;
    @FXML private TableColumn<Producto, BigDecimal> colPrecio;
    @FXML private TableColumn<Producto, Integer> colExistencia;
    @FXML private TableColumn<Producto, Boolean> colActivo;

    private FilteredList<Producto> productosFiltrados;
    private Producto productoSeleccionado;
    private final Categoria categoriaTodas = new Categoria(0, "Todas", true);

    @FXML
    private void initialize() {
        cmbCategoria.setItems(DataStore.getCategorias());

        ObservableList<Categoria> categoriasFiltro = FXCollections.observableArrayList();
        categoriasFiltro.add(categoriaTodas);
        categoriasFiltro.addAll(DataStore.getCategorias());
        cmbFiltroCategoria.setItems(categoriasFiltro);
        cmbFiltroCategoria.setValue(categoriaTodas);

        cmbFiltroEstado.setItems(FXCollections.observableArrayList("Todos", "Activos", "Inactivos"));
        cmbFiltroEstado.setValue("Todos");

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        colActivo.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean activo, boolean empty) {
                super.updateItem(activo, empty);
                if (empty || activo == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(activo ? "Activo" : "Inactivo");
                    setStyle(activo ? "-fx-text-fill: #15803d; -fx-font-weight: bold;" : "-fx-text-fill: #b91c1c;");
                }
            }
        });

        productosFiltrados = new FilteredList<>(DataStore.getProductos(), p -> true);

        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        cmbFiltroCategoria.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        cmbFiltroEstado.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());

        SortedList<Producto> productosOrdenados = new SortedList<>(productosFiltrados);
        productosOrdenados.comparatorProperty().bind(tblProductos.comparatorProperty());
        tblProductos.setItems(productosOrdenados);

        tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            if (nuevo != null) {
                cargarProductoEnFormulario(nuevo);
            }
        });

        actualizarContador();
        productosFiltrados.predicateProperty().addListener((obs, o, n) -> actualizarContador());
        DataStore.getProductos().addListener((javafx.collections.ListChangeListener<Producto>) c -> actualizarContador());

        chkActivo.setSelected(true);
    }

    private void aplicarFiltros() {
        String texto = txtBuscar.getText() != null ? txtBuscar.getText().trim().toLowerCase() : "";
        Categoria catFiltro = cmbFiltroCategoria.getValue();
        String estadoFiltro = cmbFiltroEstado.getValue();

        productosFiltrados.setPredicate(producto -> {
            if (producto == null) return false;

            boolean coincideTexto = texto.isEmpty()
                || (producto.getCodigo() != null && producto.getCodigo().toLowerCase().contains(texto))
                || (producto.getNombre() != null && producto.getNombre().toLowerCase().contains(texto))
                || (producto.getCategoria() != null && producto.getCategoria().getNombre().toLowerCase().contains(texto));

            boolean coincideCategoria = (catFiltro == null || catFiltro.getId() == 0 || "Todas".equalsIgnoreCase(catFiltro.getNombre()))
                || (producto.getCategoria() != null && producto.getCategoria().getId().equals(catFiltro.getId()));

            boolean coincideEstado = true;
            if ("Activos".equalsIgnoreCase(estadoFiltro)) {
                coincideEstado = producto.isActivo();
            } else if ("Inactivos".equalsIgnoreCase(estadoFiltro)) {
                coincideEstado = !producto.isActivo();
            }

            return coincideTexto && coincideCategoria && coincideEstado;
        });

        actualizarContador();
    }

    private void actualizarContador() {
        int total = tblProductos.getItems().size();
        lblTotalRegistros.setText("Mostrando " + total + (total == 1 ? " producto" : " productos"));
    }

    private void cargarProductoEnFormulario(Producto producto) {
        this.productoSeleccionado = producto;
        txtCodigo.setText(producto.getCodigo());
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(producto.getPrecioVenta() != null ? producto.getPrecioVenta().toString() : "");
        txtExistencia.setText(String.valueOf(producto.getExistencia()));
        chkActivo.setSelected(producto.isActivo());

        if (producto.getCategoria() != null) {
            for (Categoria c : cmbCategoria.getItems()) {
                if (c.getId().equals(producto.getCategoria().getId())) {
                    cmbCategoria.setValue(c);
                    break;
                }
            }
        } else {
            cmbCategoria.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void guardar() {
        if (!validarCampos()) return;

        String codigo = txtCodigo.getText().trim();
        boolean existeCodigo = DataStore.getProductos().stream()
            .anyMatch(p -> p.getCodigo().equalsIgnoreCase(codigo));

        if (existeCodigo) {
            mensaje(Alert.AlertType.WARNING, "Ya existe un producto con el codigo '" + codigo + "'.");
            return;
        }

        try {
            BigDecimal precio = new BigDecimal(txtPrecio.getText().trim());
            int existencia = Integer.parseInt(txtExistencia.getText().trim());

            if (!validarNumericos(precio, existencia)) return;

            int nuevoId = DataStore.getProductos().size() + 1;
            Producto nuevoProducto = new Producto(
                nuevoId,
                codigo,
                txtNombre.getText().trim(),
                cmbCategoria.getValue(),
                precio,
                existencia,
                chkActivo.isSelected()
            );

            DataStore.getProductos().add(nuevoProducto);
            mensaje(Alert.AlertType.INFORMATION, "Producto registrado correctamente.");
            limpiar();
        } catch (NumberFormatException e) {
            mensaje(Alert.AlertType.ERROR, "Precio o existencia no tienen un formato numerico valido.");
        }
    }

    @FXML
    private void actualizar() {
        if (productoSeleccionado == null) {
            mensaje(Alert.AlertType.WARNING, "Seleccione un producto de la tabla para actualizar.");
            return;
        }

        if (!validarCampos()) return;

        String codigoNuevo = txtCodigo.getText().trim();
        boolean codigoDuplicadoEnOtro = DataStore.getProductos().stream()
            .anyMatch(p -> !p.getId().equals(productoSeleccionado.getId()) && p.getCodigo().equalsIgnoreCase(codigoNuevo));

        if (codigoDuplicadoEnOtro) {
            mensaje(Alert.AlertType.WARNING, "El codigo '" + codigoNuevo + "' ya pertenece a otro producto registrado.");
            return;
        }

        try {
            BigDecimal precio = new BigDecimal(txtPrecio.getText().trim());
            int existencia = Integer.parseInt(txtExistencia.getText().trim());

            if (!validarNumericos(precio, existencia)) return;

            productoSeleccionado.setCodigo(codigoNuevo);
            productoSeleccionado.setNombre(txtNombre.getText().trim());
            productoSeleccionado.setCategoria(cmbCategoria.getValue());
            productoSeleccionado.setPrecioVenta(precio);
            productoSeleccionado.setExistencia(existencia);
            productoSeleccionado.setActivo(chkActivo.isSelected());

            tblProductos.refresh();
            mensaje(Alert.AlertType.INFORMATION, "Producto actualizado correctamente.");
            limpiar();
        } catch (NumberFormatException e) {
            mensaje(Alert.AlertType.ERROR, "Precio o existencia no tienen un formato numerico valido.");
        }
    }

    @FXML
    private void eliminar() {
        if (productoSeleccionado == null) {
            mensaje(Alert.AlertType.WARNING, "Seleccione un producto de la tabla para eliminar.");
            return;
        }

        Alert alertaConfirmacion = new Alert(
            Alert.AlertType.CONFIRMATION,
            "Esta seguro de eliminar el producto '" + productoSeleccionado.getNombre() + "'?",
            ButtonType.OK,
            ButtonType.CANCEL
        );
        alertaConfirmacion.setTitle("Confirmar eliminacion");
        alertaConfirmacion.setHeaderText(null);

        Optional<ButtonType> respuesta = alertaConfirmacion.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            DataStore.getProductos().remove(productoSeleccionado);
            mensaje(Alert.AlertType.INFORMATION, "Producto eliminado correctamente.");
            limpiar();
        }
    }

    @FXML
    private void limpiar() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtExistencia.clear();
        cmbCategoria.getSelectionModel().clearSelection();
        chkActivo.setSelected(true);
        productoSeleccionado = null;
        tblProductos.getSelectionModel().clearSelection();
    }

    @FXML
    private void limpiarFiltros() {
        txtBuscar.clear();
        cmbFiltroCategoria.setValue(categoriaTodas);
        cmbFiltroEstado.setValue("Todos");
    }

    private boolean validarCampos() {
        if (txtCodigo.getText() == null || txtCodigo.getText().isBlank()) {
            mensaje(Alert.AlertType.WARNING, "El codigo del producto es obligatorio.");
            txtCodigo.requestFocus();
            return false;
        }
        if (txtNombre.getText() == null || txtNombre.getText().isBlank()) {
            mensaje(Alert.AlertType.WARNING, "El nombre del producto es obligatorio.");
            txtNombre.requestFocus();
            return false;
        }
        if (cmbCategoria.getValue() == null) {
            mensaje(Alert.AlertType.WARNING, "Debe seleccionar una categoria.");
            cmbCategoria.requestFocus();
            return false;
        }
        if (txtPrecio.getText() == null || txtPrecio.getText().isBlank()) {
            mensaje(Alert.AlertType.WARNING, "El precio de venta es obligatorio.");
            txtPrecio.requestFocus();
            return false;
        }
        if (txtExistencia.getText() == null || txtExistencia.getText().isBlank()) {
            mensaje(Alert.AlertType.WARNING, "La existencia es obligatoria.");
            txtExistencia.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validarNumericos(BigDecimal precio, int existencia) {
        if (precio.signum() <= 0) {
            mensaje(Alert.AlertType.WARNING, "El precio de venta debe ser mayor que cero.");
            txtPrecio.requestFocus();
            return false;
        }
        if (existencia < 0) {
            mensaje(Alert.AlertType.WARNING, "La existencia no puede ser negativa.");
            txtExistencia.requestFocus();
            return false;
        }
        return true;
    }

    private void mensaje(Alert.AlertType tipo, String texto) {
        Alert alerta = new Alert(tipo, texto, ButtonType.OK);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
