import React from 'react';
import styles from './Modal.module.css';

export default function ModalExcluir({ vantagem, onClose, onConfirm }) {
  const handleConfirm = async () => {
    try {
      await onConfirm(vantagem.id);
      onClose();
    } catch (error) {
      console.error('Erro ao excluir vantagem:', error);
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose}>&times;</button>
        <h2 className={styles.modalTitle}>Confirmar Exclusão</h2>
        
        <div style={{ textAlign: 'center', marginBottom: '24px' }}>
          <p style={{ color: 'white', fontSize: '18px', margin: '0 0 16px 0' }}>
            Tem certeza que deseja excluir esta vantagem?
          </p>
          
          <div style={{ 
            background: 'rgba(255, 255, 255, 0.05)', 
            padding: '16px', 
            borderRadius: '8px',
            border: '1px solid rgba(255, 255, 255, 0.1)'
          }}>
            <h3 style={{ color: 'white', margin: '0 0 8px 0', fontSize: '20px' }}>
              {vantagem.descricao}
            </h3>
            <p style={{ color: 'rgba(255, 255, 255, 0.8)', margin: '0', fontSize: '16px' }}>
              Custo: {vantagem.custoMoedas} moedas
            </p>
          </div>
          
          <p style={{ 
            color: '#ef4444', 
            fontSize: '14px', 
            margin: '16px 0 0 0',
            fontWeight: '500' 
          }}>
            ⚠️ Esta ação não pode ser desfeita!
          </p>
        </div>

        <div className={styles.buttonGroup}>
          <button 
            type="button" 
            className={`${styles.button} ${styles.secondaryButton}`} 
            onClick={onClose}
          >
            Cancelar
          </button>
          <button 
            type="button" 
            className={`${styles.button}`}
            onClick={handleConfirm}
            style={{
              background: 'linear-gradient(90deg, #ef4444, #dc2626)',
              color: 'white'
            }}
          >
            Excluir
          </button>
        </div>
      </div>
    </div>
  );
}
