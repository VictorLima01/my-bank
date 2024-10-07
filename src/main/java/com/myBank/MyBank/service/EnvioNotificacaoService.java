package com.myBank.MyBank.service;


import com.myBank.MyBank.entity.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnvioNotificacaoService {

    @Autowired
    private RestTemplate restTemplate;

    // Observacao, API de envio não está funcionando
    public void envioNotificacaoRestApi(Transacao transacao) {
        String url = "https://run.mocky.io/v3/fc2b4c52-0365-45b2-9005-4cd3a7d989a0";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transacao> request = new HttpEntity<>(transacao, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Notificação enviada com sucesso! Resposta: " + response.getBody());
            } else {
                System.out.println("Erro ao enviar notificação: " + response.getStatusCode());
            }

        } catch (Exception e) {
            System.out.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
