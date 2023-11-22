package br.com.sanara.spring.data.repository;

import br.com.sanara.spring.data.orm.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuncionarioCrudRepository extends CrudRepository<Funcionario, Integer> {

    //DerivedQuery
    List<Funcionario> findByNome(String nome);

    List<Funcionario> findByNomeLike(String nome);

    List<Funcionario> findByNomeEndingWith(String nome);

    List<Funcionario> findByNomeStartingWith(String nome);

    List<Funcionario> findByNomeIsNull();

    List<Funcionario> findByNomeIsNotNull();

    List<Funcionario> findByNomeOrderByNomeAsc(String nome);

    //deve estar no repositório do funcionário
    List<Funcionario> findByCargoDescricao(String descricao);

    //Query usando JPQL
    //derivedQuery --> List<Funcionario> findByNameAndSalarioGreaterThanAndDataContratacao(String nome, Double salario, LocalDate dataContratacao);
    //usando JPQL :

    @Query("SELECT f FROM Funcionario f WHERE f.nome= :nome AND f.salario >= :salario AND f.dataContratacao = :data")
    public List findByNomeSalarioMaiorDataContratacao(@Param("nome") String nome, @Param("salario") Double salario, @Param("data") LocalDate data);


    @Query("SELECT f FROM Funcionario f JOIN f.cargo c WHERE c.descricao = :descricao")
    List<Funcionario> findByCargoPelaDescricao(@Param("descricao") String descricao);

    //pesquisa pela descrição da UnidadeTrabalho
    //usar _ para informar ao JPA que queremos pesquisar pela descrição da unidade de trabalho e
    // não de funcionario essa forma é uma desvantagem para seguir o padrão JAVA
    List<Funcionario> findByUnidadeTrabalhos_Descricao(String descricao);

    //mesma query em JPQL:
    @Query("SELECT f FROM Funcionario f JOIN f.unidadeTrabalhos u WHERE u.descricao = :descricao")
    List<Funcionario> findByUnidadeTrabalhos_DescricaoJPQL(@Param("descricao") String descricao);

    //nativeQuerys:
    @Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data", nativeQuery = true)
    List<Funcionario> findDataContratacaoMaior(@Param("data") LocalDate data);

}
