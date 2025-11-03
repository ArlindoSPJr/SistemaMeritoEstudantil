package lab.dev.meritoEstudantil.domain.aluno;

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
import jakarta.persistence.Table;

import lab.dev.meritoEstudantil.domain.gerente.Role;

@Entity
@Table(name = "alunos")
public class Aluno implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false, unique = true)
	private String cpf;

	@Column(nullable = false, unique = true)
	private String rg;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String endereco;

	@Column(nullable = false)
	private Integer saldoMoedas = 0;

	@Column(nullable = false)
	private String curso;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.ALUNO;

	public Long getId() { return id; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }
	public String getRg() { return rg; }
	public void setRg(String rg) { this.rg = rg; }
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getEndereco() { return endereco; }
	public void setEndereco(String endereco) { this.endereco = endereco; }
	public Integer getSaldoMoedas() { return saldoMoedas; }
	public void setSaldoMoedas(Integer saldoMoedas) { this.saldoMoedas = saldoMoedas; }
	public String getCurso() { return curso; }
	public void setCurso(String curso) { this.curso = curso; }
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


