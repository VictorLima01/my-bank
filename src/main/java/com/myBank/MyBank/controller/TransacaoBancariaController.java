package com.myBank.MyBank.controller;

import com.myBank.MyBank.dto.BodyTransferenciaBancariaDTO;
import com.myBank.MyBank.exceptions.TransacaoBancariaException;
import com.myBank.MyBank.service.TransacaoBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("myBank/transacao")
public class TransacaoBancariaController {

    @Autowired
    TransacaoBancariaService transacaoBancariaService;

    @PostMapping("/transferir-para-conta")
    public ResponseEntity<?> transferenciaBancaria(@RequestBody @Valid BodyTransferenciaBancariaDTO data){
        try {
            transacaoBancariaService.transferir(data);
            return ResponseEntity.ok("Transferência realizada com sucesso.");
        } catch (TransacaoBancariaException e) {
            return ResponseEntity.badRequest().body("Erro na transferência: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro inesperado: " + e.getMessage());
        }
    }

    /*@GetMapping("/reverter-pagamento")
    public ResponseEntity reverterPagamento(@RequestBody @Valid BodyTransferenciaBancariaDTO data){

    }*/
}
