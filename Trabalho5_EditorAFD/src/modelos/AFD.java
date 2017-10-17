package modelos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AFD {
    private int estados[];
    private int estadoInicial;
    private String estadosFinais;
    private HashMap<String,Integer> transicoes;
    private String alfabeto;
    
    public AFD(int quantidadeEstados, String alfabeto){
        estados = new int[quantidadeEstados];
        for(int i=0; i<estados.length;i++)
            estados[i] = i;
        transicoes = new HashMap<>();
        this.alfabeto = alfabeto;
        this.estadosFinais = "";
                
       
    }

    
    public int getTrasicoes(){
        return this.transicoes.size();
    }
    public String getAlfabeto() {
        return alfabeto;
    }

    
    public int[] getEstados(){
        return this.estados;
    }
    
    public void setInicial(int estado){
        this.estadoInicial = estado;
    }
    
    public int getEstadoIncial(){
        return this.estadoInicial;
    }
    
    public void addEstadoFinal(int estadoFinal){
        //add um estado em uma lista de estados finais(string) 
        if(estadosFinais.contains(";q"+estadoFinal+";")){
            //caso um estado seja reinserido na lista de estados finais, ele eh removido e nao add
            estadosFinais = estadosFinais.replaceAll(";q"+estadoFinal+";", "");
        }else{
            //caso n esteja na lista, eh inserido
            estadosFinais = estadosFinais+";q"+estadoFinal+";";
        }
    }
   //verifica se o estado recebido esta dentro da lista de estados finais 
   public boolean ehFinal(int estado){ 
        if(estadosFinais.contains(";q"+estado+";"))
            return true;
        return false;
    }
    // pega o estado atual, o proximo estado e o simbolo que ta sendo passado
    public void addTransicao(int estadoOrigem, int estadoDestino, char simbolo){
        if(alfabeto.contains(""+simbolo)){
//guarda a transicao em uma lista de transicoes(hash (chave-valor) guarda string como chave e valor como inteiro)
            transicoes.put("q"+estadoOrigem+"->"+simbolo,estadoDestino); 
        }
    }
    //passa o estado atual e o simbolo da transicao
    public int transicao(int q, char simbolo){ 
        if(alfabeto.contains(""+simbolo)){
            //verifica se existe um proximo estado p/ transicao recebida(estados+simb)
            Integer proximo = transicoes.get("q"+q+"->"+simbolo);
            if(proximo==null) //verifica se existe transicao cadastrada else retorna -1
                return -1;
            return proximo;
        }
        return -1;
    }
    
    private int estendida(int q, String w){
        if(w.isEmpty()) 
        return q;
    String x = w;      
    char a;
    // O a eh o ultimo simbolo da direita
    a = x.charAt(x.length()-1); 
   // elimina sempre o ultimo simbolo da palavra
    x = x.substring(0, x.length()-1);
    
    return transicao(estendida(q, x), a);  
    }
    
    public String verificarPalavra(String entrada){
        // passa a entrada fornecida e verifica se o resultado da extendida eh final
        if(ehFinal(estendida(this.estadoInicial, entrada))) 
            return "Palavra reconhecida pelo Autômato cadastrado";
        return "Palavra inválida";
    }   
}