package ni.edu.uam.fast_app.controller;

import javafx.fxml.FXML;
import javafx.application.Platform;

public class MenuPrincipalController {

    @FXML
    protected void onIrAProductosClick() {
        System.out.println("Navegando a la vista de productos...");
    }

    @FXML
    protected void onSalirClick() {
        Platform.exit();
    }
}
