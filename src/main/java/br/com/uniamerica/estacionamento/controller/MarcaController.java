package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "api/marca")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping("/lista")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(this.marcaRepository.findAll());
    }

    @GetMapping
    public ResponseEntity<?> findMarcaByid(@RequestParam("id") final Long id) {
        final Marca marca = this.marcaRepository.findById(id).orElse(null);

        return marca == null
                ? ResponseEntity.badRequest().body("Error , nao foi encontrado nenhum registro")
                : ResponseEntity.ok(marca);

    }

    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Marca marca){

        try{
            this.marcaRepository.save(marca);
            return ResponseEntity.ok("registro com sucesso");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error" + e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> editar (@RequestParam("id") final Long id, @RequestBody final Marca marca){
        try{
            final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

            if(marcaBanco == null || !marcaBanco.getId().equals(marca.getId())){
                throw new RuntimeException("Nao foi possivel identificar o registro");
            }

            this.marcaRepository.save(marca);
            return ResponseEntity.ok().body("Salvo com sucesso");
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }catch(RuntimeException e){
            return  ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete (@RequestParam("id") final  Long id){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

        List<Modelo> modeloLista = this.marcaRepository.findModelo(marcaBanco);

        if(modeloLista == null){
            this.marcaRepository.delete(marcaBanco);
            return ResponseEntity.ok("Delete som sucesso");
        }else{
            marcaBanco.setAtivo(false);
            return ResponseEntity.ok("Ativo(marca) alterado para false ");
        }
    }
}