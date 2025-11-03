package lab.dev.meritoEstudantil.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lab.dev.meritoEstudantil.domain.aluno.Aluno;
import lab.dev.meritoEstudantil.dto.aluno.AlunoCreateDTO;
import lab.dev.meritoEstudantil.dto.aluno.AlunoResponseDTO;
import lab.dev.meritoEstudantil.dto.aluno.AlunoUpdateDTO;
import lab.dev.meritoEstudantil.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

	private final AlunoService alunoService;

	public AlunoController(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<AlunoResponseDTO> create(@RequestBody AlunoCreateDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setEmail(dto.email());
        aluno.setSenha(dto.senha());
        aluno.setCpf(dto.cpf());
        aluno.setRg(dto.rg());
        aluno.setNome(dto.nome());
        aluno.setEndereco(dto.endereco());
        if (dto.saldoMoedas() != null) aluno.setSaldoMoedas(dto.saldoMoedas());
        aluno.setCurso(dto.curso());
        Aluno saved = alunoService.create(aluno);
        return ResponseEntity.ok(toResponse(saved));
    }

	@GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> list() {
        return ResponseEntity.ok(alunoService.findAll().stream().map(this::toResponse).toList());
	}

	@GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(alunoService.findById(id)));
	}

	@PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> update(@PathVariable Long id, @RequestBody AlunoUpdateDTO dto) {
        Aluno update = new Aluno();
        update.setEmail(dto.email());
        update.setSenha(dto.senha());
        update.setCpf(dto.cpf());
        update.setRg(dto.rg());
        update.setNome(dto.nome());
        update.setEndereco(dto.endereco());
        if (dto.saldoMoedas() != null) update.setSaldoMoedas(dto.saldoMoedas());
        update.setCurso(dto.curso());
        Aluno saved = alunoService.update(id, update);
        return ResponseEntity.ok(toResponse(saved));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		alunoService.delete(id);
		return ResponseEntity.noContent().build();
	}

    private AlunoResponseDTO toResponse(Aluno a) {
        return new AlunoResponseDTO(
                a.getId(),
                a.getEmail(),
                a.getCpf(),
                a.getRg(),
                a.getNome(),
                a.getEndereco(),
                a.getSaldoMoedas(),
                a.getCurso()
        );
    }
}


