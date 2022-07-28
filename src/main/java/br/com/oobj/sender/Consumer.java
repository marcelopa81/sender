package br.com.oobj.sender;

import br.com.oobj.sender.service.ArquivoService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Consumer {

    private final List<String> dadosSaida = new ArrayList<>();

    private ArquivoService arquivoService;

    public Consumer(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @JmsListener(destination = "pre_impressao", concurrency = "4")
    public void consumerFila(String string) throws IOException {
        String string1 = removeEspacos(string);
        dadosSaida.add(retornaSubItinerario(string1) + "|" + retornaSequencia(string1));
        if (dadosSaida.size() == 44) {
            arquivoService.retornaArquivo(dadosSaida);
        }

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
