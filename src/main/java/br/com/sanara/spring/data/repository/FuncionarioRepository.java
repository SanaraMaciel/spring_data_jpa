package br.com.sanara.spring.data.repository;

import br.com.sanara.spring.data.orm.Funcionario;
import br.com.sanara.spring.data.orm.FuncionarioDTO;
import br.com.sanara.spring.data.orm.FuncionarioProjecao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//PagingAndSortingRepository interface que já faz a paginação pelo JPA
//A feature que o Spring Data utiliza para fazer Querys dinâmicas se chama Specification.
//JpaSpecificationExecutor interface que faz a urilização das querys dinâmicas
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>,
        JpaSpecificationExecutor<Funcionario> {


    List<Funcionario> findByNome(String nome);

    //novo método com paginação
    List<Funcionario> findByNome(String nome, Pageable pageable);


    //query de projeção faz projeções das entidades contendo apenas os atributos que quero usar
    //ela é feita criando uma interface com os atributos que queremos usar
    @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
    List<FuncionarioProjecao> findFuncionarioSalario();

    //usando o padrão DTO - Data Transfer Object a vantagem é que se
    // pode criar métodos mais especificos de formatação para a view
    //Segundo a documentação do Spring Data Jpa não é possível usar Projection de DTO com query nativa.
    //As alternativas mais simples são: 1- Usar JPQL e passar o construtor do DTO
    // (importante tomar cuidado q tem q passar o caminho completo do pacote até a classe):
    //2 - Usar uma query derivada com base nos atributos da entidade e colocando o DTO como tp de retorno:
    //List<FuncionarioDto> findAllByOrderByNome();
    @Query(value = "SELECT new br.com.sanara.spring.data.orm.FuncionarioDTO(f.id, f.nome, f.salario) FROM Funcionario f")
    List<FuncionarioDTO> findFuncionarioSalarioDTO();




}
