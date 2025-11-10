import React from 'react';
import Header from '../Components/header';
import styles from './home.module.css';

export default function Home() {
  return (
    <div className={styles.page}>
      <Header />

      <main className={styles.main}>
        <section className={styles.hero}>
          <div className={styles.heroContent}>
            <h1>Bem-vindo ao MeritoEstudantil</h1>
            <p>Gerencie alunos, empresas parceiras e reconheça o mérito dos estudantes com facilidade.</p>
            <div className={styles.ctaRow}>
              <button className={styles.ctaPrimary}>Criar novo registro</button>
              <button className={styles.ctaGhost}>Ver relatórios</button>
            </div>
          </div>
        </section>

        <section className={styles.grid}>
          <article className={styles.card}>
            <h3>Alunos</h3>
            <p>Administre informações e progresso dos alunos.</p>
          </article>
          <article className={styles.card}>
            <h3>Empresas Parceiras</h3>
            <p>Gerencie parcerias e oportunidades para estudantes.</p>
          </article>
          <article className={styles.card}>
            <h3>Relatórios</h3>
            <p>Visualize estatísticas e desempenho em tempo real.</p>
          </article>
        </section>
      </main>
    </div>
  );
}
