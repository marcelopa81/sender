package br.com.oobj.sender.service;

import lombok.var;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ArquivoService {

    public void salvaArquivo(String arquivo, String nomeArquivo, String diretorio) {
        try {
            Files.write(criaPath(diretorio, nomeArquivo), arquivo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveArquivo(String nomeArquivo, String diretorioOrigem, String diretorioDestino) {
        try {
            Files.move(criaPath(diretorioOrigem, nomeArquivo), criaPath(diretorioDestino, nomeArquivo),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retornaArquivo(List<String> mensagens) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(nomeArquivoRetorno()));

        for (String mensagem : mensagens) {
            bufferedWriter.write(mensagem);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();

    }

    private String nomeArquivoRetorno() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        var path = "C:/Users/Marcelo-Oobj/Desktop/Saida/";
        var caminho = path;
        LocalDateTime localDateTime = LocalDateTime.now();
        return caminho + "pre-impressao-" + localDateTime.format(formatter) + "-retorno" + ".txt";
    }

    private Path criaPath(String diretorio, String nomeArquivo) {
        return Paths.get(diretorio + nomeArquivo);
    }
}
