package MeritoEstudantil.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import MeritoEstudantil.app.controllers.dto.AlunoDTO;
import MeritoEstudantil.app.controllers.dto.AlunoResponseDTO;
import MeritoEstudantil.app.models.Aluno;
import MeritoEstudantil.app.repository.AlunoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService implements UserDetailsService {
    
    @Autowired
    private AlunoRepository clienteRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado com email: " + email));
    }
    
    public AlunoResponseDTO cadastrarCliente(AlunoDTO clienteDTO) {
        // Verificar se já existe cliente com email, CPF ou RG
        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este email");
        }
        
        if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este CPF");
        }
        
        if (clienteRepository.existsByRg(clienteDTO.getRg())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este RG");
        }
        
        // Criar novo cliente
        Aluno cliente = new Aluno();
        cliente.setRg(clienteDTO.getRg());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setNome(clienteDTO.getNome());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setProfissao(clienteDTO.getProfissao());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setSenha(passwordEncoder.encode(clienteDTO.getSenha()));
        
        Aluno clienteSalvo = clienteRepository.save(cliente);
        return convertToResponseDTO(clienteSalvo);
    }
    
    public List<AlunoResponseDTO> listarClientes() {
        List<Aluno> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public AlunoResponseDTO buscarClientePorId(Long id) {
        Aluno cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        return convertToResponseDTO(cliente);
    }
    
    public AlunoResponseDTO atualizarCliente(Long id, AlunoDTO clienteDTO) {
        Aluno cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        
        // Verificar se email, CPF ou RG já existem em outros clientes
        if (!cliente.getEmail().equals(clienteDTO.getEmail()) && 
            clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este email");
        }
        
        if (!cliente.getCpf().equals(clienteDTO.getCpf()) && 
            clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este CPF");
        }
        
        if (!cliente.getRg().equals(clienteDTO.getRg()) && 
            clienteRepository.existsByRg(clienteDTO.getRg())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este RG");
        }
        
        // Atualizar dados do cliente
        cliente.setRg(clienteDTO.getRg());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setNome(clienteDTO.getNome());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setProfissao(clienteDTO.getProfissao());
        cliente.setEmail(clienteDTO.getEmail());
        
        // Atualizar senha apenas se fornecida
        if (clienteDTO.getSenha() != null && !clienteDTO.getSenha().isEmpty()) {
            cliente.setSenha(passwordEncoder.encode(clienteDTO.getSenha()));
        }
        
        Aluno clienteAtualizado = clienteRepository.save(cliente);
        return convertToResponseDTO(clienteAtualizado);
    }
    
    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
    
    private AlunoResponseDTO convertToResponseDTO(Aluno cliente) {
        return new AlunoResponseDTO(cliente);
    }
}
