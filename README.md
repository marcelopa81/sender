sender - É um projeto para avaliação da Oobj, através do programa Escalar. Basicamente, esta aplicação tem a função de receber um arquivo, contendo várias informações, e imprimir um novo documento, manifesto de tranposte, que conterá apenas as informações de interesse, SUB-ITINERÁRIO e SEQ.
O projeto a ser desenvolvido consiste de uma solução de integração nos moldes das soluções conhecidas no mercado. Há 5 componentes principais: sender, integrador, enfileirador, broker e receiver.

Sender - faz a simulação da chamada ao endpoint da API de integração

Para realizar a chamada é necessário realizar autenticação, através do Postman: Authorization>Type>Basic Auth> Username (admin) Password (password).
A requisição é do tipo Post e a url:http://localhost:8080/api/pre-impressao
Integrador - expõe os edpoints da aplicação e salva o conteúdo da requisição em uma pasta de entrada (altere o caminho dessa pasta para uma pasta da sua máquina. Essa alteração deve ser feita no arquivo application.properties da aplicação).

Enfileirador - faz a leitura dos arquivos de entrada e enfileira os dados para o broker. Após a leitura do arquivo da pasta de entrada, o mensmo é movido para a pasta de processados (altere o caminho dessa pasta para uma pasta da sua máquina. Essa alteração deve ser feita no arquivo application.properties da aplicação).

Broker - tem o papel de distribuir as mensagens para as múltiplas instâncias da aplicação. Esse distribuidor (broker) é o ActiveMQ.

Receiver - irá consumir as mensagens do broker e retornar a saída do manifesto de tranporte em uma pasta de saída.
