package p2.ecocorporation.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping(path = "/amostra/desempenho")
	public String consultarDesempenhoF(@RequestParam Map<String,String> params) {
		
		String tipoConsulta = "";
		String busca = "";
		
		for (Entry<String, String> entry : params.entrySet()) 
		{
		    System.out.println(entry.getKey() + "/" + entry.getValue());
		    tipoConsulta = entry.getKey();
		    busca = entry.getValue();
		}
		
		Double cota = 0.0;
		Double vendas = 0.0;
		Double desempenho = 0.0;
		String trimestre = "";
		String regiao = "";
		String funcionario;
		String result = "";
		String resultTemp = "";
		List<Amostra> amostra = null;
		
		if(tipoConsulta.equalsIgnoreCase("funcionario"))
		{
			result = "Funcionario: " + busca;
			amostra = repository.findByFuncionario(busca);
		}
		else if(tipoConsulta.equalsIgnoreCase("regiao"))
		{
			result = "Regiao: " + busca;
			amostra = repository.findByRegiao(busca);
		}
		else
		{
			result = "Trimestre: " + busca;
			amostra = repository.findByTrimestre(busca);
		}

		for(Amostra registro : amostra)
		{
			cota = registro.getCota();
			vendas = registro.getVendas();
			trimestre = registro.getTrimestre();
			funcionario = registro.getFuncionario();
			regiao = registro.getRegiao();
			desempenho = desempenho + ((vendas*100) / cota);
			
			if(tipoConsulta.equalsIgnoreCase("funcionario"))
			{
				resultTemp = resultTemp + "\n\nTrimestre: " + trimestre + "\nCota: " + cota + "\nVendas: " + vendas + "\nRegiao: " + regiao;
			}
			else if(tipoConsulta.equalsIgnoreCase("regiao"))
			{
				resultTemp = resultTemp + "\n\nTrimestre: " + "" + "\nCota: " + cota + "\nVendas: " + vendas + "\nFucionario: " + funcionario;
			}
			else
			{
				resultTemp = resultTemp + "\n\nCota: " + cota + "\nVendas: " + vendas + "\nFuncionario: " + funcionario + "\nRegiao: "+ regiao;
			}
		}
		result = result + "\nDesempenho: " + new DecimalFormat("#.##").format(desempenho) + "%" + 
				"\n\n-----HISTORICO-----" + resultTemp + "\n-------------------";
		return result;
	}

}
