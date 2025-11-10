import { postJson } from './Api';

/**
 * Faz o login na API.
 * @param {string} email
 * @param {string} senha
 * @returns {Promise<{token: string}>}
 */
export async function login(email, senha) {
	return postJson('/auth/login', { email, senha });
}

/**
 * Remove o token/localStorage e opcionalmente redireciona para a página de login.
 * @param {{ redirect?: boolean, redirectTo?: string }} [opts]
 */
export function logout(opts = {}) {
	const { redirect = false, redirectTo = '/login' } = opts;
	try {
		localStorage.removeItem('token');
	} catch (err) {
		// ignore
	}
	if (redirect && typeof window !== 'undefined') {
		window.location.href = redirectTo;
	}
}

/**
 * Retorna o token atual (ou null)
 */
export function getToken() {
	try {
		return localStorage.getItem('token');
	} catch (err) {
		return null;
	}
}

export function isAuthenticated() {
	return !!getToken();
}

export default { login, logout, getToken, isAuthenticated };
