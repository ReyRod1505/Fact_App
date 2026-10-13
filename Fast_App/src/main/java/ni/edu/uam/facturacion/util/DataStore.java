package ni.edu.uam.facturacion.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ni.edu.uam.facturacion.model.Cargo;
import ni.edu.uam.facturacion.model.Categoria;
import ni.edu.uam.facturacion.model.Empleado;
import ni.edu.uam.facturacion.model.Producto;

import java.math.BigDecimal;

/**
 * Almacén de datos en memoria compartido entre controladores durante la sesión.
 */
public final class DataStore {

    private static final ObservableList<Cargo> cargos = FXCollections.observableArrayList(
        new Cargo(1, "Gerente",    "Gerente general"),
        new Cargo(2, "Cajero",     "Atiende la caja"),
        new Cargo(3, "Bodeguero",  "Maneja el inventario")
    );

    private static final ObservableList<Categoria> categorias = FXCollections.observableArrayList(
        new Categoria(1, "Alimentos", true),
        new Categoria(2, "Bebidas",   true),
        new Categoria(3, "Limpieza",  true)
    );

    private static final ObservableList<Producto> productos = FXCollections.observableArrayList(
        new Producto(1, "P001", "Arroz Faisán 1lb", categorias.get(0), new BigDecimal("25.50"), 50, true),
        new Producto(2, "P002", "Coca Cola 2L", categorias.get(1), new BigDecimal("45.00"), 30, true),
        new Producto(3, "P003", "Cloro Macuelizo 1L", categorias.get(2), new BigDecimal("18.00"), 15, false)
    );

    private static final ObservableList<Empleado> empleados = FXCollections.observableArrayList();

    private DataStore() { }

    public static ObservableList<Cargo>     getCargos()     { return cargos;     }
    public static ObservableList<Categoria> getCategorias() { return categorias;  }
    public static ObservableList<Producto>  getProductos()  { return productos;  }
    public static ObservableList<Empleado>  getEmpleados()  { return empleados;  }
}
