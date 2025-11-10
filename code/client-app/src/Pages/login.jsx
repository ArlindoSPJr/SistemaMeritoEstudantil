import React, { useState } from 'react';
import { login as loginService } from '../Services/LoginService';
import FormularioAutoCadastroAluno from '../Components/FormularioAutoCadastroAluno';
import FormularioAutoCadastroEmpresa from '../Components/FormularioAutoCadastroEmpresa';
import styles from './login.module.css';

export default function Login() {
	const [email, setEmail] = useState('');
	const [senha, setSenha] = useState('');
	const [loading, setLoading] = useState(false);
	const [error, setError] = useState('');
	const [showCadastro, setShowCadastro] = useState(false);
	const [tipoCadastro, setTipoCadastro] = useState(null);
	const [showModal, setShowModal] = useState(false);

	async function handleSubmit(e) {
		e.preventDefault();
		setError('');
		setLoading(true);
		try {
			const res = await loginService(email, senha);
			if (res && res.token) {
				localStorage.setItem('token', res.token);
				window.location.href = '/';
			} else {
				setError('Resposta inválida do servidor');
			}
		} catch (err) {
			const msg = err?.body?.message || err?.message || 'Erro ao autenticar';
			setError(msg);
		} finally {
			setLoading(false);
		}
	}

	return (
		<div className={styles.page}>
			<div className={styles.container}>
				<div className={styles.brand}>
					<h1>Merito<span>Estudantil</span></h1>
					<p>Plataforma de gestão de mérito escolar.</p>
				</div>

				<div className={styles.formWrap}>
					{showCadastro ? (
						tipoCadastro === 'aluno' ? (
							<FormularioAutoCadastroAluno onBack={() => {
								setShowCadastro(false);
								setTipoCadastro(null);
							}} />
						) : (
							<FormularioAutoCadastroEmpresa onBack={() => {
								setShowCadastro(false);
								setTipoCadastro(null);
							}} />
						)
					) : (
						<form className={styles.card} onSubmit={handleSubmit} noValidate>
							<h2 className={styles.title}>Entrar na conta</h2>

							{error && <div className={styles.error} role="alert">{error}</div>}

							<label className={styles.label} htmlFor="email">Email</label>
							<input
								id="email"
								className={styles.input}
								type="email"
								value={email}
								onChange={(e) => setEmail(e.target.value)}
								required
								placeholder="seu@exemplo.com"
								autoComplete="email"
							/>

							<label className={styles.label} htmlFor="senha">Senha</label>
							<input
								id="senha"
								className={styles.input}
								type="password"
								value={senha}
								onChange={(e) => setSenha(e.target.value)}
								required
								placeholder="••••••••"
								autoComplete="current-password"
							/>

							<div className={styles.actions}>
								<label className={styles.remember}>
									<input type="checkbox" /> Lembrar-me
								</label>
								<a className={styles.forgot} href="#">Esqueceu a senha?</a>
							</div>

							<button className={styles.button} type="submit" disabled={loading} aria-busy={loading}>
								{loading ? 'Entrando...' : 'Entrar'}
							</button>

							<div className={styles.footer}>
								Ainda não tem conta?{' '}
								<a href="#" onClick={() => setShowModal(true)}>Cadastre-se</a>
							</div>
						</form>
					)}
				</div>
			</div>

			{showModal && (
				<div className={styles.modalOverlay}>
					<div className={styles.modalContent}>
						<h2 style={{ color: 'black' }}>Escolha o tipo de cadastro</h2>
						<div className={styles.modalActions}>
							<button className={styles.modalButton} onClick={() => {
								setTipoCadastro('aluno');
								setShowCadastro(true);
								setShowModal(false);
							}}>Aluno</button>
							<button className={styles.modalButton} onClick={() => {
								setTipoCadastro('empresa');
								setShowCadastro(true);
								setShowModal(false);
							}}>Empresa</button>
						</div>
					</div>
				</div>
			)}
		</div>
	);
}
