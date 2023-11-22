package br.com.sanara.spring.data.service;

import br.com.sanara.spring.data.orm.Cargo;
import br.com.sanara.spring.data.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CrudCargoService {

    private Boolean system = true;
    private final CargoRepository cargoRepository;

    public CrudCargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void inicial(Scanner scanner) {

        while (system) {
            System.out.println("Qual ação de carg você quer executar? ");
            System.out.println("0 - Sair ");
            System.out.println("1 - Salvar Cargo");
            System.out.println("2 - Editar Cargo");
            System.out.println("3 - Visulizar Cargos");
            System.out.println("4 - Deletar Cargos");

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
                default:
                    system = false;
                    break;
            }

        }
    }

    private void salvar(Scanner scanner) {
        System.out.println("Descrição do cargo: ");
        //pega o valor que o usuário digitou no console
        String descricao = scanner.next();
        Cargo cargo = new Cargo();
        cargo.setDescricao(descricao);
        cargoRepository.save(cargo);
        System.out.println("Cargo Salvo");
    }

    private void atualizar(Scanner scanner) {
        System.out.println("ID do registro a ser editado: ");
        int id = scanner.nextInt();
        System.out.println("Nova descrição do cargo: ");
        String descricao = scanner.next();
        Cargo cargo = new Cargo();
        cargo.setId(id);
        cargo.setDescricao(descricao);
        cargoRepository.save(cargo);
        System.out.println("Cargo atualizado");
    }

    private void visualizar() {
        Iterable<Cargo> cargos = cargoRepository.findAll();
        cargos.forEach(cargo -> System.out.println(cargo.getDescricao()));
    }

    private void deletar(Scanner scanner) {
        System.out.println("ID do registro a ser deletado: ");
        int id = scanner.nextInt();
        cargoRepository.deleteById(id);
        System.out.println("Cargo deletado");
    }


}
