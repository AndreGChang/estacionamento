package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping(value = "/lista")
    public ResponseEntity<?> listar (){
        return ResponseEntity.ok(this.movimentacaoRepository.findAll());
    }

    @GetMapping
    public ResponseEntity<?> findbyId (@RequestParam("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        return movimentacao == null
                ? ResponseEntity.badRequest().body("Error nao foi encontrado o registro")
                : ResponseEntity.ok(movimentacao);
    }


    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Movimentacao movimentacao){

        try{
            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("registro com sucesso");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error" + e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> editar (@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try{
            final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);

            if(movimentacaoBanco == null || !movimentacaoBanco.getId().equals(movimentacao.getId())){
                throw new RuntimeException("Nao foi possivel identificar o registro");
            }

            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok().body("Salvo com sucesso");
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }catch(RuntimeException e){
            return  ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }


    @DeleteMapping
    public ResponseEntity<?> delete (@RequestParam("id") final  Long id){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);

        List<Movimentacao> movimentacaoLista = this.movimentacaoRepository.findSaidas();
        try{
            movimentacaoBanco.setAtivo(false);
            return ResponseEntity.ok("Ativo(marca) alterado para false ");
        }catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }


    }

}
