package p2.ecocorporation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "amostra")
public class Amostra {
	
	@Id
	public Integer codigo;
	
	@Column(nullable = false, length = 200)
	public String funcionario;
	
	@Column(nullable = false, length = 200)
	public String regiao;
	
	@Column(nullable = false, length = 200)
	public String trimestre;
	
	@Column
	public Double cota;
	
	@Column
	public Double vendas;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(String trimestre) {
		this.trimestre = trimestre;
	}

	public Double getCota() {
		return cota;
	}

	public void setCota(Double cota) {
		this.cota = cota;
	}

	public Double getVendas() {
		return vendas;
	}

	public void setVendas(Double vendas) {
		this.vendas = vendas;
	}

}
