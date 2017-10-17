package telas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ListaDePalavrasController implements Initializable {

    //ATRIBUTOS DA INTERFACE GRAFICA
    @FXML
    private ListView<String> lvLista;

    //LISTA ENVIADA PELA INTERFACE PRINCIPAL
    private List<String> lista;

    //METODOS
    public void setLista(List<String> lista) {
        this.lista = lista;
        ObservableList<String> aux = FXCollections.observableArrayList(lista);
        lvLista.setItems(aux); //adiciona lista recebida como parâmetros na lista da interface
    }

    public void fecharComEsc(KeyEvent t) {

        if (t.getCode() == KeyCode.ESCAPE) {
            Stage s = (Stage) lvLista.getScene().getWindow();
            s.close();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        
    }

}
