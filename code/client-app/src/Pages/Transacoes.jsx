import React, { useEffect, useState } from 'react';
import LoginService from '../Services/LoginService';
import TransacaoService from '../Services/TransacaoService';
import styles from './Transacoes.module.css';
import Header from '../Components/header';

export default function Transacoes() {
  const [transacoes, setTransacoes] = useState([]);
  const [transacoesFiltradas, setTransacoesFiltradas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filtroTipo, setFiltroTipo] = useState('TODOS'); // 'TODOS', 'ENVIO', 'RESGATE'

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
        setTransacoesFiltradas(data || []);
      } catch (err) {
        console.error('Erro ao buscar transações:', err);
        setTransacoes([]);
        setTransacoesFiltradas([]);
      } finally {
        setLoading(false);
      }
    };

    fetch();
  }, [isGerente, isProfessor, isAluno]);

  // Aplicar filtro quando o tipo de filtro ou transações mudam
  useEffect(() => {
    if (filtroTipo === 'TODOS') {
      setTransacoesFiltradas(transacoes);
    } else if (filtroTipo === 'ENVIO') {
      setTransacoesFiltradas(transacoes.filter(t => t.tipo === 'ENVIO'));
    } else if (filtroTipo === 'RESGATE') {
      setTransacoesFiltradas(transacoes.filter(t => t.tipo === 'RESGATE'));
    }
  }, [filtroTipo, transacoes]);

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
      <div className={styles.header}>
        <h1 className={styles.title}>Transações</h1>
        <div className={styles.filterButtons}>
          {!isProfessor && (
            <button 
              className={`${styles.filterButton} ${filtroTipo === 'TODOS' ? styles.active : ''}`}
              onClick={() => setFiltroTipo('TODOS')}
            >
              Todas
            </button>
          )}
          <button 
            className={`${styles.filterButton} ${filtroTipo === 'ENVIO' ? styles.active : ''}`}
            onClick={() => setFiltroTipo('ENVIO')}
          >
            {isProfessor ? '📤 Envios' : '💰 Recebimentos'}
          </button>
          {isAluno && (
            <button 
              className={`${styles.filterButton} ${filtroTipo === 'RESGATE' ? styles.active : ''}`}
              onClick={() => setFiltroTipo('RESGATE')}
            >
              🎁 Resgates
            </button>
          )}
        </div>
      </div>

      {loading ? (
        <p style={{ color: 'white', textAlign: 'center' }}>Carregando...</p>
      ) : (
        <ul className={styles.list}>
          {transacoesFiltradas.map((t) => (
            <li key={t.id} className={styles.listItem}>
              {isGerente && <p><strong>ID:</strong> {t.id}</p>}
              {t.professorNome && <p><strong>Professor:</strong> {t.professorNome}</p>}
              {t.alunoNome && <p><strong>Aluno:</strong> {t.alunoNome}</p>}
              <p><strong>Quantidade:</strong> {t.quantidadeMoedas} moedas</p>
              <p><strong>Tipo:</strong> {t.tipo}</p>
              {t.vantagemNome && <p><strong>Vantagem:</strong> {t.vantagemNome}</p>}
              {t.descricao && <p><strong>Descrição:</strong> {t.descricao}</p>}
              <p><strong>Data:</strong> {formatDate(t.dataCriacao)}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
