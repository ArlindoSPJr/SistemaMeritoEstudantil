import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import ProfessorService from '../Services/ProfessorService';
import styles from './Professor.module.css';
import Header from '../Components/header';
import LoginService from '../Services/LoginService';

export default function Professor() {
	const [professores, setProfessores] = useState([]);

	const isGerente = LoginService.hasRole('GERENTE');

	useEffect(() => {
		const fetchProfessores = async () => {
			try {
				const data = await ProfessorService.list();
				setProfessores(data);
			} catch (error) {
				console.error('Erro ao buscar professores:', error);
			}
		};

		fetchProfessores();
	}, []);
	if (!isGerente) {
		return <Navigate to="/" replace />;
	}

	return (
		<div className={styles.container}>
			<Header />
			<h1 className={styles.title}>Lista de Professores</h1>
			<ul className={styles.list}>
				{professores.map((p) => (
					<li key={p.id} className={styles.listItem}>
						<p><strong>Nome:</strong> {p.nome}</p>
						<p><strong>Email:</strong> {p.email}</p>
						<p><strong>CPF:</strong> {p.cpf}</p>
						<p><strong>Departamento:</strong> {p.departamento}</p>
						<p><strong>Saldo Moedas:</strong> {p.saldoMoedas}</p>
						<p><strong>Instituição (ID):</strong> {p.instituicaoEnsino}</p>
					</li>
				))}
			</ul>
		</div>
	);
}
