import axios from 'axios';

const API_URL = 'http://localhost:8080/empresas';

class EmpresaService {
  async create(empresa) {
    const response = await axios.post(API_URL, empresa);
    return response.data;
  }

  async list() {
    const response = await axios.get(API_URL);
    return response.data;
  }
}

export default new EmpresaService();
