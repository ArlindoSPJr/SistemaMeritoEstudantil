package lab.dev.meritoEstudantil.domain.empresa;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lab.dev.meritoEstudantil.domain.gerente.Role;
import lab.dev.meritoEstudantil.domain.vantagem.Vantagem;

@Entity
@Table(name = "empresas_parceiras")
public class EmpresaParceira implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false)
	private String nomeFantasia;

	@Column(nullable = false)
	private String razaoSocial;

	@Column(nullable = false, unique = true)
	private String cnpj;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.EMPRESA_PARCEIRA;

	@OneToMany(mappedBy = "empresaParceira")
	private List<Vantagem> vantagens = new ArrayList<>();

	public Long getId() { return id; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
	public String getNomeFantasia() { return nomeFantasia; }
	public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
	public String getRazaoSocial() { return razaoSocial; }
	public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
	public String getCnpj() { return cnpj; }
	public void setCnpj(String cnpj) { this.cnpj = cnpj; }
	public Role getRole() { return role; }
	public void setRole(Role role) { this.role = role; }

	public List<Vantagem> getVantagens() { return vantagens; }
	public void setVantagens(List<Vantagem> vantagens) { this.vantagens = vantagens; }

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


