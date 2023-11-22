package br.com.sanara.spring.data.specification;

import br.com.sanara.spring.data.orm.Funcionario;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class SpecificationFuncionario {

    //faz uma consulta no banco por nome where like
    //essa forma substitui a forma verbosa de criar lista de predicados, criteriaQuery , builder
    public static Specification<Funcionario> nome(String nome){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("nome"),"%" + nome +"%");
    }

    public static Specification<Funcionario> cpf(String cpf){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("cpf"),cpf);
    }

    public static Specification<Funcionario> salario(Double salario){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("salario"),salario);
    }
    public static Specification<Funcionario> dataContratacao(LocalDate dataContratacao){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("dataContratacao"), dataContratacao);
    }
}
