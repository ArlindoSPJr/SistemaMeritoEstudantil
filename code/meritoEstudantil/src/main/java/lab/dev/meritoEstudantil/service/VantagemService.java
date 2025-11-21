package lab.dev.meritoEstudantil.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;
import lab.dev.meritoEstudantil.domain.vantagem.Vantagem;
import lab.dev.meritoEstudantil.repository.AlunoRepository;
import lab.dev.meritoEstudantil.repository.EmpresaParceiraRepository;
import lab.dev.meritoEstudantil.repository.VantagemRepository;

@Service
@Transactional
public class VantagemService {

    private final VantagemRepository vantagemRepository;
    private final AlunoRepository alunoRepository;
    private final CloudinaryService cloudinaryService;
    private final EmpresaParceiraRepository empresaParceiraRepository;
    private final TransacaoService transacaoService;

    public VantagemService(VantagemRepository vantagemRepository,TransacaoService transacaoService, AlunoRepository alunoRepository, CloudinaryService cloudinaryService, EmpresaParceiraRepository empresaParceiraRepository) {
        this.vantagemRepository = vantagemRepository;
        this.alunoRepository = alunoRepository;
        this.empresaParceiraRepository = empresaParceiraRepository;
        this.cloudinaryService = cloudinaryService;
        this.transacaoService = transacaoService;
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

    public List<Vantagem> getAlunoVantagens(Long alunoId) {
        var aluno = alunoRepository.findById(alunoId).orElseThrow();
        return aluno.getVantagens();
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

    public void resgatarVantagem(Long vantagemId, Long alunoId) {
        
        var vantagem = vantagemRepository.findById(vantagemId).orElseThrow();
        var aluno = alunoRepository.findById(alunoId).orElseThrow();

        if (!vantagem.isAtivo()) {
            throw new RuntimeException("Vantagem inativa.");
        }

        if (vantagem.getQuantidade() <= 0) {
            throw new RuntimeException("Vantagem esgotada.");
        }

        if (aluno.getSaldoMoedas() < vantagem.getCustoMoedas()) {
            throw new RuntimeException("Saldo insuficiente de moedas.");
        }

        if (aluno.getVantagens().stream().anyMatch(v -> v.getId().equals(vantagemId))) {
            throw new RuntimeException("Vantagem já resgatada por este aluno.");
        }

        aluno.setSaldoMoedas((int)(aluno.getSaldoMoedas() - vantagem.getCustoMoedas()));
        vantagem.setQuantidade(vantagem.getQuantidade() - 1);

        vantagem.addAluno(aluno);
        aluno.addVantagem(vantagem);

        transacaoService.registrarResgate(alunoId, vantagemId, vantagem.getCustoMoedas());
        
        alunoRepository.save(aluno);
        vantagemRepository.save(vantagem);

    }
}
