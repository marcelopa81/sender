package br.com.oobj.sender.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Files;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping(value = "/api", produces = "application/json")
@Slf4j
public class ArquivoRestController {

    private final String pathArquivo;

    public ArquivoRestController(@Value("&{aplicacao.path.arquivos}") String pathArquivo){
        this.pathArquivo = pathArquivo;
    }

    @PostMapping("/pre-impressao")
    public ResponseEntity<String> salvaArquivo(@RequestParam ("arquivo") MultipartFile arquivo) {
        log.info("Recebendo arquivo: " + arquivo.getOriginalFilename());
        var caminho = pathArquivo + novoNomeArquivo(LocalDateTime.now());

        log.info("Novo nome: " + caminho);

        try {
            Files.copy(arquivo.getInputStream(), Paths.get(caminho), StandardCopyOption.REPLACE_EXISTING);
            return new ResponseEntity<>("{\"preImpressaoSolicitada\": \"true\"}",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"mensagem\": \"Erro ao carregar arquivo!\"}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private String novoNomeArquivo(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "pre-impressao-" + localDateTime.format(formatter) + ".txt";
    }

}


