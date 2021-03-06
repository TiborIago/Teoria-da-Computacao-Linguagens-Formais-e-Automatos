
package telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelos.AFN;




public class AFNTela implements Initializable {

    AFN afn;
    @FXML
    TextField txtPalavra;
    @FXML
    Label lblResultado;
    
    @FXML
    public void reconhecerPalavra(){
        if(txtPalavra.getText().length()>0){
            if(afn.ehfinal(afn.extendida("q0", txtPalavra.getText()))){
                lblResultado.setText("Palavra reconhecida pela linguagem");
            }else{
                lblResultado.setText("Essa palavra não é reconhecida pela linguagem");
            }
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afn = new AFN();
    }

}
