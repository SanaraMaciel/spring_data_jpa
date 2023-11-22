package br.com.sanara.spring.data.service;

import br.com.sanara.spring.data.orm.Funcionario;
import br.com.sanara.spring.data.orm.FuncionarioDTO;
import br.com.sanara.spring.data.orm.FuncionarioProjecao;
import br.com.sanara.spring.data.repository.FuncionarioCrudRepository;
import br.com.sanara.spring.data.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatoriosService {

    private Boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FuncionarioCrudRepository funcionarioCrudRepository;
    private final FuncionarioRepository funcionarioRepository;

    public RelatoriosService(FuncionarioCrudRepository funcionarioCrudRepository, FuncionarioRepository funcionarioRepository) {
        this.funcionarioCrudRepository = funcionarioCrudRepository;
        this.funcionarioRepository = funcionarioRepository;
    }


    public void inicial(Scanner scanner) {

        while (system) {
            System.out.println("Qual ação de carg você quer executar? ");
            System.out.println("0 - Sair ");
            System.out.println("1 - Busca funcionário por nome");
            System.out.println("2 - Busca funcionário por nome, salário maior e data");
            System.out.println("3 - Busca funcionário por data contratação");
            System.out.println("4 - Busca funcionário por salário projeção");
            System.out.println("5 - Busca funcionário por salário dto");

            int action = scanner.nextInt();
            switch (action) {
                case 1:
                    buscaFuncionarioNome(scanner);
                    break;
                case 2:
                    buscaFuncionarioPorNomeSalarioMaiorDataContratacao(scanner);
                    break;
                case 3:
                    buscaFuncionarioDataContratacao(scanner);
                    break;
                case 4:
                    pesquisaFuncionarioSalarioProjecao();
                    break;
                case 5:
                    pesquisaFuncionarioSalarioDTO();
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void buscaFuncionarioNome(Scanner scanner) {
        System.out.println("Digite o nome que vc quer buscar");
        String nome = scanner.next();
        List<Funcionario> funcionarios = funcionarioCrudRepository.findByNome(nome);
        System.out.println("Os Funcionário com o nome são: ");
        funcionarios.forEach(f -> System.out.println(f));
    }

    private void buscaFuncionarioPorNomeSalarioMaiorDataContratacao(Scanner scanner) {

        System.out.println("Digite o nome que vc quer buscar");
        String nome = scanner.next();

        System.out.println("Digite a data que vc quer buscar");
        String data = scanner.next();

        System.out.println("Digite o salário maior que vc quer buscar");
        Double salario = scanner.nextDouble();

        LocalDate dataU = LocalDate.parse(data, formatter);

        List<Funcionario> funcionarios = funcionarioCrudRepository.findByNomeSalarioMaiorDataContratacao(nome,
                salario, dataU);

        System.out.println("Os Funcionário com o nome são: ");
        funcionarios.forEach(f -> System.out.println(f));
    }

    private void buscaFuncionarioDataContratacao(Scanner scanner) {

        System.out.println("Digite a data que vc quer buscar");
        String data = scanner.next();

        LocalDate dataU = LocalDate.parse(data, formatter);

        List<Funcionario> funcionarios = funcionarioCrudRepository.findDataContratacaoMaior(dataU);

        System.out.println("Os Funcionários com a data de contratacao maior é: ");
        funcionarios.forEach(f -> System.out.println(f));
    }

    private void pesquisaFuncionarioSalarioProjecao() {
        List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
        list.forEach(f -> System.out.println("Funcionario: id: " + f.getId()
                + " | nome: " + f.getNome() + " | salario: " + f.getSalario()));

    }

    private void pesquisaFuncionarioSalarioDTO() {
        List<FuncionarioDTO> list = funcionarioRepository.findFuncionarioSalarioDTO();
        list.forEach(f -> System.out.println("Funcionario: id: " + f.getId()
                + " | nome: " + f.getNome() + " | salario: " + f.getSalario()));

    }


}
