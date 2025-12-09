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

import lab.dev.meritoEstudantil.domain.aluno.Aluno;
import lab.dev.meritoEstudantil.dto.aluno.AlunoCreateDTO;
import lab.dev.meritoEstudantil.dto.aluno.AlunoResponseDTO;
import lab.dev.meritoEstudantil.dto.aluno.AlunoUpdateDTO;
import lab.dev.meritoEstudantil.mapper.AlunoMapper;
import lab.dev.meritoEstudantil.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

	private final AlunoService alunoService;
	private final AlunoMapper mapper;

	public AlunoController(AlunoService alunoService, AlunoMapper mapper) {
		this.alunoService = alunoService;
		this.mapper = mapper;
	}

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> create(@RequestBody AlunoCreateDTO dto) {
        Aluno aluno = mapper.toEntity(dto);
        Aluno saved = alunoService.create(aluno);
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
    }

	@GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> list() {
        return ResponseEntity.ok(alunoService.findAll().stream().map(mapper::toResponseDTO).toList());
	}

	@GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponseDTO(alunoService.findById(id)));
	}

	@PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> update(@PathVariable Long id, @RequestBody AlunoUpdateDTO dto) {
        Aluno update = mapper.toEntity(dto);
        Aluno saved = alunoService.update(id, update);
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		alunoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}


