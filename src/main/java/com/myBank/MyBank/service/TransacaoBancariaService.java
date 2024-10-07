package com.myBank.MyBank.service;

import com.myBank.MyBank.dto.BodyTransferenciaBancariaDTO;
import com.myBank.MyBank.entity.Contas;
import com.myBank.MyBank.entity.Users;
import com.myBank.MyBank.exceptions.TransacaoBancariaException;
import com.myBank.MyBank.repository.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoBancariaService {

    @Autowired
    ContasRepository contasRepository;

    public void transferir(BodyTransferenciaBancariaDTO data) {
        // Remetente
        Users usuarioLogado = obterUsuarioLogado();
        boolean validaSaldoConta = usuarioLogado.getContas().stream()
                .anyMatch(conta -> conta.getSaldo() > 0);
        Optional<Contas> contaBancariaRemetenteOp = usuarioLogado.getContas().stream()
                .filter(conta -> conta.getStatus().equals(Contas.StatusConta.ATIVA))
                .findFirst();

        // Destinatário
        Contas contasAtivasDestinatario = contasRepository.findAgencia(data.getAgenciaDestinataria());

        if(contaBancariaRemetenteOp.isPresent()  && contasAtivasDestinatario != null){
            Contas contaRemetente = contaBancariaRemetenteOp.get();

            if(validaSaldoConta){
                // Altero o saldo
              contasAtivasDestinatario.setSaldo(data.getValorTransferencia());
              contasRepository.save(contasAtivasDestinatario);

              contaRemetente.setSaldo(contaRemetente.getSaldo() - data.getValorTransferencia());
              contasRepository.save(contaRemetente);
            }else{
                throw new TransacaoBancariaException("Contas Bancarias vazias");
            }

        }else{
            throw new TransacaoBancariaException("Contas Bancarias nao existem para agencia Remetente e ou destinataria informada");
        }
    }


    public Users obterUsuarioLogado() {
        return UsuarioCache.getInstance().getUsers();
    }
}
