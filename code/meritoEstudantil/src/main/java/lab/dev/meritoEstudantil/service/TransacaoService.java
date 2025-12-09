package lab.dev.meritoEstudantil.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.dev.meritoEstudantil.domain.aluno.Aluno;
import lab.dev.meritoEstudantil.domain.professor.Professor;
import lab.dev.meritoEstudantil.domain.transacao.Transacao;
import lab.dev.meritoEstudantil.domain.vantagem.Vantagem;
import lab.dev.meritoEstudantil.domain.transacao.TipoTransacao;
import lab.dev.meritoEstudantil.dto.transacao.EnviarMoedasDTO;
import lab.dev.meritoEstudantil.exception.InsufficientBalanceException;
import lab.dev.meritoEstudantil.exception.ResourceNotFoundException;
import lab.dev.meritoEstudantil.repository.AlunoRepository;
import lab.dev.meritoEstudantil.repository.ProfessorRepository;
import lab.dev.meritoEstudantil.repository.TransacaoRepository;
import lab.dev.meritoEstudantil.repository.VantagemRepository;

@Service
@Transactional
public class TransacaoService {

	private final TransacaoRepository transacaoRepository;
	private final ProfessorRepository professorRepository;
	private final AlunoRepository alunoRepository;
	private final VantagemRepository vantagemRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;

	public TransacaoService(TransacaoRepository transacaoRepository,
			ProfessorRepository professorRepository,
			AlunoRepository alunoRepository,
			PasswordEncoder passwordEncoder,
			VantagemRepository vantagemRepository,
			EmailService emailService) {
		this.transacaoRepository = transacaoRepository;
		this.professorRepository = professorRepository;
		this.alunoRepository = alunoRepository;
		this.passwordEncoder = passwordEncoder;
		this.vantagemRepository = vantagemRepository;
		this.emailService = emailService;
	}

	public Transacao enviarMoedas(Long professorId, EnviarMoedasDTO dto) {
		Professor professor = professorRepository.findById(professorId)
				.orElseThrow(() -> new ResourceNotFoundException("Professor", professorId));

		Aluno aluno = alunoRepository.findById(dto.alunoId())
				.orElseThrow(() -> new ResourceNotFoundException("Aluno", dto.alunoId()));

		if (professor.getSaldoMoedas() < dto.quantidadeMoedas()) {
			throw new InsufficientBalanceException(professor.getSaldoMoedas(), dto.quantidadeMoedas());
		}

		professor.setSaldoMoedas(professor.getSaldoMoedas() - dto.quantidadeMoedas());
		aluno.setSaldoMoedas(aluno.getSaldoMoedas() + dto.quantidadeMoedas());

		professorRepository.save(professor);
		alunoRepository.save(aluno);

		Transacao transacao = new Transacao(professor, aluno, dto.quantidadeMoedas(), TipoTransacao.ENVIO,
				dto.descricao());

		emailService.sendEmailEnvioMoedas(professor, aluno, dto.quantidadeMoedas(), dto.descricao());

		return transacaoRepository.save(transacao);
	}

	public Transacao registrarResgate(Long alunoId, Long vantagemId, double valor) {
		Aluno aluno = alunoRepository.findById(alunoId)
				.orElseThrow(() -> new ResourceNotFoundException("Aluno", alunoId));
		Vantagem vantagem = vantagemRepository.findById(vantagemId)
				.orElseThrow(() -> new ResourceNotFoundException("Vantagem", vantagemId));

		Transacao transacao = new Transacao(
				null, // professor é null no resgate
				aluno,
				(int)valor,
				TipoTransacao.RESGATE, 
				null);
		transacao.setVantagem(vantagem); // se existir esse campo na entidade Transacao
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
				.orElseThrow(() -> new ResourceNotFoundException("Transação", id));
	}

	public List<Transacao> obterTodas() {
		return transacaoRepository.findAll();
	}
}
