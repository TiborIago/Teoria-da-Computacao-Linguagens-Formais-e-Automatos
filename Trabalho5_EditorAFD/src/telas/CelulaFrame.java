
package telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import util.AssistenteTransicao;

public class CelulaFrame implements Initializable {

    @FXML
    Label lblEstado;

    @FXML
    Label lblSimbolo;

  
    @FXML
    ComboBox<String> comboEstados;

    private void preencherCombo() {
            for (int estado : AssistenteTransicao.getAssistente().afd.getEstados()) {
                this.comboEstados.getItems().add("q" + estado);
            }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherCombo();
        lblEstado.setText("Estado: q"+AssistenteTransicao.getAssistente().estadoAtual);
        lblSimbolo.setText("Símbolo: "+AssistenteTransicao.getAssistente().simboloAtual);

    }

    @FXML
    private void addTransicao() {
        if (comboEstados.getSelectionModel().getSelectedIndex() > -1) {
            int estadoDestino = Integer.parseInt(comboEstados.getSelectionModel().getSelectedItem().substring(1));
            AssistenteTransicao.getAssistente().afd.addTransicao(AssistenteTransicao.getAssistente().estadoAtual, estadoDestino, AssistenteTransicao.getAssistente().simboloAtual);
            Stage janela = (Stage) this.comboEstados.getScene().getWindow();

            janela.close();
        }
    }

    @FXML
    private void cancelar() {
        Stage janela = (Stage) this.comboEstados.getScene().getWindow();

        janela.close();

    }

}
