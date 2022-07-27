package br.com.oobj.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Value("${sender.diretorio.saida}")
    private String diretoriosaida;


    @JmsListener(destination = "pre_impressao", concurrency = "4")
    public void consumerFila(String string) {
        String string1 = removeEspacos(string);
        System.out.println(retornaSubItinerario(string1) + "|" + retornaSequencia(string1));
    }

    private Object retornaSequencia(String string) {
        return string.substring(string.trim().indexOf("SEQ") + 4, string.indexOf("22008",
                string.indexOf("SEQ")));
    }

    private String retornaSubItinerario(String string) {
        try {
            return string.substring(string.indexOf("SUB-ITINERÁRIO") + 15, string.indexOf("22003",
                    string.indexOf("SUB_ITINERÁRIO")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    private String removeEspacos(String string) {
        return string.replaceAll("\\s", "");
    }

}
