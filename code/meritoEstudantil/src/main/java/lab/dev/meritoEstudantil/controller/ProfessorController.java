package lab.dev.meritoEstudantil.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lab.dev.meritoEstudantil.domain.professor.Professor;
import lab.dev.meritoEstudantil.domain.transacao.Transacao;
import lab.dev.meritoEstudantil.dto.professor.ProfessorCreateDTO;
import lab.dev.meritoEstudantil.dto.professor.ProfessorResponseDTO;
import lab.dev.meritoEstudantil.dto.transacao.EnviarMoedasDTO;
import lab.dev.meritoEstudantil.dto.transacao.TransacaoResponseDTO;
import lab.dev.meritoEstudantil.service.ProfessorService;
import lab.dev.meritoEstudantil.service.TransacaoService;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

	private final ProfessorService professorService;
	private final TransacaoService transacaoService;

	public ProfessorController(ProfessorService professorService, TransacaoService transacaoService) {
		this.professorService = professorService;
		this.transacaoService = transacaoService;
	}

	@PostMapping
	public ResponseEntity<ProfessorResponseDTO> create(@RequestBody ProfessorCreateDTO dto) {
		Professor professor = new Professor();
		professor.setEmail(dto.email());
		professor.setSenha(dto.senha());
		professor.setNome(dto.nome());
		professor.setCpf(dto.cpf());
		professor.setDepartamento(dto.departamento());
		Professor saved = professorService.create(professor);
		return ResponseEntity.ok(toResponse(saved));
	}

	@GetMapping
	public ResponseEntity<List<ProfessorResponseDTO>> list() {
		return ResponseEntity.ok(professorService.findAll().stream().map(this::toResponse).toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfessorResponseDTO> get(@PathVariable Long id) {
		return ResponseEntity.ok(toResponse(professorService.findById(id)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProfessorResponseDTO> update(@PathVariable Long id, @RequestBody ProfessorCreateDTO dto) {
		Professor update = new Professor();
		update.setNome(dto.nome());
		update.setEmail(dto.email());
		update.setDepartamento(dto.departamento());
		Professor saved = professorService.update(id, update);
		return ResponseEntity.ok(toResponse(saved));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		professorService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Professor envia moedas para um aluno")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Moedas enviadas com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na validação"),
			@ApiResponse(responseCode = "401", description = "Não autenticado")
	})
	@PostMapping("/enviar-moedas")
	@PreAuthorize("hasRole('PROFESSOR')")
	public ResponseEntity<TransacaoResponseDTO> enviarMoedas(
			@RequestBody EnviarMoedasDTO dto,
			Authentication authentication) {
		String email = authentication.getName();
		Professor professor = professorService.findAll().stream()
				.filter(p -> p.getEmail().equals(email))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Professor não encontrado"));

		Transacao transacao = transacaoService.enviarMoedas(professor.getId(), dto);
		return ResponseEntity.ok(toTransacaoResponse(transacao));
	}

	@GetMapping("/{id}/transacoes")
	@PreAuthorize("hasRole('PROFESSOR')")
	public ResponseEntity<List<TransacaoResponseDTO>> obterTransacoes(@PathVariable Long id) {
		return ResponseEntity.ok(transacaoService.obterTransacoesDoProfessor(id).stream()
				.map(this::toTransacaoResponse).toList());
	}

	private ProfessorResponseDTO toResponse(Professor p) {
		return new ProfessorResponseDTO(
				p.getId(),
				p.getEmail(),
				p.getNome(),
				p.getCpf(),
				p.getDepartamento(),
				p.getSaldoMoedas(),
				p.getInstituicaoEnsino() != null ? p.getInstituicaoEnsino().getId() : null);
	}

	private TransacaoResponseDTO toTransacaoResponse(Transacao t) {
		Long professorId = t.getProfessor() != null ? t.getProfessor().getId() : null;
		String professorNome = t.getProfessor() != null ? t.getProfessor().getNome() : null;

		Long alunoId = t.getAluno() != null ? t.getAluno().getId() : null;
		String alunoNome = t.getAluno() != null ? t.getAluno().getNome() : null;

		Long vantagemId = t.getVantagem() != null ? t.getVantagem().getId() : null;
		String vantagemNome = t.getVantagem() != null ? t.getVantagem().getDescricao() : null; // ajuste se o getter tiver
																							// outro nome

		return new TransacaoResponseDTO(
				t.getId(),
				professorId,
				professorNome,
				alunoId,
				alunoNome,
				t.getQuantidadeMoedas(),
				t.getTipo().toString(),
				t.getDescricao(),
				t.getDataCriacao(),
				vantagemId,
				vantagemNome);
	}
}
