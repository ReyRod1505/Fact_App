module ni.edu.uam.fast_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.fast_app to javafx.fxml;
    exports ni.edu.uam.fast_app;
}