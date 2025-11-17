package lab.dev.meritoEstudantil.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lab.dev.meritoEstudantil.domain.transacao.Transacao;
import lab.dev.meritoEstudantil.dto.transacao.TransacaoResponseDTO;
import lab.dev.meritoEstudantil.service.AlunoService;
import lab.dev.meritoEstudantil.service.ProfessorService;
import lab.dev.meritoEstudantil.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

	private final TransacaoService transacaoService;
	private final ProfessorService professorService;
	private final AlunoService alunoService;

	public TransacaoController(TransacaoService transacaoService, 
							   ProfessorService professorService,
							   AlunoService alunoService) {
		this.transacaoService = transacaoService;
		this.professorService = professorService;
		this.alunoService = alunoService;
	}

	@Operation(summary = "Listar todas as transações do professor autenticado")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Transações do professor"),
		@ApiResponse(responseCode = "401", description = "Não autenticado")
	})
	@GetMapping("/meu-professor")
	@PreAuthorize("hasRole('PROFESSOR')")
	public ResponseEntity<List<TransacaoResponseDTO>> minhasTransacoesProfessor(Authentication authentication) {
		String email = authentication.getName();
		var professor = professorService.findAll().stream()
				.filter(p -> p.getEmail().equals(email))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Professor não encontrado"));

		return ResponseEntity.ok(transacaoService.obterTransacoesDoProfessor(professor.getId()).stream()
				.map(this::toTransacaoResponse).toList());
	}

	@Operation(summary = "Listar todas as transações do aluno autenticado")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Transações do aluno"),
		@ApiResponse(responseCode = "401", description = "Não autenticado")
	})
	@GetMapping("/meu-aluno")
	@PreAuthorize("hasRole('ALUNO')")
	public ResponseEntity<List<TransacaoResponseDTO>> minhasTransacoesAluno(Authentication authentication) {
		String email = authentication.getName();
		var aluno = alunoService.findAll().stream()
				.filter(a -> a.getEmail().equals(email))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

		return ResponseEntity.ok(transacaoService.obterTransacoesDoAluno(aluno.getId()).stream()
				.map(this::toTransacaoResponse).toList());
	}

	@Operation(summary = "Listar todas as transações de um aluno específico (professor)")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Transações do aluno"),
		@ApiResponse(responseCode = "401", description = "Não autenticado"),
		@ApiResponse(responseCode = "404", description = "Aluno não encontrado")
	})
	@GetMapping("/aluno/{alunoId}")
	@PreAuthorize("hasRole('PROFESSOR')")
	public ResponseEntity<List<TransacaoResponseDTO>> transacoesDoAluno(@PathVariable Long alunoId) {
		return ResponseEntity.ok(transacaoService.obterTransacoesDoAluno(alunoId).stream()
				.map(this::toTransacaoResponse).toList());
	}

	@Operation(summary = "Listar todas as transações do sistema (admin/gerente)")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Todas as transações"),
		@ApiResponse(responseCode = "401", description = "Não autenticado")
	})
	@GetMapping
	@PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<List<TransacaoResponseDTO>> todasAsTransacoes() {
		return ResponseEntity.ok(transacaoService.obterTodas().stream()
				.map(this::toTransacaoResponse).toList());
	}

	private TransacaoResponseDTO toTransacaoResponse(Transacao t) {
		return new TransacaoResponseDTO(
			t.getId(),
			t.getProfessor().getId(),
			t.getProfessor().getNome(),
			t.getAluno().getId(),
			t.getAluno().getNome(),
			t.getQuantidadeMoedas(),
			t.getTipo().toString(),
			t.getDescricao(),
			t.getDataCriacao()
		);
	}
}
