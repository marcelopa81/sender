package br.com.oobj.sender;


import br.com.oobj.sender.service.ArquivoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class Integrador {

    @Value("${sender.diretorio.entrada}")
    private String diretorioEntrada;

    @Value("${sender.diretorio.processados}")
    private String diretorioProcessados;

    private final ArquivoService arquivoService;
    private final Enfileirador enfileirador;

    public Integrador(ArquivoService arquivoService, Enfileirador enfileirador) {
        this.arquivoService = arquivoService;
        this.enfileirador = enfileirador;
    }


    public void processaArquivo(String arquivo, LocalDateTime localDateTime) throws IOException {
        arquivoService.salvaArquivo(arquivo, novoNomeArquivo(localDateTime), diretorioEntrada);
        enfileirador.enviaFila(novoNomeArquivo(localDateTime));

    }

    private String novoNomeArquivo(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "pre-impressao-" + localDateTime.format(formatter) + ".txt";
    }
}
