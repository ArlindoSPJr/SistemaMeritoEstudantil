package lab.dev.meritoEstudantil.domain.professor;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lab.dev.meritoEstudantil.domain.gerente.Role;
import lab.dev.meritoEstudantil.domain.instituicao.IInstituicaoEnsino;

@Entity
@Table(name = "professor")
public class Professor implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false, unique = true)
	private String cpf;

	@Column(nullable = false)
	private String departamento;

	@Column(nullable = false)
	private Integer saldoMoedas = 0;

	@ManyToOne
	@JoinColumn(name = "instituicao_ensino_id", nullable = false)
	private IInstituicaoEnsino instituicaoEnsino;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.PROFESSOR;

	public Professor() {}

	public Professor(String email, String senha, String nome, String cpf, String departamento, IInstituicaoEnsino instituicaoEnsino) {
		this.email = email;
		this.senha = senha;
		this.nome = nome;
		this.cpf = cpf;
		this.departamento = departamento;
		this.instituicaoEnsino = instituicaoEnsino;
		this.role = Role.PROFESSOR;
	}

	public Long getId() { return id; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }
	public String getDepartamento() { return departamento; }
	public void setDepartamento(String departamento) { this.departamento = departamento; }
	public Integer getSaldoMoedas() { return saldoMoedas; }
	public void setSaldoMoedas(Integer saldoMoedas) { this.saldoMoedas = saldoMoedas; }
	public IInstituicaoEnsino getInstituicaoEnsino() { return instituicaoEnsino; }
	public void setInstituicaoEnsino(IInstituicaoEnsino instituicaoEnsino) { this.instituicaoEnsino = instituicaoEnsino; }
	public Role getRole() { return role; }
	public void setRole(Role role) { this.role = role; }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
