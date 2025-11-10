import React from 'react';
import { Link } from 'react-router-dom';
import styles from './header.module.css';
import LoginService from '../Services/LoginService';

export default function Header() {
  function handleLogout() {
    LoginService.logout({ redirect: true, redirectTo: '/login' });
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
        <Link to="/alunos" className={styles.navLink}>Alunos</Link>
        <Link to="/empresas" className={styles.navLink}>Empresas</Link>
        <Link to="/vantagens" className={styles.navLink}>Vantagens</Link>
      </nav>

      <div className={styles.actions}>
        <button className={styles.logout} onClick={handleLogout}>Sair</button>
      </div>
    </header>
  );
}
