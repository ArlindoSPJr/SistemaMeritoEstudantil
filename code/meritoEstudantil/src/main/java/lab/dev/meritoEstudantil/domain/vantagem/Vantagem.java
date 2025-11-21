package lab.dev.meritoEstudantil.domain.vantagem;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lab.dev.meritoEstudantil.domain.aluno.Aluno;
import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;

@Entity
@Table(name = "vantagens")
public class Vantagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String descricao;

    @Column(name = "ativo")
    private boolean ativo;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "custo_moedas")
    private double custoMoedas;

    @ManyToOne
    @JoinColumn(name = "empresa_parceira_id")
    private EmpresaParceira empresaParceira;

    @Column(name = "quantidade")
    private double quantidade;

    @ManyToMany(mappedBy = "vantagens")
    private List<Aluno> alunos;

    public Vantagem() {
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public void addAluno(Aluno aluno) {
        if (this.alunos == null) {
            this.alunos = new java.util.ArrayList<>();
        }
        this.alunos.add(aluno);
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getCustoMoedas() {
        return custoMoedas;
    }

    public void setCustoMoedas(double custoMoedas) {
        this.custoMoedas = custoMoedas;
    }

    public EmpresaParceira getEmpresaParceira() {
        return empresaParceira;
    }

    public void setEmpresaParceira(EmpresaParceira empresaParceira) {
        this.empresaParceira = empresaParceira;
    }

}
