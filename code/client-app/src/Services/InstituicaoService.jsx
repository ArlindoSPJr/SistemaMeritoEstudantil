import axios from 'axios';
import LoginService from './LoginService';

const API_URL = 'http://localhost:8080/instituicoes';

class InstituicaoService {
  getHeaders() {
    const token = LoginService.getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
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
}

export default new InstituicaoService();
