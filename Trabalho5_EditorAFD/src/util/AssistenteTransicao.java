/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import modelos.AFD;


/**
 * 
 * Singleton para transição
 */
public class AssistenteTransicao {
    private static final AssistenteTransicao assistente = new AssistenteTransicao();
    public AFD afd;

    public int estadoAtual;
    public char simboloAtual;
    public static AssistenteTransicao getAssistente() {
        return assistente;
    }
    
    private AssistenteTransicao (){
        
    }
}
