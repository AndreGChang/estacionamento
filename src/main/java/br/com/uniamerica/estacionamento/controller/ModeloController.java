package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {

    /**
     * forma de gerar injeçoes
     * 1 formar e @AutoWired no repository
     * 2 forma ciar um contructor que recebo o repository
     */
    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private ModeloService modeloService;

    //contructor para gerar a injecao
    public ModeloController(ModeloRepository modeloRepository){
        this.modeloRepository = modeloRepository;
    }

    /**
     * http://localhost:8080/api/modelo/1
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath (@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Modelo());
    }


    /**
     * http://localhost:8080/api/modelo?id=1
     * @param id
     * @return
     */
    @GetMapping
    public ResponseEntity<?> findByIdParam (@RequestParam("id") final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null
                ? ResponseEntity.badRequest().body("nenhum modelo encontrado.")
                : ResponseEntity.ok(modelo);

    }

    @GetMapping("/lista")
    public ResponseEntity<?> listCompleta(){
        return  ResponseEntity.ok(this.modeloRepository.findAll());
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> buscarModelosAtivos (){
        return ResponseEntity.ok(this.modeloRepository.findByAtivo());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Modelo modelo){

        try{
            this.modeloRepository.save(modelo);
            return ResponseEntity.ok("registro com sucesso");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar (@RequestParam("id") final Long id, @RequestBody final Modelo modelo){
        try{
            this.modeloService.editar(id,modelo);
            return ResponseEntity.ok("Registro atualizado com sucesso");
        }catch(DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deletar (@RequestParam("id") final Long id) {
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);

        this.modeloService.deletar(modeloBanco);

        return ResponseEntity.ok("Registro deletado");
    }

}
