package br.com.oobj.sender;


import br.com.oobj.sender.service.ArquivoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class Enfileirador {

    @Value("${spring.active-mq.queue}")
    private String queue;

    @Value("${sender.diretorio.entrada}")
    private String diretorioEntrada;

    @Value("${sender.diretorio.processados}")
    private String diretorioProcessados;

    private final JmsTemplate jmsTemplate;
    private final ArquivoService arquivoService;
    private Consumer consumer;

    public Enfileirador(JmsTemplate jmsTemplate, ArquivoService arquivoService, Consumer consumer) {
        this.jmsTemplate = jmsTemplate;
        this.arquivoService = arquivoService;
        this.consumer = consumer;
    }

    public void enviaFila(String nomeArquivo) throws IOException {
        List<String> mensagens = new ArrayList<>();
        String mensagem = "";
        Scanner scanner = new Scanner(new FileReader(String.valueOf(Paths.get(diretorioEntrada + nomeArquivo))));
        StringBuffer stringBuffer = new StringBuffer();

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            stringBuffer.append(linha);
            stringBuffer.append("\n");

            if (linha.equals("25000;STAPLE_TOP_LEFT")) {
                mensagem = stringBuffer.toString();
                mensagens.add(mensagem);
                stringBuffer.setLength(0);
                jmsTemplate.convertAndSend(queue, mensagem);
            }
        }
        scanner.close();
        arquivoService.moveArquivo(nomeArquivo, diretorioEntrada, diretorioProcessados);

    }
}




