package lab.dev.meritoEstudantil.domain.transacao;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lab.dev.meritoEstudantil.domain.aluno.Aluno;
import lab.dev.meritoEstudantil.domain.professor.Professor;
import lab.dev.meritoEstudantil.domain.vantagem.Vantagem;

@Entity
@Table(name = "transacoes")
public class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "professor_id", nullable = true)
	private Professor professor;

	@ManyToOne
	@JoinColumn(name = "aluno_id", nullable = false)
	private Aluno aluno;

	@Column(nullable = false)
	private Integer quantidadeMoedas;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoTransacao tipo;

	@Column(nullable = true)
	private String descricao;

	@Column(nullable = false)
	private LocalDateTime dataCriacao;

	@ManyToOne
	@JoinColumn(name = "vantagem_id", nullable = true)
	private Vantagem vantagem;

	public Transacao() {
		this.dataCriacao = LocalDateTime.now();
	}

	public Transacao(Professor professor, Aluno aluno, Integer quantidadeMoedas, TipoTransacao tipo, String descricao) {
		this.professor = professor;
		this.aluno = aluno;
		this.quantidadeMoedas = quantidadeMoedas;
		this.tipo = tipo;
		this.descricao = descricao;
		this.dataCriacao = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Integer getQuantidadeMoedas() {
		return quantidadeMoedas;
	}

	public void setQuantidadeMoedas(Integer quantidadeMoedas) {
		this.quantidadeMoedas = quantidadeMoedas;
	}

	public TipoTransacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransacao tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Vantagem getVantagem() {
		return vantagem;
	}

	public void setVantagem(Vantagem vantagem) {
		this.vantagem = vantagem;
	}
}
