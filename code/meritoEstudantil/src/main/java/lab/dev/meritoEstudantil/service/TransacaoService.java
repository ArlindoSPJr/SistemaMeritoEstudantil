package lab.dev.meritoEstudantil.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.dev.meritoEstudantil.domain.aluno.Aluno;
import lab.dev.meritoEstudantil.domain.professor.Professor;
import lab.dev.meritoEstudantil.domain.transacao.Transacao;
import lab.dev.meritoEstudantil.domain.transacao.TipoTransacao;
import lab.dev.meritoEstudantil.dto.transacao.EnviarMoedasDTO;
import lab.dev.meritoEstudantil.repository.AlunoRepository;
import lab.dev.meritoEstudantil.repository.ProfessorRepository;
import lab.dev.meritoEstudantil.repository.TransacaoRepository;

@Service
@Transactional
public class TransacaoService {

	private final TransacaoRepository transacaoRepository;
	private final ProfessorRepository professorRepository;
	private final AlunoRepository alunoRepository;
	private final PasswordEncoder passwordEncoder;

	public TransacaoService(TransacaoRepository transacaoRepository, 
							ProfessorRepository professorRepository, 
							AlunoRepository alunoRepository,
							PasswordEncoder passwordEncoder) {
		this.transacaoRepository = transacaoRepository;
		this.professorRepository = professorRepository;
		this.alunoRepository = alunoRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Transacao enviarMoedas(Long professorId, EnviarMoedasDTO dto) {
		Professor professor = professorRepository.findById(professorId)
				.orElseThrow(() -> new RuntimeException("Professor não encontrado com ID: " + professorId));

		Aluno aluno = alunoRepository.findById(dto.alunoId())
				.orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + dto.alunoId()));

		if (professor.getSaldoMoedas() < dto.quantidadeMoedas()) {
			throw new RuntimeException("Saldo insuficiente. Saldo atual: " + professor.getSaldoMoedas());
		}

		professor.setSaldoMoedas(professor.getSaldoMoedas() - dto.quantidadeMoedas());
		aluno.setSaldoMoedas(aluno.getSaldoMoedas() + dto.quantidadeMoedas());

		professorRepository.save(professor);
		alunoRepository.save(aluno);

		Transacao transacao = new Transacao(professor, aluno, dto.quantidadeMoedas(), TipoTransacao.ENVIO, dto.descricao());
		return transacaoRepository.save(transacao);
	}

	public List<Transacao> obterTransacoesDoProfessor(Long professorId) {
		return transacaoRepository.findByProfessorId(professorId);
	}

	public List<Transacao> obterTransacoesDoAluno(Long alunoId) {
		return transacaoRepository.findByAlunoId(alunoId);
	}

	public Transacao obterTransacao(Long id) {
		return transacaoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Transação não encontrada com ID: " + id));
	}

	public List<Transacao> obterTodas() {
		return transacaoRepository.findAll();
	}
}
