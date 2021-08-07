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

import p2.ecocorporation.model.Amostra;
import p2.ecocorporation.repository.AmostraRepository;

@RestController
public class AmostraController {
	
	@Autowired
	private AmostraRepository repository; 
	
	@GetMapping(path = "/amostra/{codigo}")
	public Optional<Object> consultar(@PathVariable("codigo") Integer codigo) {
		return repository.findById(codigo)
				.map(record -> ResponseEntity.ok().body(record));
	}
	
	@PostMapping(path = "/amostra/criar")
	public Amostra salvar(@RequestBody Amostra amostra) {
		return repository.save(amostra);
	}
	
	@DeleteMapping(path = "/amostra/remover/{codigo}")
	public void remover(@PathVariable("codigo") Integer codigo) {
		repository.deleteById(codigo);
	}
	
	@PutMapping("/amostra/atualizar/{codigo}")
	public ResponseEntity<?> atualizar(@RequestBody Amostra amostra,
	  @PathVariable("codigo") Integer codigo) {
		repository.save(amostra);
	    return ResponseEntity.ok("Amostragem atualizado com sucesso!");
	}

}
