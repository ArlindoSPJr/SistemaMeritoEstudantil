package lab.dev.meritoEstudantil.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.dev.meritoEstudantil.domain.instituicao.IInstituicaoEnsino;
import lab.dev.meritoEstudantil.domain.professor.Professor;
import lab.dev.meritoEstudantil.repository.IInstituicaoEnsinoRepository;
import lab.dev.meritoEstudantil.repository.ProfessorRepository;

@Service
@Transactional
public class ProfessorService {

	private final ProfessorRepository professorRepository;
	private final IInstituicaoEnsinoRepository instituicaoRepository;
	private final PasswordEncoder passwordEncoder;

	public ProfessorService(ProfessorRepository professorRepository, 
							IInstituicaoEnsinoRepository instituicaoRepository,
							PasswordEncoder passwordEncoder) {
		this.professorRepository = professorRepository;
		this.instituicaoRepository = instituicaoRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Professor create(Professor professor) {
		if (professorRepository.existsByCpf(professor.getCpf())) {
			throw new RuntimeException("CPF já cadastrado: " + professor.getCpf());
		}
		professor.setSenha(passwordEncoder.encode(professor.getSenha()));
		return professorRepository.save(professor);
	}

	public List<Professor> findAll() {
		return professorRepository.findAll();
	}

	public Professor findById(Long id) {
		return professorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Professor não encontrado com ID: " + id));
	}

	public Professor update(Long id, Professor update) {
		Professor current = findById(id);
		current.setNome(update.getNome());
		current.setEmail(update.getEmail());
		current.setDepartamento(update.getDepartamento());
		if (update.getInstituicaoEnsino() != null && update.getInstituicaoEnsino().getId() != null) {
			IInstituicaoEnsino instituicao = instituicaoRepository.findById(update.getInstituicaoEnsino().getId())
					.orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
			current.setInstituicaoEnsino(instituicao);
		}
		return professorRepository.save(current);
	}

	public void delete(Long id) {
		professorRepository.deleteById(id);
	}
}
