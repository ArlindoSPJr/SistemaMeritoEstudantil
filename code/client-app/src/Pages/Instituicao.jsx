import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import InstituicaoService from '../Services/InstituicaoService';
import styles from './Instituicao.module.css';
import Header from '../Components/header';
import LoginService from '../Services/LoginService';

export default function Instituicao() {
	const [instituicoes, setInstituicoes] = useState([]);

	if (!LoginService.hasRole('GERENTE')) {
		return <Navigate to="/" replace />;
	}

	useEffect(() => {
		const fetchInstituicoes = async () => {
			try {
				const data = await InstituicaoService.list();
				setInstituicoes(data);
			} catch (error) {
				console.error('Erro ao buscar instituições:', error);
			}
		};

		fetchInstituicoes();
	}, []);

	return (
		<div className={styles.container}>
			<Header />
			<h1 className={styles.title}>Lista de Instituições</h1>
			<ul className={styles.list}>
				{instituicoes.map((i) => (
					<li key={i.id} className={styles.listItem}>
						<p><strong>Nome:</strong> {i.nome}</p>
						<p><strong>Endereço:</strong> {i.endereco}</p>
					</li>
				))}
			</ul>
		</div>
	);
}
