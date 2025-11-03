package lab.dev.meritoEstudantil.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;
import lab.dev.meritoEstudantil.domain.gerente.Role;
import lab.dev.meritoEstudantil.repository.EmpresaParceiraRepository;

@Service
@Transactional
public class EmpresaParceiraService {

	private final EmpresaParceiraRepository empresaParceiraRepository;
	private final PasswordEncoder passwordEncoder;

	public EmpresaParceiraService(EmpresaParceiraRepository empresaParceiraRepository, PasswordEncoder passwordEncoder) {
		this.empresaParceiraRepository = empresaParceiraRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public EmpresaParceira create(EmpresaParceira empresa) {
		empresa.setSenha(passwordEncoder.encode(empresa.getSenha()));
		empresa.setRole(Role.EMPRESA_PARCEIRA);
		return empresaParceiraRepository.save(empresa);
	}

	public List<EmpresaParceira> findAll() {
		return empresaParceiraRepository.findAll();
	}

	public EmpresaParceira findById(Long id) {
		return empresaParceiraRepository.findById(id).orElseThrow();
	}

	public EmpresaParceira update(Long id, EmpresaParceira update) {
		EmpresaParceira current = findById(id);
		current.setEmail(update.getEmail());
		if (update.getSenha() != null && !update.getSenha().isEmpty()) {
			current.setSenha(passwordEncoder.encode(update.getSenha()));
		}
		current.setNomeFantasia(update.getNomeFantasia());
		current.setRazaoSocial(update.getRazaoSocial());
		current.setCnpj(update.getCnpj());
		return empresaParceiraRepository.save(current);
	}

	public void delete(Long id) {
		empresaParceiraRepository.deleteById(id);
	}
}


