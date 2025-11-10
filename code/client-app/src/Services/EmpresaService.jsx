import axios from 'axios';
import LoginService from './LoginService';

const API_URL = 'http://localhost:8080/empresas';

class EmpresaService {
  getHeaders() {
    const token = LoginService.getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  async create(empresa) {
    const response = await axios.post(API_URL, empresa, {
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

  async update(id, empresa) {
    const response = await axios.put(`${API_URL}/${id}`, empresa, {
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

export default new EmpresaService();
