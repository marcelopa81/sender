package br.com.oobj.sender.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ArquivoSaidaService {

    private final String pathSaida;

    public ArquivoSaidaService(@Value("${sender.diretorio.saida}") String pathSaida) {
        this.pathSaida = pathSaida;
    }

    public Object retornaSequencia(String string) {
        return string.substring(string.trim().indexOf("SEQ") + 4, string.indexOf("22008",
                string.indexOf("SEQ")));
    }

    public String retornaSubItinerario(String string) {
        try {
            return string.substring(string.indexOf("SUB-ITINERÁRIO") + 15, string.indexOf("22003",
                    string.indexOf("SUB_ITINERÁRIO")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    public String removeEspacos(String string) {
        return string.replaceAll("\\s", "");
    }

    public void retornaArquivo(List<String> mensagens) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(salvaNomeiaArquivoRetorno()));
        bufferedWriter.write("SUB-INTINERÁRIO|SEQUENCIAMENTO" + "\n");
        for (String mensagem : mensagens) {
            bufferedWriter.write(mensagem);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    private String salvaNomeiaArquivoRetorno() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        LocalDateTime localDateTime = LocalDateTime.now();
        return pathSaida + "pre-impressao-" + localDateTime.format(formatter) + "-retorno" + ".txt";
    }
}
