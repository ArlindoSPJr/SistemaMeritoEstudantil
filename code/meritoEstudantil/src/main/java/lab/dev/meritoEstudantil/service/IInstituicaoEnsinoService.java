package lab.dev.meritoEstudantil.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.dev.meritoEstudantil.domain.instituicao.IInstituicaoEnsino;
import lab.dev.meritoEstudantil.repository.IInstituicaoEnsinoRepository;

@Service
@Transactional
public class IInstituicaoEnsinoService {

	private final IInstituicaoEnsinoRepository instituicaoRepository;

	public IInstituicaoEnsinoService(IInstituicaoEnsinoRepository instituicaoRepository) {
		this.instituicaoRepository = instituicaoRepository;
	}

	public IInstituicaoEnsino create(IInstituicaoEnsino instituicao) {
		return instituicaoRepository.save(instituicao);
	}

	public List<IInstituicaoEnsino> findAll() {
		return instituicaoRepository.findAll();
	}

	public IInstituicaoEnsino findById(Long id) {
		return instituicaoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Instituição não encontrada com ID: " + id));
	}

	public IInstituicaoEnsino update(Long id, IInstituicaoEnsino update) {
		IInstituicaoEnsino current = findById(id);
		current.setNome(update.getNome());
		current.setEndereco(update.getEndereco());
		return instituicaoRepository.save(current);
	}

	public void delete(Long id) {
		instituicaoRepository.deleteById(id);
	}
}
