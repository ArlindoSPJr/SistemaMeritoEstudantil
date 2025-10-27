package MeritoEstudantil.app.controllers.dto;

import MeritoEstudantil.app.models.Aluno;

public class AlunoResponseDTO {
    
    private Long id;
    private String rg;
    private String cpf;
    private String nome;
    private String endereco;
    private String profissao;
    private String email;
    
    // Construtor
    public AlunoResponseDTO() {}
    
    public AlunoResponseDTO(Aluno cliente) {
        this.id = cliente.getId();
        this.rg = cliente.getRg();
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
        this.endereco = cliente.getEmail();
        this.profissao = cliente.getProfissao();
        this.email = cliente.getEmail();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRg() {
        return rg;
    }
    
    public void setRg(String rg) {
        this.rg = rg;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getProfissao() {
        return profissao;
    }
    
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
