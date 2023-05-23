package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.Recibo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping("/lista")
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

    @GetMapping("/abertas")
    public ResponseEntity<?> buscarAbertas (){
        return ResponseEntity.ok(this.movimentacaoService.abertas());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Movimentacao movimentacao){
        try{
            this.movimentacaoService.cadastrar(movimentacao);
            return ResponseEntity.ok("registro com sucesso");
        }catch (RuntimeException e){
            return  ResponseEntity.badRequest().body("Error " + e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> editar (@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try{

            this.movimentacaoService.editar(id,movimentacao);
            return ResponseEntity.ok().body("Salvo com sucesso");

        }catch(DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }catch(RuntimeException e){
            return  ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

    @PutMapping("/saida")
    public ResponseEntity<?> saida (@RequestParam("id")final Long id){
        try{
             Recibo dale = this.movimentacaoService.saida(id);
            return ResponseEntity.ok(dale);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }


    @DeleteMapping
    public ResponseEntity<?> delete (@RequestParam("id") final  Long id){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);

        try{
            this.movimentacaoService.deletar(movimentacaoBanco);
            return ResponseEntity.ok("Ativo(movimentacao) alterado para false");
        }catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }

    }


}
