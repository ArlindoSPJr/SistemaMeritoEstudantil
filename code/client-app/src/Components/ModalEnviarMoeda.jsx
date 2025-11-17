import React, { useState } from 'react';
import styles from './Modal.module.css';
import TransacaoService from '../Services/TransacaoService';

export default function ModalEnviarMoeda({ aluno, onClose, onSuccess }) {
  const [quantidade, setQuantidade] = useState(1);
  const [descricao, setDescricao] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  if (!aluno) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    if (!quantidade || quantidade < 1) {
      setError('Quantidade deve ser maior que 0');
      return;
    }
    setLoading(true);
    try {
      const dto = {
        alunoId: aluno.id,
        quantidadeMoedas: Number(quantidade),
        descricao: descricao || undefined
      };
      await TransacaoService.enviarMoedas(dto);
      setLoading(false);
      if (onSuccess) onSuccess();
      onClose();
    } catch (err) {
      setLoading(false);
      setError(err?.response?.data?.message || err.message || 'Erro ao enviar moedas');
    }
  };

  return (
    <div className={styles.modalOverlay} role="dialog" aria-modal="true">
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose} aria-label="Fechar">✕</button>
        <h2 className={styles.modalTitle}>Enviar moedas para {aluno.nome}</h2>

        <form className={styles.form} onSubmit={handleSubmit}>
          <label className={styles.label} htmlFor="quantidade">Quantidade</label>
          <input
            id="quantidade"
            className={styles.input}
            type="number"
            min="1"
            value={quantidade}
            onChange={(e) => setQuantidade(e.target.value)}
            required
          />

          <label className={styles.label} htmlFor="descricao">Descrição (opcional)</label>
          <textarea
            id="descricao"
            className={styles.input}
            rows="3"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
          />

          {error && <div style={{ color: 'salmon' }}>{error}</div>}

          <div className={styles.buttonGroup}>
            <button type="button" className={`${styles.button} ${styles.secondaryButton}`} onClick={onClose} disabled={loading}>Cancelar</button>
            <button type="submit" className={`${styles.button} ${styles.primaryButton}`} disabled={loading}>{loading ? 'Enviando...' : 'Enviar'}</button>
          </div>
        </form>
      </div>
    </div>
  );
}
