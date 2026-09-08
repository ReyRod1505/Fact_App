module ni.edu.uam.fast_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    exports ni.edu.uam.fast_app.application;
    exports ni.edu.uam.fast_app.model;
    opens ni.edu.uam.fast_app.controller to javafx.fxml;
    opens ni.edu.uam.fast_app.model to javafx.base;
}