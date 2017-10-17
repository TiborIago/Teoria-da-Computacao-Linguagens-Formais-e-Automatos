package principal;



import java.util.Scanner;

public class AFD {

public String transicao(String q, char s){
    if(q.equals("q0")){
        if(s=='a')
            return "q2";
        else if(s=='b')
            return "q1";
    }
    else if(q.equals("q1")){
        if(s=='a')
            return "q4";
        else if(s=='b')
            return "q3";
    }
    else if(q.equals("q2")){
        if(s=='a')
            return "q6";
        else if(s=='b')
            return "q5";
    }
    else if(q.equals("q3")){
        if(s=='a')
            return "q8";
        else if(s=='b')
            return "q7";
    }
    else if(q.equals("q4")){
        if(s=='a')
            return "q8";
        else if(s=='b')
            return "q9";
    }
    else if(q.equals("q5")){
        if(s=='a')
            return "q8";
        else if(s=='b')
            return "q10";
    }
    else if(q.equals("q6")){
        if(s=='a')
            return "q8";
        else if(s=='b')
            return "q11";
    }
    else if(q.equals("q7")){
        if(s=='a')
            return "q12";
        else if(s=='b')
            return "q7";
    }
    else if(q.equals("q8")){
        if(s=='a')
            return "q8";
        else if(s=='b')
            return "q8";
    }
    else if(q.equals("q9")){
        if(s=='a')
            return "q13";
        else if(s=='b')
            return "q10";
    }
    else if(q.equals("q10")){
        if(s=='a')
            return "q12";
        else if(s=='b')
            return "q7";
    }
    else if(q.equals("q11")){
        if(s=='a')
            return "q13";
        else if(s=='b')
            return "q10";
    }
    else if(q.equals("q12")){
        if(s=='a')
            return "q14";
        else if(s=='b')
            return "q9";
    }
    else if(q.equals("q13")){
        if(s=='a')
            return "q14";
        else if(s=='b')
            return "q9";
    }
    else if(q.equals("q14")){
        if(s=='a')
            return "q15";
        else if(s=='b')
            return "q11";
    }
    else if(q.equals("q15")){
        if(s=='a')
            return "q15";
        else if(s=='b')
            return "q11";
    }
    return "qe";
}
public String extendida(String q, String w){
    if(w.isEmpty()) //Função base
        return q;
    int len = w.length();//variрvel guarda o tamanho da palavra passada como parРmetro
    String x = w;          //W ж copiado para X. Fazendo com que X seja a mesma string que W
    char a;             //a ж a variрvel que armazenarр o Щltimo sьmbolo da palavra passada como argumento
          
    a = x.charAt(x.length()-1); //ж armazenado o Щltimo sьmbolo de W (ou X. Sсo a mesma palavra atж agora)
     
     
    x = x.substring(0, x.length()-1);
    return transicao(extendida(q, x), a);   //Chama-se a funусo de transiусo utilizando como argumento a extendida e o Щltimo sьmbolo da palavra atual
                                            //Pode-se utilizar o debugger para acompanhar o algoritmo passo-a-passo, basta ativar o debugger
                                            //pelas configuraушes (procurar na internet) e posicionar um break point nessa linha de retorno
}
 
//Funусo para checar se, depois do processamento da palavra, o estado retornado ж um estado final
public boolean ehfinal(String q){
    if(q.equals("q7") || q.equals("q9") || q.equals("q12") || q.equals("q14"))
        return true;
    else
        return false;
}
 
public static void main(String args[]){
    Scanner in = new Scanner(System.in);
    System.out.println("Entre com um palavra");
    String entrada = in.next();
    AFD afd = new AFD();
    if(afd.ehfinal(afd.extendida("q0", entrada)))
        System.out.println("Palavra faz parte da linguagem");
    else
        System.out.println("Palavra não faz parte da linguagem");
     
}
    
}
