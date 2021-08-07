package p2.ecocorporation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
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
	public String consultarDesempenho(@RequestParam Map<String,String> params) {
		
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
				resultTemp = resultTemp + "\n\nTrimestre: " + trimestre + "\nCota: " + cota + "\nVendas: " + vendas + "\nFucionario: " + funcionario;
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
	
	@GetMapping(path = "/amostra/geraRelatorio")
	public String geraRelatorio() {
		
		List<Amostra> amostra = (List<Amostra>) repository.findAll();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet abaManual = workbook.createSheet("Relatorio Geral");
		
		HSSFFont fsTitle = workbook.createFont();
		fsTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fsTitle.setColor(HSSFColor.WHITE.index);
		
		HSSFCellStyle sTitle = workbook.createCellStyle();
		sTitle.setFont(fsTitle);
		sTitle.setBorderBottom((short) 1);
		sTitle.setBorderLeft((short) 1);
		sTitle.setBorderRight((short) 1);
		sTitle.setBorderTop((short) 1);
		sTitle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		sTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		HSSFCellStyle sLinha1 = workbook.createCellStyle();
		sLinha1.setBorderBottom((short) 1);
		sLinha1.setBorderLeft((short) 1);
		sLinha1.setBorderRight((short) 1);
		sLinha1.setBorderTop((short) 1);
		sLinha1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		sLinha1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFCellStyle sLinha2 = workbook.createCellStyle();
		sLinha2.setBorderBottom((short) 1);
		sLinha2.setBorderLeft((short) 1);
		sLinha2.setBorderRight((short) 1);
		sLinha2.setBorderTop((short) 1);

		try	
		{
			HSSFRow row = abaManual.createRow(0);
			HSSFCell cell0Title = row.createCell(0);
			cell0Title.setCellValue("Funcionario");
			cell0Title.setCellStyle(sTitle);

			HSSFCell cell1Title = row.createCell(1);
			cell1Title.setCellValue("Regiao");
			cell1Title.setCellStyle(sTitle);

			HSSFCell cell2Title = row.createCell(2);
			cell2Title.setCellValue("Trimestre");
			cell2Title.setCellStyle(sTitle);

			HSSFCell cell3Title = row.createCell(3);
			cell3Title.setCellValue("Cota");
			cell3Title.setCellStyle(sTitle);

			HSSFCell cell4Title = row.createCell(4);
			cell4Title.setCellValue("Vendas");
			cell4Title.setCellStyle(sTitle);
			
			int i = 1;
			
			for(Amostra registro : amostra)
			{
				Object func = registro.getFuncionario();
				Object regiao = registro.getRegiao();			
				Object trimestre = registro.getTrimestre();
				Double cota = registro.getCota();
				Double vendas = registro.getVendas();

				row = abaManual.createRow(i);
				popularLinhas(sLinha1, sLinha2, i, row, func, regiao, trimestre, cota, vendas);
				i++;
			}

			File currDir = new File(".");
			String path = currDir.getAbsolutePath();
			
			GregorianCalendar data = new GregorianCalendar();
			SimpleDateFormat form = new SimpleDateFormat("ddMMMyyyy HHmmss");
			form.setCalendar(data);
			String dataForm = form.format(data.getTime());
			
			String fileLocation = path.substring(0, path.length() - 1) + "relatorio-geral-" + dataForm + ".xls";
			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			outputStream.close();
			
			return "Relatorio gerado com sucesso em: " + fileLocation; 
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
			return "Erro ao gerar relatório.";
		}
	}
	
	private void popularLinhas(HSSFCellStyle sLinha1, HSSFCellStyle sLinha2, int i, HSSFRow row, Object funcionario, Object regiao, Object trimestre, Double cota, Double vendas) {

		//Funcionario
		HSSFCell cell0 = row.createCell(0);
		if (funcionario != null){
				cell0.setCellValue(funcionario.toString());
		} else {
				cell0.setCellValue("");
		}

		//Regiao
		HSSFCell cell1 = row.createCell(1);
		if (regiao != null){
				cell1.setCellValue(regiao.toString());
		} else {
				cell1.setCellValue("");
		}	

		//Trimestre
		HSSFCell cell2 = row.createCell(2);
		if (trimestre != null){
				cell2.setCellValue(trimestre.toString());
		} else {
				cell2.setCellValue("");
		}
		
		//Cota
		HSSFCell cell3 = row.createCell(3);
		if (cota != null){
//				String cotaForm = new DecimalFormat("#,##").format(cota);
				cell3.setCellValue("R$" + cota);
		} else {
				cell3.setCellValue("");
		}
		
		//Vendas
		HSSFCell cell4 = row.createCell(4);
		if (vendas != null){
//				String vendasForm = new DecimalFormat("#,##").format(vendas);
				cell4.setCellValue("R$" + vendas);
		} else {
				cell4.setCellValue("");
		}

		if (i % 2 == 0) {
				cell0.setCellStyle(sLinha1);
				cell1.setCellStyle(sLinha1);
				cell2.setCellStyle(sLinha1);
				cell3.setCellStyle(sLinha1);
				cell4.setCellStyle(sLinha1);
		}
		else {
				cell0.setCellStyle(sLinha2);
				cell1.setCellStyle(sLinha2);
				cell2.setCellStyle(sLinha2);
				cell3.setCellStyle(sLinha2);
				cell4.setCellStyle(sLinha2);
		}
	}

}
