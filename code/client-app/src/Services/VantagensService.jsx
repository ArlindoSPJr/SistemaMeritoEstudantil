import axios from 'axios';

const API_URL = 'http://localhost:8080/vantagens';

class VantagensService {
  async create(vantagem) {
    const response = await axios.post(API_URL, vantagem);
    return response.data;
  }

  async list() {
    const response = await axios.get(API_URL);
    return response.data;
  }

  async uploadImage(id, imageFile) {
    const formData = new FormData();
    formData.append('image', imageFile);
    await axios.post(`${API_URL}/${id}/image`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  }
}

export default new VantagensService();
