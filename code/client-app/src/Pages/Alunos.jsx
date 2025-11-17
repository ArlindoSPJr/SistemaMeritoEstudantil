import React, { useEffect, useState } from 'react';
import AlunoService from '../Services/AlunoService';
import LoginService from '../Services/LoginService';
import styles from './Aluno.module.css';
import Header from '../Components/header';
import ModalEnviarMoeda from '../Components/ModalEnviarMoeda';

export default function Alunos() {
  const [alunos, setAlunos] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedAluno, setSelectedAluno] = useState(null);

  const isProfessor = LoginService.hasRole('PROFESSOR');

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

  function openModal(aluno) {
    setSelectedAluno(aluno);
    setShowModal(true);
  }

  function closeModal() {
    setSelectedAluno(null);
    setShowModal(false);
  }

  return (
    <div className={styles.container}>
      <Header />
      <h1 className={styles.title}>Lista de Alunos</h1>
      <ul className={styles.list}>
        {alunos.map((aluno) => (
          <li key={aluno.id} className={styles.listItem}>
            <div className={styles.cardInner}>
              <div className={styles.cardInfo}>
                <p><strong>Nome:</strong> {aluno.nome}</p>
                <p><strong>Email:</strong> {aluno.email}</p>
                <p><strong>Curso:</strong> {aluno.curso}</p>
              </div>

              {isProfessor && (
                <div className={styles.cardActions}>
                  <button
                    onClick={() => openModal(aluno)}
                    className={styles.coinButton}
                    title={`Enviar moedas para ${aluno.nome}`}
                  >
                    <span className={styles.coinIcon}>🪙</span>
                    <span>Enviar</span>
                  </button>
                </div>
              )}
            </div>
          </li>
        ))}
      </ul>

      {showModal && selectedAluno && (
        <ModalEnviarMoeda
          aluno={selectedAluno}
          onClose={closeModal}
          onSuccess={() => {
            // atualizar lista e notificar sucesso
            window.alert('Moedas enviadas com sucesso');
            // opcional: refetch
          }}
        />
      )}
    </div>
  );
}
