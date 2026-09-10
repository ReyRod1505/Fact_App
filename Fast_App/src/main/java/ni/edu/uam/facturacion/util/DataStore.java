package ni.edu.uam.facturacion.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ni.edu.uam.facturacion.model.Cargo;
import ni.edu.uam.facturacion.model.Categoria;

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

    private DataStore() { }

    public static ObservableList<Cargo>    getCargos()     { return cargos;     }
    public static ObservableList<Categoria> getCategorias() { return categorias;  }
}
