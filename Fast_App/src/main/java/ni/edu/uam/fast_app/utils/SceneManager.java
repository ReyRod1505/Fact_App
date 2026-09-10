package ni.edu.uam.fast_app.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.*;
import java.io.IOException;

public final class SceneManager {
    private SceneManager() { }

    public static void abrirVentana(String recurso, String titulo) throws IOException {
        var url = SceneManager.class.getResource(recurso);
        if (url == null) throw new IOException("FXML no encontrado: " + recurso);

        Stage stage = new Stage();
        stage.setTitle(titulo);
        Scene scene = new Scene(new FXMLLoader(url).load());
        scene.getStylesheets().add(
            SceneManager.class.getResource("/ni.edu.uam.fast_app/css/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
