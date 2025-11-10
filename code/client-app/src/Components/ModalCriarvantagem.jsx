import React, { useState } from 'react';
import styles from './Modal.module.css';
import VantagensService from '../Services/VantagensService';

export default function ModalCriarVantagem({ onClose }) {
  const [step, setStep] = useState(1);
  const [vantagemId, setVantagemId] = useState(null);
  const [formData, setFormData] = useState({
    descricao: '',
    custoMoedas: '',
    image: null
  });

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (name === 'image') {
      setFormData(prev => ({ ...prev, [name]: files[0] }));
    } else {
      setFormData(prev => ({ ...prev, [name]: value }));
    }
  };

  const handleCreateVantagem = async (e) => {
    e.preventDefault();
    try {
      const vantagemData = {
        descricao: formData.descricao,
        custoMoedas: parseInt(formData.custoMoedas, 10),
        ativo: true
      };

      const response = await VantagensService.create(vantagemData);
      setVantagemId(response.id);
      setStep(2);
    } catch (error) {
      console.error('Erro ao criar vantagem:', error);
    }
  };

  const handleUploadImage = async (e) => {
    e.preventDefault();
    try {
      if (formData.image && vantagemId) {
        await VantagensService.uploadImage(vantagemId, formData.image);
        onClose();
        window.location.reload();
      }
    } catch (error) {
      console.error('Erro ao fazer upload da imagem:', error);
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose}>&times;</button>
        {step === 1 ? (
          <form className={styles.form} onSubmit={handleCreateVantagem}>
            <h2 className={styles.modalTitle}>Criar Nova Vantagem - Etapa 1</h2>
            <div>
              <label className={styles.label} htmlFor="descricao">Descrição</label>
              <input
                id="descricao"
                name="descricao"
                className={styles.input}
                type="text"
                value={formData.descricao}
                onChange={handleChange}
                required
              />
            </div>
            <div>
              <label className={styles.label} htmlFor="custoMoedas">Custo em Moedas</label>
              <input
                id="custoMoedas"
                name="custoMoedas"
                className={styles.input}
                type="number"
                value={formData.custoMoedas}
                onChange={handleChange}
                required
              />
            </div>
            <div className={styles.buttonGroup}>
              <button type="button" className={`${styles.button} ${styles.secondaryButton}`} onClick={onClose}>
                Cancelar
              </button>
              <button type="submit" className={`${styles.button} ${styles.primaryButton}`}>
                Avançar
              </button>
            </div>
          </form>
        ) : (
          <form className={styles.form} onSubmit={handleUploadImage}>
            <h2 className={styles.modalTitle}>Criar Nova Vantagem - Etapa 2</h2>
            <div>
              <label className={styles.label} htmlFor="image">Imagem da Vantagem</label>
              <input
                id="image"
                name="image"
                className={styles.input}
                type="file"
                accept="image/*"
                onChange={handleChange}
                required
              />
            </div>
            <div className={styles.buttonGroup}>
              <button type="button" className={`${styles.button} ${styles.secondaryButton}`} onClick={() => setStep(1)}>
                Voltar
              </button>
              <button type="submit" className={`${styles.button} ${styles.primaryButton}`}>
                Finalizar
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
}
