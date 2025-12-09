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

import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;
import lab.dev.meritoEstudantil.dto.empresa.EmpresaCreateDTO;
import lab.dev.meritoEstudantil.dto.empresa.EmpresaResponseDTO;
import lab.dev.meritoEstudantil.dto.empresa.EmpresaUpdateDTO;
import lab.dev.meritoEstudantil.mapper.EmpresaParceiraMapper;
import lab.dev.meritoEstudantil.service.EmpresaParceiraService;

@RestController
@RequestMapping("/empresas")
public class EmpresaParceiraController {

	private final EmpresaParceiraService empresaParceiraService;
	private final EmpresaParceiraMapper mapper;

	public EmpresaParceiraController(EmpresaParceiraService empresaParceiraService, EmpresaParceiraMapper mapper) {
		this.empresaParceiraService = empresaParceiraService;
		this.mapper = mapper;
	}

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> create(@RequestBody EmpresaCreateDTO dto) {
        EmpresaParceira empresa = mapper.toEntity(dto);
        EmpresaParceira saved = empresaParceiraService.create(empresa);
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
    }

	@GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> list() {
        return ResponseEntity.ok(empresaParceiraService.findAll().stream().map(mapper::toResponseDTO).toList());
	}

	@GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponseDTO(empresaParceiraService.findById(id)));
	}

	@PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> update(@PathVariable Long id, @RequestBody EmpresaUpdateDTO dto) {
        EmpresaParceira update = mapper.toEntity(dto);
        EmpresaParceira saved = empresaParceiraService.update(id, update);
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		empresaParceiraService.delete(id);
		return ResponseEntity.noContent().build();
	}
}


