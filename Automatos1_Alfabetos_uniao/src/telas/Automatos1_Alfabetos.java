package telas;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Automatos1_Alfabetos extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args); //lançando a interface gráfica

    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader graficInterface = new FXMLLoader();
            graficInterface.setLocation(getClass().getResource("InterfaceMain.fxml"));
            AnchorPane principal = (AnchorPane) graficInterface.load();

            Scene cena = new Scene(principal);
            //cena.getStylesheets().add("automatos1_alfabetos/view/Estilo.css");
            primaryStage.setScene(cena);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
