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

import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;
import lab.dev.meritoEstudantil.dto.empresa.EmpresaCreateDTO;
import lab.dev.meritoEstudantil.dto.empresa.EmpresaResponseDTO;
import lab.dev.meritoEstudantil.dto.empresa.EmpresaUpdateDTO;
import lab.dev.meritoEstudantil.service.EmpresaParceiraService;

@RestController
@RequestMapping("/empresas")
public class EmpresaParceiraController {

	private final EmpresaParceiraService empresaParceiraService;

	public EmpresaParceiraController(EmpresaParceiraService empresaParceiraService) {
		this.empresaParceiraService = empresaParceiraService;
	}

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<EmpresaResponseDTO> create(@RequestBody EmpresaCreateDTO dto) {
        EmpresaParceira empresa = new EmpresaParceira();
        empresa.setEmail(dto.email());
        empresa.setSenha(dto.senha());
        empresa.setNomeFantasia(dto.nomeFantasia());
        empresa.setRazaoSocial(dto.razaoSocial());
        empresa.setCnpj(dto.cnpj());
        EmpresaParceira saved = empresaParceiraService.create(empresa);
        return ResponseEntity.ok(toResponse(saved));
    }

	@GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> list() {
        return ResponseEntity.ok(empresaParceiraService.findAll().stream().map(this::toResponse).toList());
	}

	@GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(empresaParceiraService.findById(id)));
	}

	@PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> update(@PathVariable Long id, @RequestBody EmpresaUpdateDTO dto) {
        EmpresaParceira update = new EmpresaParceira();
        update.setEmail(dto.email());
        update.setSenha(dto.senha());
        update.setNomeFantasia(dto.nomeFantasia());
        update.setRazaoSocial(dto.razaoSocial());
        update.setCnpj(dto.cnpj());
        EmpresaParceira saved = empresaParceiraService.update(id, update);
        return ResponseEntity.ok(toResponse(saved));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		empresaParceiraService.delete(id);
		return ResponseEntity.noContent().build();
	}

    private EmpresaResponseDTO toResponse(EmpresaParceira e) {
        return new EmpresaResponseDTO(
                e.getId(),
                e.getEmail(),
                e.getNomeFantasia(),
                e.getRazaoSocial(),
                e.getCnpj()
        );
    }
}


