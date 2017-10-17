
package telas;

import model.Alfabeto;
import model.Palavra;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InterfaceMainController implements Initializable {

    //OBJETOS DA INTERFACE
    //primeira parte
    @FXML
    private Button btAdicionar;
    @FXML
    private TextField tbAlfabeto;
    @FXML
    private Label logEntrada;

    @FXML
    private TableView<Alfabeto> tvAlfabetos;
    @FXML
    private TableColumn<Alfabeto, String> clAlfabetos;

    //segunda parte
    @FXML
    private Button btVerificar;
    @FXML
    private Button btListPre;
    @FXML
    private Button btListSufixo;
    @FXML
    private Button btListSub;
    @FXML
    private TextField tbPalavra;
    @FXML
    private Label logVerifica;

    //ESTRUTURA    
    ObservableList<Alfabeto> listaAlfabetos;

    //EVENTOS
    public void adicionarAlfabeto() { //adiciona um alfabeto na lista
        try {
            addAlfabeto(new Alfabeto(tbAlfabeto.getText()));
            logEntrada.setText("");
        } catch (IllegalArgumentException a) { //lança essa exception se a lista já tiver o alfabeto
            logEntrada.setText(a.getMessage());
        }
        tbAlfabeto.setText("");
    }

    public void addAlfabeto(Alfabeto a) { //confere se o alfabeto já está na lista e adiciona se não estiver
        for (Alfabeto x : listaAlfabetos) {
            if (a.compareTo(x)) {
                throw new IllegalArgumentException("Alfabeto existente!");
            }
        }
        listaAlfabetos.add(a);

    }

    public void verificaPalavra() { //verifica quais alfabetos a palavra pertence
        try {
            if (listaAlfabetos.size() > 0) {
                String palavra = tbPalavra.getText();
                List<String> list = new ArrayList<>();
                for (Alfabeto a : listaAlfabetos) {
                    if (a.palavraPertence(palavra)) {
                        list.add("Alfabeto: " + a.toString());
                    }
                }
                if (list.size() > 0) {
                    logVerifica.setText("");
                    exibeLista("Palavra \"" + palavra + "\" pretence ao:", list);
                } else {
                    logVerifica.setText("Não pertence aos alfabetos cadastrados");
                }
            } else {
                logVerifica.setText("Não há alfabeto cadastrado");
            }
        } catch (Exception e) {
            System.out.println("Não há alfabeto cadastrado");
        }
    }

    public void listaPrefixos() {
        logVerifica.setText("");
        Palavra p = new Palavra(tbPalavra.getText());
        List<String> lista = p.getPrefixos();
        //printando no terminal
//        for (String s : lista) {
//            System.out.println(s);
//        }
        exibeLista("Prefixos de \"" + p.getPalavra() + "\"", lista);
    }

    public void listaSufixos() {
        logVerifica.setText("");
        Palavra p = new Palavra(tbPalavra.getText());
        List<String> lista = p.getSufixos();
//        for (String s : lista) {
//            System.out.println(s);
//        }
        exibeLista("Sufixos de \"" + p.getPalavra() + "\"", lista);
    }

    public void listaSubpalavras() {
        logVerifica.setText("");
        Palavra p = new Palavra(tbPalavra.getText());
        List<String> lista = p.getSubPalavras();
//        for (String s : lista) {
//            System.out.println(s);
//        }
        exibeLista("Subpalavras de \"" + p.getPalavra() + "\"", lista);
    }

    private void exibeLista(String titulo, List<String> lista) { //método para inicializar a janela de prefixos, sufixos, subpalavras
        //ou alfabetos que a palavra pertence.
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ListaDePalavras.fxml"));

            AnchorPane janela = (AnchorPane) loader.load();

            ListaDePalavrasController controlador = loader.getController();
            controlador.setLista(lista);

            Stage palco = new Stage();
            palco.setTitle(titulo);
            Scene cena = new Scene(janela);
            palco.setScene(cena);
            palco.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletaAlfabeto(MouseEvent m) { //deleta alfabeto selecionado quando o botão do meio do mouse for acionado
        if (m.getButton() == MouseButton.MIDDLE) {
            ObservableList<Integer> index = tvAlfabetos.getSelectionModel().getSelectedIndices();
            for (Integer i : index) { //correção do bug que retornava índice negativo sem motivo na linha acima
                if (i < 0) {
                    deletaAlfabeto(m);
                    return;
                }
            }
            int ind[] = new int[index.size()];
            int b = 0;
            for (Integer i : index) {
                ind[b++] = (int) i;
            }
            for (int i = (ind.length - 1); i >= 0; i--) {

                listaAlfabetos.remove(ind[i]);
            }
        }
    }

    public void unirAlfabeto() {
        ObservableList<Integer> index = tvAlfabetos.getSelectionModel().getSelectedIndices();
        for (Integer i : index) {//correção do bug que retornava índice negativo sem motivo na linha acima
            if (i < 0) {
                unirAlfabeto();
                return;
            }
        }
        if (index.size() < 2) {
            logVerifica.setText("Selecione dois alfabetos ou mais!");
        } else {
            int ind[] = new int[index.size()];
            int b = 0;
            for (Integer i : index) {
                ind[b++] = (int) i;
            }
            Alfabeto a = new Alfabeto(listaAlfabetos.get(ind[0]), listaAlfabetos.get(ind[1]));
            for (int p = 3; p < index.size(); p++) {
                a = new Alfabeto(a, listaAlfabetos.get(ind[p - 1]));
            }
            try {
                addAlfabeto(a);
                for (int i = (ind.length - 1); i >= 0; i--) {

                    listaAlfabetos.remove(ind[i]);
                }
                logVerifica.setText("");
            } catch (IllegalArgumentException e) {
                logVerifica.setText("União já existe!");
            }

        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaAlfabetos = tvAlfabetos.getItems(); //setando mesma referencias para listas de alfabetos
        clAlfabetos.setCellValueFactory(a -> a.getValue().toStringProp()); //setando o que será exibito na lista
        tvAlfabetos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //autorizando seleção multipla na lista de alfabetos

//        
    }

}
