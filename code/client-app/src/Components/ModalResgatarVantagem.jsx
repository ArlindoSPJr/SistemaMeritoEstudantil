import React, { useState } from 'react';
import styles from './Modal.module.css';
import VantagensService from '../Services/VantagensService';
import LoginService from '../Services/LoginService';

export default function ModalResgatarVantagem({ vantagem, onClose, onSuccess }) {
  const [loading, setLoading] = useState(false);

  const handleResgatar = async () => {
    setLoading(true);
    try {
      const alunoId = LoginService.getUserId();
      if (!alunoId) {
        alert('Usuário não está logado.');
        return;
      }

      await VantagensService.resgatar(vantagem.id, alunoId);
      alert('Vantagem resgatada com sucesso!');
      onSuccess?.();
      onClose();
      window.location.reload();
    } catch (error) {
      console.error('Erro ao resgatar vantagem:', error);
      const errorMsg = error.response?.data?.message || 'Erro ao resgatar vantagem. Verifique seu saldo ou disponibilidade.';
      alert(errorMsg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose}>&times;</button>
        
        <h2 className={styles.modalTitle}>Resgatar Vantagem</h2>
        
        <div style={{ marginBottom: '24px' }}>
          <div style={{ textAlign: 'center', marginBottom: '20px' }}>
            {vantagem.imageUrl ? (
              <img
                src={vantagem.imageUrl}
                alt={vantagem.descricao}
                style={{
                  width: '120px',
                  height: '120px',
                  borderRadius: '50%',
                  objectFit: 'cover',
                  border: '3px solid rgba(255, 255, 255, 0.3)'
                }}
              />
            ) : (
              <div style={{
                width: '120px',
                height: '120px',
                borderRadius: '50%',
                background: 'rgba(255, 255, 255, 0.1)',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                fontSize: '48px',
                margin: '0 auto',
                border: '3px solid rgba(255, 255, 255, 0.3)'
              }}>
                📦
              </div>
            )}
          </div>
          
          <p style={{ 
            color: 'rgba(255, 255, 255, 0.9)', 
            fontSize: '18px', 
            fontWeight: '600',
            marginBottom: '8px',
            textAlign: 'center'
          }}>
            {vantagem.descricao}
          </p>
          
          <p style={{ 
            color: 'rgba(255, 255, 255, 0.8)', 
            fontSize: '16px',
            marginBottom: '8px',
            textAlign: 'center'
          }}>
            <strong>Custo:</strong> {vantagem.custoMoedas} moedas
          </p>
          
          <p style={{ 
            color: 'rgba(255, 255, 255, 0.8)', 
            fontSize: '16px',
            marginBottom: '8px',
            textAlign: 'center'
          }}>
            <strong>Disponível:</strong> {vantagem.quantidade} {vantagem.quantidade === 1 ? 'unidade' : 'unidades'}
          </p>

          {vantagem.empresaNome && (
            <p style={{ 
              color: 'rgba(255, 255, 255, 0.7)', 
              fontSize: '14px',
              fontStyle: 'italic',
              textAlign: 'center'
            }}>
              {vantagem.empresaNome}
            </p>
          )}
          
          <p style={{ 
            color: 'rgba(255, 255, 255, 0.7)', 
            fontSize: '14px',
            marginTop: '16px',
            textAlign: 'center',
            lineHeight: '1.6'
          }}>
            Tem certeza que deseja resgatar esta vantagem?<br />
            O valor será debitado do seu saldo de moedas.
          </p>
        </div>

        <div className={styles.buttonGroup}>
          <button 
            type="button" 
            className={`${styles.button} ${styles.secondaryButton}`} 
            onClick={onClose}
            disabled={loading}
          >
            Cancelar
          </button>
          <button 
            type="button" 
            className={`${styles.button} ${styles.primaryButton}`}
            onClick={handleResgatar}
            disabled={loading}
          >
            {loading ? 'Resgatando...' : 'Confirmar Resgate'}
          </button>
        </div>
      </div>
    </div>
  );
}
