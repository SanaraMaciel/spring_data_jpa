package br.com.sanara.spring.data.service;

import br.com.sanara.spring.data.orm.Funcionario;
import br.com.sanara.spring.data.repository.FuncionarioCrudRepository;
import br.com.sanara.spring.data.repository.FuncionarioRepository;
import br.com.sanara.spring.data.specification.SpecificationFuncionario;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioFuncionarioDinamico {

    private Boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FuncionarioRepository funcionarioRepository;

    public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {

        while (system) {
            System.out.println("Qual ação de carg você quer executar? ");
            System.out.println("0 - Sair ");
            System.out.println("1 - Busca Dinâmica");

            int action = scanner.nextInt();
            switch (action) {
                case 1:
                    buscaDinamica(scanner);
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void buscaDinamica(Scanner scanner) {
        System.out.println("Digite o nome que vc quer buscar: ");
        String nomeString = scanner.next();

        if (nomeString.equalsIgnoreCase("NULL")) {
            nomeString = null;
        }

        System.out.println("Digite o cpf que vc quer buscar: ");
        String cpf = scanner.next();
        if (cpf.equalsIgnoreCase("NULL")) {
            cpf = null;
        }

        System.out.println("Digite o salario que vc quer buscar: ");
        Double salario = scanner.nextDouble();
        if (salario == 0) {
            salario = null;
        }

        System.out.println("Digite a data que vc quer buscar: ");
        String data = scanner.next();
        LocalDate dataContratacao;

        if (data.equalsIgnoreCase("NULL")) {
            dataContratacao = null;
        } else {
            dataContratacao = LocalDate.parse(data, formatter);
        }

        List<Funcionario> funcionarios = funcionarioRepository.findAll(
                Specification.where(
                         SpecificationFuncionario.nome(nomeString))
                        .or(SpecificationFuncionario.cpf(cpf))
                        .or(SpecificationFuncionario.salario(salario))
                        .or(SpecificationFuncionario.dataContratacao(dataContratacao))
        );

        System.out.println("Os Funcionário com o nome são: ");
        funcionarios.forEach(f -> System.out.println(f));
    }

}
