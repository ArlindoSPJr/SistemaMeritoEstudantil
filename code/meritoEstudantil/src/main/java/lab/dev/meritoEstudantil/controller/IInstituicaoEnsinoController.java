package lab.dev.meritoEstudantil.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lab.dev.meritoEstudantil.domain.instituicao.IInstituicaoEnsino;
import lab.dev.meritoEstudantil.dto.instituicao.IInstituicaoEnsinoCreateDTO;
import lab.dev.meritoEstudantil.dto.instituicao.IInstituicaoEnsinoResponseDTO;
import lab.dev.meritoEstudantil.service.IInstituicaoEnsinoService;

@RestController
@RequestMapping("/instituicoes")
public class IInstituicaoEnsinoController {

	private final IInstituicaoEnsinoService instituicaoService;

	public IInstituicaoEnsinoController(IInstituicaoEnsinoService instituicaoService) {
		this.instituicaoService = instituicaoService;
	}

	@PostMapping
	public ResponseEntity<IInstituicaoEnsinoResponseDTO> create(@RequestBody IInstituicaoEnsinoCreateDTO dto) {
		IInstituicaoEnsino instituicao = new IInstituicaoEnsino();
		instituicao.setNome(dto.nome());
		instituicao.setEndereco(dto.endereco());
		IInstituicaoEnsino saved = instituicaoService.create(instituicao);
		return ResponseEntity.ok(toResponse(saved));
	}

	@GetMapping
	public ResponseEntity<List<IInstituicaoEnsinoResponseDTO>> list() {
		return ResponseEntity.ok(instituicaoService.findAll().stream().map(this::toResponse).toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<IInstituicaoEnsinoResponseDTO> get(@PathVariable Long id) {
		return ResponseEntity.ok(toResponse(instituicaoService.findById(id)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<IInstituicaoEnsinoResponseDTO> update(@PathVariable Long id, @RequestBody IInstituicaoEnsinoCreateDTO dto) {
		IInstituicaoEnsino update = new IInstituicaoEnsino();
		update.setNome(dto.nome());
		update.setEndereco(dto.endereco());
		IInstituicaoEnsino saved = instituicaoService.update(id, update);
		return ResponseEntity.ok(toResponse(saved));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		instituicaoService.delete(id);
		return ResponseEntity.noContent().build();
	}

	private IInstituicaoEnsinoResponseDTO toResponse(IInstituicaoEnsino i) {
		return new IInstituicaoEnsinoResponseDTO(
			i.getId(),
			i.getNome(),
			i.getEndereco()
		);
	}
}
