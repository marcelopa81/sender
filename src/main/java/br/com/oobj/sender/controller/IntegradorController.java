package br.com.oobj.sender.controller;

import br.com.oobj.sender.ImpressaoResposta;
import br.com.oobj.sender.Integrador;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;


@RestController
@RequestMapping(value = "/api")
public class IntegradorController {

    private Integrador integrador;

    public IntegradorController(Integrador integrador) {
        this.integrador = integrador;
    }

    @PostMapping("/pre-impressao")
    public ResponseEntity<ImpressaoResposta> preImpressao(@RequestBody String arquivo) throws IOException {

        ImpressaoResposta resposta = new ImpressaoResposta();

        if (Objects.equals(arquivo, "")) {
            resposta.setImpressaoSolicidata(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
        } else {
            integrador.processaArquivo(arquivo, LocalDateTime.now());
            resposta.setImpressaoSolicidata(true);
            return ResponseEntity.ok(resposta);
        }

    }
}
