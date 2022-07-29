package br.com.oobj.sender.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ArquivoEntradaService {

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

    private Path criaPath(String diretorio, String nomeArquivo) {
        return Paths.get(diretorio + nomeArquivo);
    }
}
