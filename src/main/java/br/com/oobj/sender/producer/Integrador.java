package br.com.oobj.sender.producer;


import br.com.oobj.sender.service.ArquivoEntradaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class Integrador {

    @Value("${sender.diretorio.entrada}")
    private String diretorioEntrada;

    private final ArquivoEntradaService arquivoEntradaService;
    private final Enfileirador enfileirador;

    public Integrador(ArquivoEntradaService arquivoEntradaService, Enfileirador enfileirador) {
        this.arquivoEntradaService = arquivoEntradaService;
        this.enfileirador = enfileirador;
    }


    public void processaArquivo(String arquivo, LocalDateTime localDateTime) throws IOException {
        arquivoEntradaService.salvaArquivo(arquivo, novoNomeArquivo(localDateTime), diretorioEntrada);
        enfileirador.enviaFila(novoNomeArquivo(localDateTime));
    }

    private String novoNomeArquivo(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "pre-impressao-" + localDateTime.format(formatter) + ".txt";
    }
}
