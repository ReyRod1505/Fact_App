module ni.edu.uam.facturacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;

    exports ni.edu.uam.facturacion.application;
    exports ni.edu.uam.facturacion.model;
    exports ni.edu.uam.practicahilos;
    opens ni.edu.uam.facturacion.controller to javafx.fxml;
    opens ni.edu.uam.facturacion.model to javafx.base;
    opens ni.edu.uam.practicahilos to javafx.fxml;
}