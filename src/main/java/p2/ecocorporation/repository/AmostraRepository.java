package p2.ecocorporation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import p2.ecocorporation.model.Amostra;

public interface AmostraRepository extends CrudRepository<Amostra, Integer> {

	@Query
	("select a from amostra a where a.funcionario = :funcionario")
	List<Amostra> findByFuncionario(String funcionario);

	@Query
	("select a from amostra a where a.regiao = :regiao")
	List<Amostra> findByRegiao(String regiao);

	@Query
	("select a from amostra a where a.trimestre = :trimestre")
	List<Amostra> findByTrimestre(String trimestre);

}
