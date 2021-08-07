package p2.ecocorporation.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import p2.ecocorporation.model.Funcionario;
import p2.ecocorporation.repository.FuncionarioRepository;


@RestController
public class FuncionarioController {
	@Autowired
	private FuncionarioRepository repository; 
	
	@GetMapping(path = "/funcionario/{codigo}")
	public Optional<Object> consultar(@PathVariable("codigo") Integer codigo) {
		return repository.findById(codigo)
				.map(record -> ResponseEntity.ok().body(record));
	}
	
	@PostMapping(path = "/funcionario/criar")
	public Funcionario salvar(@RequestBody Funcionario funcionario) {
		return repository.save(funcionario);
	}
	
	@DeleteMapping(path = "/funcionario/remover/{codigo}")
	public void remover(@PathVariable("codigo") Integer codigo) {
		repository.deleteById(codigo);
	}
	
	@PutMapping("/funcionario/atualizar/{codigo}")
	public ResponseEntity<?> atualizar(@RequestBody Funcionario funcionario,
	  @PathVariable("codigo") Integer codigo) {
		repository.save(funcionario);
	    return ResponseEntity.ok("Funcionario atualizado com sucesso!");
	}

}
