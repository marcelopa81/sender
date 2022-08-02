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
    public void consumerFila(String mensagensProcessadas) throws IOException {
        String conteudo = arquivoSaidaService.removeEspacos(mensagensProcessadas);
        dadosSaida.add(arquivoSaidaService.retornaSubItinerario(conteudo) + "|" +
                arquivoSaidaService.retornaSequencia(conteudo));
        if (dadosSaida.size() == 44) {
            arquivoSaidaService.retornaArquivo(dadosSaida);
        }
    }

}
