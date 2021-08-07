package p2.ecocorporation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "funcionario")
public class Funcionario {
	
	@Id
	public Integer codigo;
	
	@Column(nullable = false, length = 200)
	public String nome;
	
	@Column(nullable = false, length = 200)
	public String dataNascimento;
	
	@Column(nullable = false, length = 9)
	public String sexo;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
