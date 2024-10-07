package com.myBank.MyBank.service;

import com.myBank.MyBank.dto.BodyReverterPagamentosDTO;
import com.myBank.MyBank.dto.BodyTransferenciaBancariaDTO;
import com.myBank.MyBank.entity.Contas;
import com.myBank.MyBank.entity.Transacao;
import com.myBank.MyBank.entity.Users;
import com.myBank.MyBank.exceptions.TransacaoBancariaException;
import com.myBank.MyBank.repository.ContasRepository;
import com.myBank.MyBank.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoBancariaService {

    @Autowired
    ContasRepository contasRepository;

    @Autowired
    TransacaoRepository transacaoRepository;


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
              contasAtivasDestinatario.setSaldo(data.getValorTransferencia() + contasAtivasDestinatario.getSaldo());
              contasRepository.save(contasAtivasDestinatario);

              contaRemetente.setSaldo(contaRemetente.getSaldo() - data.getValorTransferencia());
              contasRepository.save(contaRemetente);
              // Salvo a transacao
                try {
                    Transacao transacao = new Transacao.Builder()
                            .withAgenciaDestinatario(contasAtivasDestinatario.getAgencia())
                            .withAgenciaRemetente(contaRemetente.getAgencia())
                            .withValorTransferencia(data.getValorTransferencia())
                            .build();
                    transacaoRepository.save(transacao);
                }catch (Exception e){
                    throw new TransacaoBancariaException("Erro ao salvar transferencia bancaria! " + e.getMessage());
                }
            }else{
                throw new TransacaoBancariaException("Contas Bancarias vazias");
            }

        }else{
            throw new TransacaoBancariaException("Contas Bancarias nao existem para agencia Remetente e ou destinataria informada");
        }
    }

    public void reverterPagamento(BodyReverterPagamentosDTO data) {
        // Achar a transferencia
        Optional<Transacao> findTransacaoById = transacaoRepository.findById(data.getIdTransacao());

        if(findTransacaoById.isPresent()){
            Contas contasAtivasDestinatario = contasRepository.findAgencia(findTransacaoById.get().getAgenciaDestinatario());
            Contas contasAtivasRemetente = contasRepository.findAgencia(findTransacaoById.get().getAgenciaRemetente());
            Double valorTranferencia = findTransacaoById.get().getValorTransferencia();

            if(contasAtivasDestinatario != null || contasAtivasRemetente != null){
                contasAtivasRemetente.setSaldo(valorTranferencia + contasAtivasRemetente.getSaldo());
                contasAtivasDestinatario.setSaldo(contasAtivasDestinatario.getSaldo() - valorTranferencia);

                contasRepository.save(contasAtivasRemetente);
                contasRepository.save(contasAtivasDestinatario);
            }else{
                throw new TransacaoBancariaException("Conta do remetente ou do destinatário estão inativas, verifique por favor");
            }

        }else{
            throw new TransacaoBancariaException("Não foi possível encontrar a transação");
        }
    }

    public Users obterUsuarioLogado() {
        return UsuarioCache.getInstance().getUsers();
    }

    public List<Transacao> trazerTodasTransacoes() {
        try {
            return transacaoRepository.findAll();
        }catch (Exception e){
            throw new TransacaoBancariaException("Não foi possível encontrar a transação");
        }
    }
}
