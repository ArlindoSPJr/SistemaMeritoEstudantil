import { postJson } from './Api';

/**
 * Faz o login na API.
 * @param {string} email
 * @param {string} senha
 * @returns {Promise<{token: string}>}
 */
export async function login(email, senha) {
	const response = await postJson('/auth/login', { email, senha });
	try {
		localStorage.setItem('token', response.token);
		// Remove os colchetes e espaços da role
		const role = response.role.replace(/[\[\]]/g, '');
		localStorage.setItem('userRole', role);
		// Salva o userId se existir
		if (response.userId) {
			localStorage.setItem('userId', response.userId.toString());
		}
	} catch (err) {
		console.error('Erro ao salvar dados no localStorage:', err);
	}
	return response;
}

/**
 * Remove o token/localStorage e opcionalmente redireciona para a página de login.
 * @param {{ redirect?: boolean, redirectTo?: string }} [opts]
 */
export function logout(opts = {}) {
	const { redirect = false, redirectTo = '/login' } = opts;
	try {
		localStorage.removeItem('token');
		localStorage.removeItem('userRole');
		localStorage.removeItem('userId');
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

export function getUserRole() {
	try {
		return localStorage.getItem('userRole') || null;
	} catch (err) {
		return null;
	}
}

export function hasRole(role) {
	const userRole = getUserRole();
	return userRole === `ROLE_${role.toUpperCase()}`;
}

export function getUserId() {
	try {
		const userId = localStorage.getItem('userId');
		return userId ? parseInt(userId, 10) : null;
	} catch (err) {
		return null;
	}
}

export default { login, logout, getToken, isAuthenticated, getUserRole, hasRole, getUserId };
