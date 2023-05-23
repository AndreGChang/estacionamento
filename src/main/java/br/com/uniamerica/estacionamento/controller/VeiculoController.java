package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.VeiculoService;
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

@Controller
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;


    @Autowired
    private VeiculoService veiculoService;

    @GetMapping("/lista")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(this.veiculoRepository.findAll());
    }

    @GetMapping
    public ResponseEntity<?> findMarcaByid(@RequestParam("id") final Long id) {
        final Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);

        return veiculo == null
                ? ResponseEntity.badRequest().body("Error , nao foi encontrado nenhum registro")
                : ResponseEntity.ok(veiculo);

    }

    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody final Veiculo veiculo){

        try{
            this.veiculoService.cadastrar(veiculo);
            return ResponseEntity.ok("registro com sucesso");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error" + e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> editar (@RequestParam("id") final Long id, @RequestBody final Veiculo veiculo){
        try{

            this.veiculoService.editar(id,veiculo);
            return ResponseEntity.ok().body("Salvo com sucesso");

        }catch(DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }catch(RuntimeException e){
            return  ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete (@RequestParam("id") final  Long id){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);

        this.veiculoService.deletar(veiculoBanco);

        return ResponseEntity.ok("deletado com sucesso");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handlerValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String filedname = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(filedname,errorMessage);


        });

        return errors;
    }



}
