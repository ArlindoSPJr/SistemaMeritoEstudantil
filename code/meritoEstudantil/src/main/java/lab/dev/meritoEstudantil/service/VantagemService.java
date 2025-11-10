package lab.dev.meritoEstudantil.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;
import lab.dev.meritoEstudantil.domain.vantagem.Vantagem;
import lab.dev.meritoEstudantil.repository.EmpresaParceiraRepository;
import lab.dev.meritoEstudantil.repository.VantagemRepository;

@Service
@Transactional
public class VantagemService {

    private final VantagemRepository vantagemRepository;
    private final CloudinaryService cloudinaryService;
    private final EmpresaParceiraRepository empresaParceiraRepository;

    public VantagemService(VantagemRepository vantagemRepository, CloudinaryService cloudinaryService, EmpresaParceiraRepository empresaParceiraRepository) {
        this.vantagemRepository = vantagemRepository;
        this.empresaParceiraRepository = empresaParceiraRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Vantagem create(Vantagem v) {
        if (v.getEmpresaParceira() != null && v.getEmpresaParceira().getId() != null) {
            EmpresaParceira e = empresaParceiraRepository.findById(v.getEmpresaParceira().getId()).orElseThrow();
            v.setEmpresaParceira(e);
        }
        return vantagemRepository.save(v);
    }

    public List<Vantagem> findAllByEmpresaParceiraId(Long id) {
        return vantagemRepository.findAllByEmpresaParceiraId(id);
    }

    
    public List<Vantagem> findAll() {
        return vantagemRepository.findAll();
    }

    public Vantagem findById(Long id) {
        return vantagemRepository.findById(id).orElseThrow();
    }

    public Vantagem update(Long id, Vantagem update) {
        Vantagem current = findById(id);
        current.setDescricao(update.getDescricao());
        current.setAtivo(update.isAtivo());
        current.setImageUrl(update.getImageUrl());
        current.setCustoMoedas(update.getCustoMoedas());
        if (update.getEmpresaParceira() != null && update.getEmpresaParceira().getId() != null) {
            EmpresaParceira e = empresaParceiraRepository.findById(update.getEmpresaParceira().getId()).orElseThrow();
            current.setEmpresaParceira(e);
        }
        return vantagemRepository.save(current);
    }

    @Transactional
    public void uploadImage(Long id, MultipartFile file) {
        Vantagem vantagem = vantagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Vantagem não encontrado com o ID: " + id));
        String imageUrl = cloudinaryService.uploadFile(file, "products");

        vantagem.setImageUrl(imageUrl);
        vantagemRepository.save(vantagem);
    }

    public void delete(Long id) {
        vantagemRepository.deleteById(id);
    }
}
