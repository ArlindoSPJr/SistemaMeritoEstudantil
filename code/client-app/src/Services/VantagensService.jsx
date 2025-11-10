import axios from 'axios';
import LoginService from './LoginService';

const API_URL = 'http://localhost:8080/vantagens';

class VantagensService {
  getHeaders() {
    const token = LoginService.getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  async create(vantagem) {
    const response = await axios.post(API_URL, vantagem, {
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

  async uploadImage(id, imageFile) {
    const formData = new FormData();
    formData.append('image', imageFile);
    await axios.post(`${API_URL}/${id}/image`, formData, {
      headers: {
        ...this.getHeaders(),
        'Content-Type': 'multipart/form-data',
      },
    });
  }

  async delete(id) {
    await axios.delete(`${API_URL}/${id}`, {
      headers: this.getHeaders()
    });
  }

  async update(id, vantagemData) {
    const response = await axios.put(`${API_URL}/${id}`, vantagemData, {
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

  async listByEmpresa(empresaId) {
    const response = await axios.get(`${API_URL}/empresa/${empresaId}`, {
      headers: this.getHeaders()
    });
    return response.data;
  }
}

export default new VantagensService();
