import axios from 'axios';

const API_URL = 'http://localhost:8080/alunos';

class AlunoService {
  async create(aluno) {
    const response = await axios.post(API_URL, aluno);
    return response.data;
  }

  async list() {
    const response = await axios.get(API_URL);
    return response.data;
  }
}

export default new AlunoService();
