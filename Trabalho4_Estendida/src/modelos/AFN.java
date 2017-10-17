
package modelos;



import java.util.Scanner;


public class AFN {

    //transicao do afn
public String transicao(String q, char s){
    
    //somente um estado como entrada e um simbolo
    //retorna um conjunto de estados separados por virgula caso haja uma transicao para o simbolo recebido
    switch(q){
        case "q0":
            if(s=='a')
                return "q0,q1";
            else
                if(s=='b')
                    return "q0";
                else
                    return "";
        case "q1":
            if(s=='a' || s=='b')
                return "q2";
            else
                return "";
        case "q2":
            if(s=='a' || s=='b')
                return "q3";
            else
                return "";
        case "q3":
            return "";
        default:
            return "";       
    }
}
public String extendida(String q, String w){
    if(w.isEmpty()) 
        return q;
    String x = w;        
    char a;             
    
    a = x.charAt(x.length()-1);
    x = x.substring(0, x.length()-1);
    //separa o conjunto de estados em um vetor de strings
    String estados[] = q.split(",");
    //string utilizada para unir resultados das funcoes extendidas
    String uniao = "";
    for(int i=0; i< estados.length;i++){ //percorre todos os estados do conjunto de estados de entrada
        String resultadoExtendida = extendida(estados[i], x); 
        for(String q1: resultadoExtendida.split(",")) //for para percorrer resultado da funcao extendida
            uniao+= transicao(q1, a)+","; //realiza a transicao p/ cada estado do conj obtido e une os resultados separando-os por uma vírgula
    }
    uniao = uniao.substring(0,uniao.length()-1); 
    return uniao;   
}
 
public boolean ehfinal(String q){
    if(q.contains("q3"))
        return true;
    else
        return false;
}
}