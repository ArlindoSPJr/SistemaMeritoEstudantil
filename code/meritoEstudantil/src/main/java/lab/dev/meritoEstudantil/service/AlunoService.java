package lab.dev.meritoEstudantil.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.dev.meritoEstudantil.domain.aluno.Aluno;
import lab.dev.meritoEstudantil.domain.gerente.Role;
import lab.dev.meritoEstudantil.repository.AlunoRepository;

@Service
@Transactional
public class AlunoService {

	private final AlunoRepository alunoRepository;
	private final PasswordEncoder passwordEncoder;

	public AlunoService(AlunoRepository alunoRepository, PasswordEncoder passwordEncoder) {
		this.alunoRepository = alunoRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Aluno create(Aluno aluno) {
		aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));
		aluno.setRole(Role.ALUNO);
		return alunoRepository.save(aluno);
	}

	public List<Aluno> findAll() {
		return alunoRepository.findAll();
	}

	public Aluno findById(Long id) {
		return alunoRepository.findById(id).orElseThrow();
	}

	public Aluno update(Long id, Aluno update) {
		Aluno current = findById(id);
		current.setEmail(update.getEmail());
		if (update.getSenha() != null && !update.getSenha().isEmpty()) {
			current.setSenha(passwordEncoder.encode(update.getSenha()));
		}
		current.setCpf(update.getCpf());
		current.setRg(update.getRg());
		current.setNome(update.getNome());
		current.setEndereco(update.getEndereco());
		current.setSaldoMoedas(update.getSaldoMoedas());
		current.setCurso(update.getCurso());
		return alunoRepository.save(current);
	}

	public void delete(Long id) {
		alunoRepository.deleteById(id);
	}
}


