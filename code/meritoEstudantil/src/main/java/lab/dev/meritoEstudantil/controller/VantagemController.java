package lab.dev.meritoEstudantil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lab.dev.meritoEstudantil.domain.vantagem.Vantagem;
import lab.dev.meritoEstudantil.dto.vantagem.VantagemCreateDTO;
import lab.dev.meritoEstudantil.dto.vantagem.VantagemResponseDTO;
import lab.dev.meritoEstudantil.dto.vantagem.VantagemUpdateDTO;
import lab.dev.meritoEstudantil.dto.vantagem.ResgatarVantagemDTO;
import lab.dev.meritoEstudantil.mapper.VantagemMapper;
import lab.dev.meritoEstudantil.service.VantagemService;

@RestController
@RequestMapping("/vantagens")
public class VantagemController {

    private final VantagemService vantagemService;
    private final VantagemMapper mapper;

    public VantagemController(VantagemService vantagemService, VantagemMapper mapper) {
        this.vantagemService = vantagemService;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPRESA_PARCEIRA')")
    public ResponseEntity<VantagemResponseDTO> create(@RequestBody VantagemCreateDTO dto) {
        Vantagem v = mapper.toEntity(dto);
        Vantagem saved = vantagemService.create(v);
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
    }

    // LISTA VANTAGENS DE UM ALUNO
    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<VantagemResponseDTO>> getAlunoVantagens(@PathVariable Long alunoId) {
        return ResponseEntity.ok(vantagemService.getAlunoVantagens(alunoId).stream().map(mapper::toResponseDTO).toList());
    }


    // LISTA TODOS POR EMPRESA
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<VantagemResponseDTO>> findByEmpresaId(@PathVariable Long empresaId) {
        return ResponseEntity.ok(vantagemService.findAllByEmpresaParceiraId(empresaId).stream().map(mapper::toResponseDTO).toList());
    }

    // LISTA TODOS - PARA O ALUNO
    @GetMapping
    public ResponseEntity<List<VantagemResponseDTO>> list() {
        return ResponseEntity.ok(vantagemService.findAll().stream().map(mapper::toResponseDTO).toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<VantagemResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponseDTO(vantagemService.findById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPRESA_PARCEIRA')")
    public ResponseEntity<VantagemResponseDTO> update(@PathVariable Long id, @RequestBody VantagemUpdateDTO dto) {
        Vantagem update = mapper.toEntity(dto);
        Vantagem saved = vantagemService.update(id, update);
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPRESA_PARCEIRA')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vantagemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Faz upload de uma imagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Upload realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro no upload")
    })
    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('EMPRESA_PARCEIRA')")
    public ResponseEntity<Void> uploadProductImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        vantagemService.uploadImage(id, file);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Resgate uma vantagem como aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vantagem resgatada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro no resgate (saldo insuficiente, vantagem inativa ou esgotada)"),
            @ApiResponse(responseCode = "404", description = "Vantagem ou aluno não encontrado")
    })
    @PostMapping("/{id}/resgatar")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<Void> resgatarVantagem(@PathVariable Long id, @RequestBody ResgatarVantagemDTO dto) {
        vantagemService.resgatarVantagem(id, dto.alunoId());
        return ResponseEntity.noContent().build();
    }
}
