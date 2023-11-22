package br.com.sanara.spring.data.orm;

public class FuncionarioDTO {
    private Integer id;
    private String nome;
    private Double salario;

    //getter e setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }


    //construtor recebendo os atributos na ordem da query
    public FuncionarioDTO(Integer id, String nome, Double salario) {
        this.id = id;
        this.nome = nome;
        this.salario = salario;
    }
}