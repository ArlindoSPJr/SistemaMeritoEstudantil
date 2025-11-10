import React, { useEffect, useState } from 'react';
import VantagensService from '../Services/VantagensService';
import styles from './Vantagens.module.css';
import Header from '../Components/header';
import ModalCriarVantagem from '../Components/ModalCriarvantagem';

export default function Vantagens() {
  const [vantagens, setVantagens] = useState([]);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    const fetchVantagens = async () => {
      try {
        const data = await VantagensService.list();
        setVantagens(data);
      } catch (error) {
        console.error('Erro ao buscar vantagens:', error);
      }
    };

    fetchVantagens();
  }, []);

  return (
    <div className={styles.container}>
      <Header />
      <div className={styles.header}>
        <h1 className={styles.title}>Vantagens</h1>
        <button className={styles.addButton} onClick={() => setShowModal(true)}>
          Criar Vantagem
        </button>
      </div>
      <div className={styles.grid}>
        {vantagens.map((vantagem) => (
          <div key={vantagem.id} className={styles.card}>
            {vantagem.imageUrl && (
              <img
                src={vantagem.imageUrl}
                alt={vantagem.descricao}
                className={styles.cardImage}
              />
            )}
            <div className={styles.cardContent}>
              <h3 className={styles.cardTitle}>{vantagem.descricao}</h3>
              <p className={styles.cardPrice}>Custo: {vantagem.custoMoedas} moedas</p>
            </div>
          </div>
        ))}
      </div>
      {showModal && <ModalCriarVantagem onClose={() => setShowModal(false)} />}
    </div>
  );
}
