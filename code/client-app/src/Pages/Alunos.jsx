import React, { useEffect, useState } from 'react';
import AlunoService from '../Services/AlunoService';
import styles from './Aluno.module.css';
import Header from '../Components/header';

export default function Alunos() {
  const [alunos, setAlunos] = useState([]);

  useEffect(() => {
    const fetchAlunos = async () => {
      try {
        const data = await AlunoService.list();
        setAlunos(data);
      } catch (error) {
        console.error('Erro ao buscar alunos:', error);
      }
    };

    fetchAlunos();
  }, []);

  return (
    <div className={styles.container}>
      <Header />
      <h1 className={styles.title}>Lista de Alunos</h1>
      <ul className={styles.list}>
        {alunos.map((aluno) => (
          <li key={aluno.id} className={styles.listItem}>
            <p><strong>Nome:</strong> {aluno.nome}</p>
            <p><strong>Email:</strong> {aluno.email}</p>
            <p><strong>Curso:</strong> {aluno.curso}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}
