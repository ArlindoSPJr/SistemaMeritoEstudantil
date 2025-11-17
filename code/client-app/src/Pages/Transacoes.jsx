import React, { useEffect, useState } from 'react';
import LoginService from '../Services/LoginService';
import TransacaoService from '../Services/TransacaoService';
import styles from './Transacoes.module.css';
import Header from '../Components/header';

export default function Transacoes() {
  const [transacoes, setTransacoes] = useState([]);
  const [loading, setLoading] = useState(true);

  const isGerente = LoginService.hasRole('GERENTE');
  const isProfessor = LoginService.hasRole('PROFESSOR');
  const isAluno = LoginService.hasRole('ALUNO');

  useEffect(() => {
    const fetch = async () => {
      setLoading(true);
      try {
        let data = [];
        if (isGerente) {
          data = await TransacaoService.listarTodas();
        } else if (isProfessor) {
          data = await TransacaoService.listarMeuProfessor();
        } else if (isAluno) {
          data = await TransacaoService.listarMeuAluno();
        } else {
          data = [];
        }
        setTransacoes(data || []);
      } catch (err) {
        console.error('Erro ao buscar transações:', err);
        setTransacoes([]);
      } finally {
        setLoading(false);
      }
    };

    fetch();
  }, [isGerente, isProfessor, isAluno]);

  function formatDate(d) {
    if (!d) return '-';
    try {
      return new Date(d).toLocaleString();
    } catch (e) {
      return d.toString();
    }
  }

  return (
    <div className={styles.container}>
      <Header />
      <h1 className={styles.title}>Transações</h1>

      {loading ? (
        <p style={{ color: 'white', textAlign: 'center' }}>Carregando...</p>
      ) : (
        <ul className={styles.list}>
          {transacoes.map((t) => (
            <li key={t.id} className={styles.listItem}>
              {isGerente && <p><strong>ID:</strong> {t.id}</p>}
              <p><strong>Professor:</strong> {t.professorNome}</p>
              <p><strong>Aluno:</strong> {t.alunoNome}</p>
              <p><strong>Quantidade:</strong> {t.quantidadeMoedas}</p>
              <p><strong>Tipo:</strong> {t.tipo}</p>
              <p><strong>Descrição:</strong> {t.descricao || '-'}</p>
              <p><strong>Data:</strong> {formatDate(t.dataCriacao)}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
