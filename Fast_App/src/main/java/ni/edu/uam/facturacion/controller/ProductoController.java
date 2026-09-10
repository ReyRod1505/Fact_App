package ni.edu.uam.facturacion.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ni.edu.uam.facturacion.model.Categoria;
import ni.edu.uam.facturacion.model.Producto;
import ni.edu.uam.facturacion.util.DataStore;

import java.io.File;
import java.math.BigDecimal;

public class ProductoController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtExistencia;
    @FXML private ComboBox<Categoria> cmbCategoria;
    @FXML private CheckBox chkActivo;
    @FXML private ImageView imgProducto;
    @FXML private TableView<Producto> tblProductos;

    @FXML private TableColumn<Producto, String>     colCodigo;
    @FXML private TableColumn<Producto, String>     colNombre;
    @FXML private TableColumn<Producto, Categoria>  colCategoria;
    @FXML private TableColumn<Producto, BigDecimal> colPrecio;
    @FXML private TableColumn<Producto, Integer>    colExistencia;
    @FXML private TableColumn<Producto, Boolean>    colActivo;

    private final ObservableList<Producto> productos = FXCollections.observableArrayList();
    private String rutaImagen;

    @FXML
    private void initialize() {
        // Usa la lista compartida de categorías (incluye las creadas en sesión)
        cmbCategoria.setItems(DataStore.getCategorias());

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        tblProductos.setItems(productos);
        chkActivo.setSelected(true);
    }

    @FXML
    private void seleccionarImagen() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar imagen del producto");
        chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File archivo = chooser.showOpenDialog(txtCodigo.getScene().getWindow());
        if (archivo != null) {
            rutaImagen = archivo.toURI().toString();
            imgProducto.setImage(new Image(rutaImagen));
        }
    }

    @FXML
    private void guardar() {
        if (txtCodigo.getText().isBlank() || txtNombre.getText().isBlank()
                || txtPrecio.getText().isBlank() || txtExistencia.getText().isBlank()
                || cmbCategoria.getValue() == null) {
            alerta(Alert.AlertType.WARNING, "Complete todos los campos obligatorios.");
            return;
        }
        try {
            BigDecimal precio     = new BigDecimal(txtPrecio.getText().trim());
            int        existencia = Integer.parseInt(txtExistencia.getText().trim());
            if (precio.signum() <= 0) {
                alerta(Alert.AlertType.WARNING, "El precio debe ser mayor que cero.");
                return;
            }
            if (existencia < 0) {
                alerta(Alert.AlertType.WARNING, "La existencia no puede ser negativa.");
                return;
            }
            productos.add(new Producto(
                productos.size() + 1,
                txtCodigo.getText().trim(),
                txtNombre.getText().trim(),
                cmbCategoria.getValue(),
                precio, existencia, rutaImagen,
                chkActivo.isSelected()
            ));
            alerta(Alert.AlertType.INFORMATION, "Producto registrado correctamente.");
            limpiar();
        } catch (NumberFormatException e) {
            alerta(Alert.AlertType.ERROR, "Precio o existencia no son valores válidos.");
        }
    }

    private void limpiar() {
        txtCodigo.clear(); txtNombre.clear();
        txtPrecio.clear(); txtExistencia.clear();
        cmbCategoria.getSelectionModel().clearSelection();
        chkActivo.setSelected(true);
        imgProducto.setImage(null);
        rutaImagen = null;
    }

    private void alerta(Alert.AlertType tipo, String mensaje) {
        new Alert(tipo, mensaje, ButtonType.OK).showAndWait();
    }
}
