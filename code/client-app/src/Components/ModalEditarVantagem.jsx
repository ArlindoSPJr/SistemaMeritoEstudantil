import React, { useState, useEffect } from 'react';
import styles from './Modal.module.css';
import VantagensService from '../Services/VantagensService';

export default function ModalEditarVantagem({ vantagem, onClose, onSuccess }) {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    descricao: '',
    custoMoedas: '',
    quantidade: '',
    ativo: true,
    image: null
  });
  const [loading, setLoading] = useState(false);
  const [updatedVantagem, setUpdatedVantagem] = useState(null);

  useEffect(() => {
    if (vantagem) {
      setFormData({
        descricao: vantagem.descricao || '',
        custoMoedas: vantagem.custoMoedas?.toString() || '',
        quantidade: vantagem.quantidade?.toString() || '0',
        ativo: vantagem.ativo !== undefined ? vantagem.ativo : true,
        image: null
      });
    }
  }, [vantagem]);

  const handleChange = (e) => {
    const { name, value, type, checked, files } = e.target;
    
    if (name === 'image') {
      setFormData(prev => ({ ...prev, [name]: files[0] || null }));
    } else if (type === 'checkbox') {
      setFormData(prev => ({ ...prev, [name]: checked }));
    } else {
      setFormData(prev => ({ ...prev, [name]: value || '' }));
    }
  };

  const handleUpdateInfo = async (e) => {
    e.preventDefault();
    setLoading(true);
    
    try {
      const updateData = {
        descricao: formData.descricao,
        custoMoedas: parseFloat(formData.custoMoedas),
        quantidade: parseFloat(formData.quantidade),
        ativo: formData.ativo,
        imageUrl: vantagem.imageUrl // Preserva a imagem atual
      };

      console.log('Dados sendo enviados para atualização:', updateData);
      const updated = await VantagensService.update(vantagem.id, updateData);
      console.log('Vantagem atualizada:', updated);
      setUpdatedVantagem(updated);
      setStep(2);
    } catch (error) {
      console.error('Erro ao atualizar vantagem:', error);
      alert('Erro ao atualizar vantagem. Tente novamente.');
    } finally {
      setLoading(false);
    }
  };

  const handleUploadImage = async (e) => {
    e.preventDefault();
    setLoading(true);
    
    try {
      if (formData.image) {
        await VantagensService.uploadImage(vantagem.id, formData.image);
      }
      
      // Busca a vantagem atualizada com a nova imagem
      const finalVantagem = await VantagensService.getById(vantagem.id);
      onSuccess(finalVantagem);
      onClose();
      window.location.reload();
    } catch (error) {
      console.error('Erro ao fazer upload da imagem:', error);
      alert('Erro ao fazer upload da imagem. Tente novamente.');
    } finally {
      setLoading(false);
    }
  };

  const handleSkipImage = () => {
    // A imagem já foi preservada na etapa 1, então usamos a vantagem atualizada
    onSuccess(updatedVantagem);
    onClose();
    window.location.reload();
  };

  if (!vantagem) return null;

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose}>&times;</button>
        
        {step === 1 ? (
          <form className={styles.form} onSubmit={handleUpdateInfo}>
            <h2 className={styles.modalTitle}>Editar Vantagem - Etapa 1</h2>
            
            <div>
              <label className={styles.label} htmlFor="descricao">Descrição</label>
              <input
                id="descricao"
                name="descricao"
                className={styles.input}
                type="text"
                value={formData.descricao || ''}
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
                step="0.01"
                value={formData.custoMoedas || ''}
                onChange={handleChange}
                required
              />
            </div>

            <div>
              <label className={styles.label} htmlFor="quantidade">Quantidade</label>
              <input
                id="quantidade"
                name="quantidade"
                className={styles.input}
                type="number"
                value={formData.quantidade}
                onChange={handleChange}
                required
              />
            </div>

            <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
              <input
                id="ativo"
                name="ativo"
                type="checkbox"
                checked={formData.ativo}
                onChange={handleChange}
                style={{ width: 'auto' }}
              />
              <label className={styles.label} htmlFor="ativo" style={{ margin: 0 }}>
                Vantagem Ativa
              </label>
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
                type="submit" 
                className={`${styles.button} ${styles.primaryButton}`}
                disabled={loading}
              >
                {loading ? 'Salvando...' : 'Avançar'}
              </button>
            </div>
          </form>
        ) : (
          <form className={styles.form} onSubmit={handleUploadImage}>
            <h2 className={styles.modalTitle}>Editar Vantagem - Etapa 2</h2>
            
            <div>
              <label className={styles.label} htmlFor="image">Nova Imagem (opcional)</label>
              <input
                id="image"
                name="image"
                className={styles.input}
                type="file"
                accept="image/*"
                onChange={handleChange}
              />
              {vantagem.imageUrl && (
                <div style={{ marginTop: '8px' }}>
                  <span style={{ color: 'rgba(255, 255, 255, 0.7)', fontSize: '14px' }}>
                    Imagem atual:
                  </span>
                  <img 
                    src={vantagem.imageUrl} 
                    alt="Imagem atual" 
                    style={{ 
                      width: '60px', 
                      height: '60px', 
                      borderRadius: '50%', 
                      marginLeft: '8px',
                      objectFit: 'cover',
                      border: '2px solid rgba(255, 255, 255, 0.2)'
                    }} 
                  />
                </div>
              )}
            </div>

            <div className={styles.buttonGroup}>
              <button 
                type="button" 
                className={`${styles.button} ${styles.secondaryButton}`} 
                onClick={() => setStep(1)}
                disabled={loading}
              >
                Voltar
              </button>
              <button 
                type="button" 
                className={`${styles.button}`}
                onClick={handleSkipImage}
                disabled={loading}
                style={{ background: 'rgba(255, 255, 255, 0.1)', color: 'white' }}
              >
                Pular Imagem
              </button>
              <button 
                type="submit" 
                className={`${styles.button} ${styles.primaryButton}`}
                disabled={loading || !formData.image}
              >
                {loading ? 'Enviando...' : 'Finalizar'}
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
}
