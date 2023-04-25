package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    public ModeloController(ModeloRepository modeloRepository){
        this.modeloRepository = modeloRepository;
    }

    /**
     * http://localhost:8080/api/modelo/1
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Modelo> findByIdPath (@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Modelo());
    }

    /**
     * http://localhost:8080/api/modelo?id=1
     * @param id
     * @return
     */
    @GetMapping
    public ResponseEntity<Modelo> findByIdParam (@RequestParam("id") final Long id){
        return ResponseEntity.ok(new Modelo());
    }

//    @GetMapping
//
//    @PostMapping
//    @PutMapping
//    @DeleteMapping
}
