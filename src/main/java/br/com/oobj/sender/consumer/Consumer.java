package br.com.oobj.sender.consumer;

import br.com.oobj.sender.service.ArquivoSaidaService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Consumer {

    private final List<String> dadosSaida = new ArrayList<>();

    private ArquivoSaidaService arquivoSaidaService;

    public Consumer( ArquivoSaidaService arquivoSaidaService) {
        this.arquivoSaidaService = arquivoSaidaService;
    }

    @JmsListener(destination = "pre_impressao", concurrency = "4")
    public void consumerFila(String string) throws IOException {
        String string1 = arquivoSaidaService.removeEspacos(string);
        dadosSaida.add(arquivoSaidaService.retornaSubItinerario(string1) + "|" +
                arquivoSaidaService.retornaSequencia(string1));
        if (dadosSaida.size() == 44) {
            arquivoSaidaService.retornaArquivo(dadosSaida);
        }

    }



}
