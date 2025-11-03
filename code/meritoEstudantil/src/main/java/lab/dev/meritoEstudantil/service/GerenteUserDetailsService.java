package lab.dev.meritoEstudantil.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lab.dev.meritoEstudantil.repository.AlunoRepository;
import lab.dev.meritoEstudantil.repository.EmpresaParceiraRepository;
import lab.dev.meritoEstudantil.repository.GerenteRepository;

@Service
public class GerenteUserDetailsService implements UserDetailsService {

	private final GerenteRepository gerenteRepository;
	private final AlunoRepository alunoRepository;
	private final EmpresaParceiraRepository empresaParceiraRepository;

	public GerenteUserDetailsService(
			GerenteRepository gerenteRepository,
			AlunoRepository alunoRepository,
			EmpresaParceiraRepository empresaParceiraRepository) {
		this.gerenteRepository = gerenteRepository;
		this.alunoRepository = alunoRepository;
		this.empresaParceiraRepository = empresaParceiraRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return gerenteRepository.findByEmail(username)
				.map(UserDetails.class::cast)
				.or(() -> alunoRepository.findByEmail(username).map(UserDetails.class::cast))
				.or(() -> empresaParceiraRepository.findByEmail(username).map(UserDetails.class::cast))
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	}
}


