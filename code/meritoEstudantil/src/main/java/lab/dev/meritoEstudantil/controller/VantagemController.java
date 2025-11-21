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
import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;
import lab.dev.meritoEstudantil.domain.vantagem.Vantagem;
import lab.dev.meritoEstudantil.dto.vantagem.VantagemCreateDTO;
import lab.dev.meritoEstudantil.dto.vantagem.VantagemResponseDTO;
import lab.dev.meritoEstudantil.dto.vantagem.VantagemUpdateDTO;
import lab.dev.meritoEstudantil.dto.vantagem.ResgatarVantagemDTO;
import lab.dev.meritoEstudantil.service.VantagemService;

@RestController
@RequestMapping("/vantagens")
public class VantagemController {

    @Autowired
    private VantagemService vantagemService;

    @PostMapping
    @PreAuthorize("hasRole('EMPRESA_PARCEIRA')")
    public ResponseEntity<VantagemResponseDTO> create(@RequestBody VantagemCreateDTO dto) {
        Vantagem v = new Vantagem();
        v.setDescricao(dto.descricao());
        v.setAtivo(true);
        v.setCustoMoedas(dto.custoMoedas());
        v.setQuantidade(dto.quantidade());
        if (dto.empresaParceiraId() != null) {
            EmpresaParceira empresa = new EmpresaParceira();
            try {
                java.lang.reflect.Field idField = EmpresaParceira.class.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(empresa, dto.empresaParceiraId());
            } catch (Exception ex) {
                // se falhar, deixamos sem id e o service lançará erro
            }
            v.setEmpresaParceira(empresa);
        }
        Vantagem saved = vantagemService.create(v);
        return ResponseEntity.ok(toResponse(saved));
    }

    // LISTA VANTAGENS DE UM ALUNO
    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<VantagemResponseDTO>> getAlunoVantagens(@PathVariable Long alunoId) {
        return ResponseEntity.ok(vantagemService.getAlunoVantagens(alunoId).stream().map(this::toResponse).toList());
    }


    // LISTA TODOS POR EMPRESA
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<VantagemResponseDTO>> findByEmpresaId(@PathVariable Long empresaId) {
        return ResponseEntity.ok(vantagemService.findAllByEmpresaParceiraId(empresaId).stream().map(this::toResponse).toList());
    }

    // LISTA TODOS - PARA O ALUNO
    @GetMapping
    public ResponseEntity<List<VantagemResponseDTO>> list() {
        return ResponseEntity.ok(vantagemService.findAll().stream().map(this::toResponse).toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<VantagemResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(vantagemService.findById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPRESA_PARCEIRA')")
    public ResponseEntity<VantagemResponseDTO> update(@PathVariable Long id, @RequestBody VantagemUpdateDTO dto) {
        Vantagem update = new Vantagem();
        update.setDescricao(dto.descricao());
        if (dto.ativo() != null)
            update.setAtivo(dto.ativo());
        if (dto.imageUrl() != null)
            update.setImageUrl(dto.imageUrl());
        if (dto.custoMoedas() != null)
            update.setCustoMoedas(dto.custoMoedas());
        if (dto.quantidade() != null)
            update.setQuantidade(dto.quantidade());
        if (dto.empresaParceiraId() != null) {
            EmpresaParceira empresa = new EmpresaParceira();
            try {
                java.lang.reflect.Field idField = EmpresaParceira.class.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(empresa, dto.empresaParceiraId());
            } catch (Exception ex) {
            }
            update.setEmpresaParceira(empresa);
        }
        Vantagem saved = vantagemService.update(id, update);
        return ResponseEntity.ok(toResponse(saved));
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

    private VantagemResponseDTO toResponse(Vantagem e) {
        Long empresaId = e.getEmpresaParceira() != null ? e.getEmpresaParceira().getId() : null;
        String empresaNome = e.getEmpresaParceira() != null ? e.getEmpresaParceira().getNomeFantasia() : null;
        return new VantagemResponseDTO(
                e.getId(),
                e.getDescricao(),
                e.isAtivo(),
                e.getImageUrl(),
                e.getCustoMoedas(),
                empresaId,
                empresaNome,
                e.getQuantidade());
    }
}
