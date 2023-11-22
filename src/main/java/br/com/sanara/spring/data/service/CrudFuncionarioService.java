package br.com.sanara.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import br.com.sanara.spring.data.repository.FuncionarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.sanara.spring.data.orm.Cargo;
import br.com.sanara.spring.data.orm.Funcionario;
import br.com.sanara.spring.data.orm.UnidadeTrabalho;
import br.com.sanara.spring.data.repository.CargoRepository;
import br.com.sanara.spring.data.repository.FuncionarioCrudRepository;
import br.com.sanara.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

    private Boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final CargoRepository cargoRepository;
    private final FuncionarioCrudRepository funcionarioCrudRepository;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    private final FuncionarioRepository funcionarioRepository;


    public CrudFuncionarioService(FuncionarioCrudRepository funcionarioCrudRepository, CargoRepository cargoRepository,
                                  UnidadeTrabalhoRepository unidadeTrabalhoRepository, FuncionarioRepository funcionarioRepository) {
        this.cargoRepository = cargoRepository;
        this.funcionarioCrudRepository = funcionarioCrudRepository;
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual acao de cargo deseja executar");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Deletar");
            System.out.println("5 - Visualizar Paginado");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    atualizar(scanner);
                    break;
                case 3:
                    visualizar();
                    break;
                case 4:
                    deletar(scanner);
                    break;
                case 5:
                    visualizarPaginado(scanner);
                    break;
                default:
                    system = false;
                    break;
            }

        }

    }

    private void salvar(Scanner scanner) {
        System.out.println("Digite o nome");
        String nome = scanner.next();

        System.out.println("Digite o cpf");
        String cpf = scanner.next();

        System.out.println("Digite o salario");
        Double salario = scanner.nextDouble();

        System.out.println("Digite a data de contracao");
        String dataContratacao = scanner.next();

        System.out.println("Digite o cargoId");
        Integer cargoId = scanner.nextInt();

        List<UnidadeTrabalho> unidades = unidade(scanner);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        funcionario.setCargo(cargo.get());
        funcionario.setUnidadeTrabalhos(unidades);

        funcionarioCrudRepository.save(funcionario);
        System.out.println("Salvo");
    }

    private List<UnidadeTrabalho> unidade(Scanner scanner) {
        Boolean isTrue = true;
        List<UnidadeTrabalho> unidades = new ArrayList<>();

        while (isTrue) {
            System.out.println("Digite o unidadeId (Para sair digite 0)");
            Integer unidadeId = scanner.nextInt();

            if (unidadeId != 0) {
                Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
                unidades.add(unidade.get());
            } else {
                isTrue = false;
            }
        }

        return unidades;
    }

    private void atualizar(Scanner scanner) {
        System.out.println("Digite o id");
        Integer id = scanner.nextInt();

        System.out.println("Digite o nome");
        String nome = scanner.next();

        System.out.println("Digite o cpf");
        String cpf = scanner.next();

        System.out.println("Digite o salario");
        Double salario = scanner.nextDouble();

        System.out.println("Digite a data de contracao");
        String dataContratacao = scanner.next();

        System.out.println("Digite o cargoId");
        Integer cargoId = scanner.nextInt();

        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        funcionario.setCargo(cargo.get());

        funcionarioCrudRepository.save(funcionario);
        System.out.println("Alterado");
    }

    private void visualizar() {
        Iterable<Funcionario> funcionarios = funcionarioCrudRepository.findAll();
        funcionarios.forEach(funcionario -> System.out.println(funcionario));
    }

    //funcionários paginados
    private void visualizarPaginado(Scanner scanner) {
        System.out.println("Qual página você deseja visualizar: ");
        Integer page = scanner.nextInt();

        //unsorted usa a ordenação default que é por id
        ///Pageable pageable = PageRequest.of(page, 5, Sort.unsorted());

        //ordenação pelo nome em ordem decrescente
        //Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "nome"));

        //ordenando do menor salário para o maior
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "salario"));

        Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);

        //mostrando ao cliente a qtd de páginas que ele tem
        System.out.println("páginas: " + funcionarios);
        System.out.println("página atual: " + funcionarios.getNumber());
        System.out.println("quantidade total de elementos: " + funcionarios.getTotalElements());

        funcionarios.forEach(funcionario -> System.out.println(funcionario));
    }

    private void deletar(Scanner scanner) {
        System.out.println("Id");
        int id = scanner.nextInt();
        funcionarioCrudRepository.deleteById(id);
        System.out.println("Deletado");
    }

}
