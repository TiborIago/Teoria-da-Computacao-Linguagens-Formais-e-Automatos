
package telas;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import modelos.AFD;
import util.AssistenteTransicao;

public class AFDTela implements Initializable {

    private String alfabeto;

    @FXML
    TableView<List<String>> tabelaEstados;

    @FXML
    TextField txtAlfabeto;

    @FXML
    TextField txtQuantEstados;

    @FXML
    ComboBox<String> comboEstadoInicial;
    
    @FXML
    TextField txtEntrada;
    
    @FXML
    Label lblAlfabeto;
    
    @FXML
    Label lblReconhecida;
    
    @FXML
    Button btnReconhecer;
    
    private int quantSimbolos;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnReconhecer.setDisable(true);
    }
    
    
    @FXML
    private void reconhecerPalavra(){
        if(txtEntrada.getText()!=null && !txtEntrada.getText().isEmpty()){
            String resultado = AssistenteTransicao.getAssistente().afd.verificarPalavra(txtEntrada.getText());
            lblReconhecida.setText(resultado);
        }
    }

    @FXML
    private void addEstadoFinal(){
         TablePosition celula = tabelaEstados.getFocusModel().getFocusedCell();
         AssistenteTransicao.getAssistente().afd.addEstadoFinal(celula.getRow());
         
           
    }
    
    @FXML
    private void addAlfabeto() {
        String entrada = txtAlfabeto.getText();
        if (entrada != null && !entrada.isEmpty()) {
            if (entrada.startsWith("{") && entrada.endsWith("}")) {
                String conjunto = entrada.substring(1, entrada.length() - 1);
                if (conjunto.replaceAll("[A-Za-z0-9, ]+", "").length() > 0) {
                    JOptionPane.showMessageDialog(null, "Alfabeto Inválido", "Alfabeto inválido", JOptionPane.ERROR_MESSAGE);
                    alfabeto = null;
                }

                String simbolos[] = conjunto.split(",");
                alfabeto = "{";
                for (String simbolo : simbolos) {
                    if (!simbolo.isEmpty() && !simbolo.contains(" ")) {
                        if (!alfabeto.contains(simbolo)) {
                            alfabeto += simbolo + ",";
                        }
                    }

                }
                alfabeto = alfabeto.substring(0, alfabeto.length() - 1);
                alfabeto += "}";
                txtAlfabeto.setText("");
                lblAlfabeto.setText("Alfabeto inserido: "+ alfabeto);
            } else {
                JOptionPane.showMessageDialog(null, "Alfabeto Inválido", "Alfabeto inválido", JOptionPane.ERROR_MESSAGE);
                alfabeto = null;
            }

        }

    }

    private void preencherComboEstadoInicial() {
        this.comboEstadoInicial.getItems().clear();
        for (int estado : AssistenteTransicao.getAssistente().afd.getEstados()) {
            this.comboEstadoInicial.getItems().add("q" + estado);
        }
    }

    @FXML
    private void addEstados() {
        if (alfabeto != null) {
            try {
                AssistenteTransicao.getAssistente().afd = new AFD(Integer.parseInt(txtQuantEstados.getText()), alfabeto);
                AssistenteTransicao.getAssistente().afd.setInicial(0);

            } catch (Exception er) {
                JOptionPane.showMessageDialog(null, "Por favor, informe um número", "Erro de tipo", JOptionPane.ERROR_MESSAGE);
            }

            try {
                printTable(AssistenteTransicao.getAssistente().afd);
                preencherComboEstadoInicial();
            } catch (Exception e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, informe um alfabeto", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void limparTabela() {
        tabelaEstados.getColumns().remove(0, tabelaEstados.getColumns().size());
        tabelaEstados.getItems().remove(0, tabelaEstados.getItems().size());

    }

    @FXML
    private void estaoInicialSelecionado() {
        if (comboEstadoInicial.getSelectionModel().getSelectedIndex() > -1) {
            AssistenteTransicao.getAssistente().afd.setInicial(Integer.parseInt(comboEstadoInicial.getSelectionModel().getSelectedItem().substring(1)));

            printTable(AssistenteTransicao.getAssistente().afd);

        }
    }

    @FXML
    private void linhaSelecionada(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            TablePosition celula = tabelaEstados.getFocusModel().getFocusedCell();
            String alfabeto = AssistenteTransicao.getAssistente().afd.getAlfabeto();
            alfabeto = alfabeto.substring(1, alfabeto.length() - 1);

            String simbolos[] = alfabeto.split(",");
            this.quantSimbolos = simbolos.length;
            AssistenteTransicao.getAssistente().estadoAtual = celula.getRow();
            AssistenteTransicao.getAssistente().simboloAtual = simbolos[celula.getColumn()-1].charAt(0);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("CelulaTela.fxml"));

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

           
        }

    }
    
    @FXML
    private void recarregarTabela(){
        AFD afd = AssistenteTransicao.getAssistente().afd; 
        printTable(afd);
         if(afd==null || (quantSimbolos*afd.getEstados().length) > afd.getTrasicoes()){
          btnReconhecer.setDisable(true);
         }else{
             btnReconhecer.setDisable(false);
         }
    }

    private void printTable(AFD afd) {
        if(afd!=null){
            limparTabela();
        String alfabeto = afd.getAlfabeto();
        alfabeto = alfabeto.substring(1, alfabeto.length() - 1);

        String simbolos[] = alfabeto.split(",");
        String colunas[] = new String[simbolos.length + 1];
        colunas[0] = "Estados";
        for (int i = 0; i < simbolos.length; i++) {
            colunas[i + 1] = "   " + simbolos[i] + "   ";
        }
        addColumns(tabelaEstados, colunas);

        for (int estado : afd.getEstados()) {
            String linhas[] = new String[simbolos.length + 1];
            linhas[0] = "";
            if (afd.ehFinal(estado)) {
                linhas[0] += "*";
            }

            if (estado == afd.getEstadoIncial()) {
                linhas[0] += "->";
            }

            linhas[0] += "q" + estado;

            for (int i = 0; i < simbolos.length; i++) {
                char simbolo = simbolos[i].charAt(0);
                linhas[i + 1] = "";
                int proximoEstado = afd.transicao(estado, simbolo);
                if (proximoEstado != -1) {
                    linhas[i + 1] = "q" + proximoEstado;

                } else {
                    linhas[i + 1] = "";
                }

            }
            tabelaEstados.getItems().add(Arrays.asList(linhas));
        }

        tabelaEstados.autosize();
        }
        
    }

    private static void addColumns(TableView tb, String[] columnNames) {
        for (int i = 0; i < columnNames.length; i++) {
            final int col = i;
            String columnName = columnNames[i];
            TableColumn<List<String>, String> coluna = new TableColumn<>();
            coluna.setCellValueFactory((param) -> new ReadOnlyObjectWrapper(param.getValue().get(col)));
            coluna.setText(columnName);
            tb.getColumns().add(coluna);
        }
        List<String> lista = new ArrayList<String>();
        lista.stream().forEachOrdered(System.out::println);
        Button b = new Button();
        b.setOnAction(e -> {
        });

    }

}
