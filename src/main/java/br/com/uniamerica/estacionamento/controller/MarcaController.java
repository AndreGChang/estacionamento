package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.service.MarcaService;
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

    @Autowired
    private MarcaService marcaService;

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

    @GetMapping("/ativo")
    public ResponseEntity<?> buscarAtivos(){
        return ResponseEntity.ok(this.marcaRepository.findMarcasAtivas());
    }


    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Marca marca){
        try{
            this.marcaService.cadastrar(marca);;
            return ResponseEntity.ok("registro com sucesso");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error" + e.getMessage());
        }





    }

    @PutMapping
    public ResponseEntity<?> editar (@RequestParam("id") final Long id, @RequestBody final Marca marca){
        try{
            //final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

            this.marcaService.editar(id,marca);
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
        try{
            this.marcaService.deletar(marcaBanco);
            return ResponseEntity.ok("Delete com sucesso");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(marcaBanco.getId() +"\n"+ marcaBanco.getNome());
        }



    }
}