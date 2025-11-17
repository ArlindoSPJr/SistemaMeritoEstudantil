import axios from 'axios';
import LoginService from './LoginService';

const BASE = 'http://localhost:8080';

class TransacaoService {
  getHeaders() {
    const token = LoginService.getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  // Professor envia moedas para um aluno
  async enviarMoedas(dto) {
    const response = await axios.post(`${BASE}/professores/enviar-moedas`, dto, {
      headers: this.getHeaders()
    });
    return response.data;
  }

  // Rotas de listagem baseadas no controller de Transacao
  async listarMeuProfessor() {
    const response = await axios.get(`${BASE}/transacoes/meu-professor`, { headers: this.getHeaders() });
    return response.data;
  }

  async listarMeuAluno() {
    const response = await axios.get(`${BASE}/transacoes/meu-aluno`, { headers: this.getHeaders() });
    return response.data;
  }

  async listarTransacoesAluno(alunoId) {
    const response = await axios.get(`${BASE}/transacoes/aluno/${alunoId}`, { headers: this.getHeaders() });
    return response.data;
  }

  async listarTodas() {
    const response = await axios.get(`${BASE}/transacoes`, { headers: this.getHeaders() });
    return response.data;
  }
}

export default new TransacaoService();
