// Pequeno wrapper para chamadas à API
export const API_BASE = import.meta.env.VITE_API_URL || 'http://localhost:8080';

export async function postJson(path, body, options = {}) {
  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {}),
  };

  // adiciona Authorization se houver token no localStorage e se o header não foi provido
  try {
    const token = localStorage.getItem('token');
    if (token && !headers.Authorization && !headers.authorization) {
      headers.Authorization = `Bearer ${token}`;
    }
  } catch (err) {
    // ambiente sem localStorage (testes/SSR) -> ignora
  }

  const res = await fetch(`${API_BASE}${path}`, {
    method: 'POST',
    headers,
    body: JSON.stringify(body),
    ...(options.fetchOptions || {}),
  });

  const text = await res.text();
  let data = null;
  try {
    data = text ? JSON.parse(text) : null;
  } catch (err) {
    data = text;
  }

  if (!res.ok) {
    const error = new Error((data && data.message) || res.statusText || 'Erro na requisição');
    error.status = res.status;
    error.body = data;
    throw error;
  }

  return data;
}

export async function getJson(path, options = {}) {
  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {}),
  };

  try {
    const token = localStorage.getItem('token');
    if (token && !headers.Authorization && !headers.authorization) {
      headers.Authorization = `Bearer ${token}`;
    }
  } catch (err) {
    // ambiente sem localStorage -> ignora
  }

  const res = await fetch(`${API_BASE}${path}`, {
    method: 'GET',
    headers,
    ...(options.fetchOptions || {}),
  });

  const text = await res.text();
  let data = null;
  try {
    data = text ? JSON.parse(text) : null;
  } catch (err) {
    data = text;
  }

  if (!res.ok) {
    const error = new Error((data && data.message) || res.statusText || 'Erro na requisição');
    error.status = res.status;
    error.body = data;
    throw error;
  }

  return data;
}
