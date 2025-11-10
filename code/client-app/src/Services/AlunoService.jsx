import axios from 'axios';
import LoginService from './LoginService';

const API_URL = 'http://localhost:8080/alunos';

class AlunoService {
  getHeaders() {
    const token = LoginService.getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  async create(aluno) {
    const response = await axios.post(API_URL, aluno, {
      headers: this.getHeaders()
    });
    return response.data;
  }

  async list() {
    const response = await axios.get(API_URL, {
      headers: this.getHeaders()
    });
    return response.data;
  }

  async getById(id) {
    const response = await axios.get(`${API_URL}/${id}`, {
      headers: this.getHeaders()
    });
    return response.data;
  }

  async update(id, aluno) {
    const response = await axios.put(`${API_URL}/${id}`, aluno, {
      headers: this.getHeaders()
    });
    return response.data;
  }

  async delete(id) {
    await axios.delete(`${API_URL}/${id}`, {
      headers: this.getHeaders()
    });
  }
}

export default new AlunoService();
