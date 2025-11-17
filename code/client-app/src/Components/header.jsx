import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styles from './header.module.css';
import LoginService from '../Services/LoginService';
import ModalPerfil from './ModalPerfil';
import AlunoService from '../Services/AlunoService';
import EmpresaService from '../Services/EmpresaService';
import ProfessorService from '../Services/ProfessorService';

export default function Header() {
  const [showProfileModal, setShowProfileModal] = useState(false);
  const [userName, setUserName] = useState('Usuário');

  const isEmpresaParceira = LoginService.hasRole('EMPRESA_PARCEIRA');
  const isAluno = LoginService.hasRole('ALUNO');
  const isGerente = LoginService.hasRole('GERENTE');
  const isProfessor = LoginService.hasRole('PROFESSOR');
  
  // Busca o nome do usuário logado
  useEffect(() => {
    const fetchUserName = async () => {
      try {
        const userId = LoginService.getUserId();
        if (!userId) return;

        if (isEmpresaParceira) {
          const empresa = await EmpresaService.getById(userId);
          setUserName(empresa.nomeFantasia);
        } else if (isAluno) {
          const aluno = await AlunoService.getById(userId);
          setUserName(aluno.nome);
        } else if (isProfessor) {
          const professor = await ProfessorService.getById(userId);
          setUserName(professor.nome);
        } else if (isGerente) {
          setUserName('Gerente');
        }
      } catch (error) {
        console.error('Erro ao buscar nome do usuário:', error);
        setUserName('Usuário');
      }
    };

    fetchUserName();
  }, [isEmpresaParceira, isAluno, isGerente, isProfessor]);

  function handleLogout() {
    LoginService.logout({ redirect: true, redirectTo: '/login' });
  }

  function handleProfileClick() {
    setShowProfileModal(true);
  }

  return (
    <header className={styles.header}>
      <div className={styles.brand}>
        <Link to="/" className={styles.logo}>
          <strong>Merito</strong>
          <span>Estudantil</span>
        </Link>
      </div>

      <nav className={styles.nav} aria-label="Main navigation">
        <Link to="/" className={styles.navLink}>Início</Link>
        {isProfessor ? (
          // Professor vê Início, Alunos e Transações
          <>
            <Link to="/alunos" className={styles.navLink}>Alunos</Link>
            <Link to="/transacoes" className={styles.navLink}>Transações</Link>
          </>
        ) : (
          <>
            {!isAluno && !isEmpresaParceira && (
              <>
                <Link to="/alunos" className={styles.navLink}>Alunos</Link>
                <Link to="/empresas" className={styles.navLink}>Empresas</Link>
              </>
            )}
            {isGerente && (
              <>
                <Link to="/professores" className={styles.navLink}>Professores</Link>
                <Link to="/instituicoes" className={styles.navLink}>Instituições</Link>
              </>
            )}
            <Link to="/vantagens" className={styles.navLink}>Vantagens</Link>
            {(isGerente || isAluno) && (
              <Link to="/transacoes" className={styles.navLink}>Transações</Link>
            )}
          </>
        )}
      </nav>

      <div className={styles.actions}>
        <button className={styles.userProfile} onClick={handleProfileClick}>
          <span className={styles.userAvatar}>
            {isEmpresaParceira ? '🏢' : isGerente ? '🛡️' : isProfessor ? '🎓' : '👤'}
          </span>
          <span className={styles.userName}>{userName}</span>
        </button>
        <button className={styles.logout} onClick={handleLogout}>Sair</button>
      </div>

      {showProfileModal && (
        <ModalPerfil onClose={() => setShowProfileModal(false)} />
      )}
    </header>
  );
}
