import React, { useState, useEffect } from 'react';
import styles from './Modal.module.css';
import AlunoService from '../Services/AlunoService';
import EmpresaService from '../Services/EmpresaService';
import LoginService from '../Services/LoginService';

export default function ModalEditarPerfil({ userData, isEmpresaParceira, onClose, onSuccess }) {
  const [formData, setFormData] = useState({
    email: '',
    nome: '',
    cpf: '',
    rg: '',
    endereco: '',
    curso: '',
    nomeFantasia: '',
    razaoSocial: '',
    cnpj: '',
    senha: ''
  });
  const [loading, setLoading] = useState(false);
  const [showPasswordField, setShowPasswordField] = useState(false);

  useEffect(() => {
    if (userData) {
      setFormData({
        email: userData.email || '',
        nome: userData.nome || '',
        cpf: userData.cpf || '',
        rg: userData.rg || '',
        endereco: userData.endereco || '',
        curso: userData.curso || '',
        nomeFantasia: userData.nomeFantasia || '',
        razaoSocial: userData.razaoSocial || '',
        cnpj: userData.cnpj || '',
        senha: ''
      });
    }
  }, [userData]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const userId = LoginService.getUserId();
      
      if (isEmpresaParceira) {
        const updateData = {
          email: formData.email,
          nomeFantasia: formData.nomeFantasia,
          razaoSocial: formData.razaoSocial,
          cnpj: formData.cnpj,
          senha: formData.senha || undefined // Só inclui senha se foi preenchida
        };
        
        await EmpresaService.update(userId, updateData);
      } else {
        const updateData = {
          email: formData.email,
          nome: formData.nome,
          cpf: formData.cpf,
          rg: formData.rg,
          endereco: formData.endereco,
          curso: formData.curso,
          senha: formData.senha || undefined, // Só inclui senha se foi preenchida
          saldoMoedas: userData.saldoMoedas // Mantém o saldo atual
        };
        
        await AlunoService.update(userId, updateData);
      }

      alert('Perfil atualizado com sucesso!');
      onSuccess?.();
      onClose();
      window.location.reload();
    } catch (error) {
      console.error('Erro ao atualizar perfil:', error);
      alert('Erro ao atualizar perfil. Verifique os dados e tente novamente.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose}>&times;</button>
        <h2 className={styles.modalTitle}>Editar Perfil</h2>
        
        <form onSubmit={handleSubmit} style={{ marginBottom: '24px' }}>
          <div style={{ display: 'grid', gap: '16px' }}>
            {/* Campo Email (comum para ambos) */}
            <div>
              <label style={{ 
                display: 'block', 
                color: 'rgba(255, 255, 255, 0.9)', 
                marginBottom: '8px',
                fontSize: '14px',
                fontWeight: '500'
              }}>
                Email *
              </label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                required
                className={styles.input}
                placeholder="Digite seu email"
              />
            </div>

            {/* Campos específicos para Aluno */}
            {!isEmpresaParceira && (
              <>
                <div>
                  <label style={{ 
                    display: 'block', 
                    color: 'rgba(255, 255, 255, 0.9)', 
                    marginBottom: '8px',
                    fontSize: '14px',
                    fontWeight: '500'
                  }}>
                    Nome Completo *
                  </label>
                  <input
                    type="text"
                    name="nome"
                    value={formData.nome}
                    onChange={handleInputChange}
                    required
                    className={styles.input}
                    placeholder="Digite seu nome completo"
                  />
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '12px' }}>
                  <div>
                    <label style={{ 
                      display: 'block', 
                      color: 'rgba(255, 255, 255, 0.9)', 
                      marginBottom: '8px',
                      fontSize: '14px',
                      fontWeight: '500'
                    }}>
                      CPF *
                    </label>
                    <input
                      type="text"
                      name="cpf"
                      value={formData.cpf}
                      onChange={handleInputChange}
                      required
                      className={styles.input}
                      placeholder="000.000.000-00"
                    />
                  </div>

                  <div>
                    <label style={{ 
                      display: 'block', 
                      color: 'rgba(255, 255, 255, 0.9)', 
                      marginBottom: '8px',
                      fontSize: '14px',
                      fontWeight: '500'
                    }}>
                      RG *
                    </label>
                    <input
                      type="text"
                      name="rg"
                      value={formData.rg}
                      onChange={handleInputChange}
                      required
                      className={styles.input}
                      placeholder="0.000.000"
                    />
                  </div>
                </div>

                <div>
                  <label style={{ 
                    display: 'block', 
                    color: 'rgba(255, 255, 255, 0.9)', 
                    marginBottom: '8px',
                    fontSize: '14px',
                    fontWeight: '500'
                  }}>
                    Endereço *
                  </label>
                  <input
                    type="text"
                    name="endereco"
                    value={formData.endereco}
                    onChange={handleInputChange}
                    required
                    className={styles.input}
                    placeholder="Digite seu endereço completo"
                  />
                </div>

                <div>
                  <label style={{ 
                    display: 'block', 
                    color: 'rgba(255, 255, 255, 0.9)', 
                    marginBottom: '8px',
                    fontSize: '14px',
                    fontWeight: '500'
                  }}>
                    Curso *
                  </label>
                  <input
                    type="text"
                    name="curso"
                    value={formData.curso}
                    onChange={handleInputChange}
                    required
                    className={styles.input}
                    placeholder="Digite seu curso"
                  />
                </div>
              </>
            )}

            {/* Campos específicos para Empresa */}
            {isEmpresaParceira && (
              <>
                <div>
                  <label style={{ 
                    display: 'block', 
                    color: 'rgba(255, 255, 255, 0.9)', 
                    marginBottom: '8px',
                    fontSize: '14px',
                    fontWeight: '500'
                  }}>
                    Nome Fantasia *
                  </label>
                  <input
                    type="text"
                    name="nomeFantasia"
                    value={formData.nomeFantasia}
                    onChange={handleInputChange}
                    required
                    className={styles.input}
                    placeholder="Digite o nome fantasia"
                  />
                </div>

                <div>
                  <label style={{ 
                    display: 'block', 
                    color: 'rgba(255, 255, 255, 0.9)', 
                    marginBottom: '8px',
                    fontSize: '14px',
                    fontWeight: '500'
                  }}>
                    Razão Social *
                  </label>
                  <input
                    type="text"
                    name="razaoSocial"
                    value={formData.razaoSocial}
                    onChange={handleInputChange}
                    required
                    className={styles.input}
                    placeholder="Digite a razão social"
                  />
                </div>

                <div>
                  <label style={{ 
                    display: 'block', 
                    color: 'rgba(255, 255, 255, 0.9)', 
                    marginBottom: '8px',
                    fontSize: '14px',
                    fontWeight: '500'
                  }}>
                    CNPJ *
                  </label>
                  <input
                    type="text"
                    name="cnpj"
                    value={formData.cnpj}
                    onChange={handleInputChange}
                    required
                    className={styles.input}
                    placeholder="00.000.000/0000-00"
                  />
                </div>
              </>
            )}

            {/* Campo de Senha (opcional) */}
            <div>
              <div style={{ 
                display: 'flex', 
                alignItems: 'center', 
                marginBottom: '8px',
                gap: '8px'
              }}>
                <input
                  type="checkbox"
                  id="changePassword"
                  checked={showPasswordField}
                  onChange={(e) => {
                    setShowPasswordField(e.target.checked);
                    if (!e.target.checked) {
                      setFormData(prev => ({ ...prev, senha: '' }));
                    }
                  }}
                  style={{ margin: 0 }}
                />
                <label 
                  htmlFor="changePassword"
                  style={{ 
                    color: 'rgba(255, 255, 255, 0.9)', 
                    fontSize: '14px',
                    fontWeight: '500',
                    cursor: 'pointer'
                  }}
                >
                  Alterar senha
                </label>
              </div>
              
              {showPasswordField && (
                <input
                  type="password"
                  name="senha"
                  value={formData.senha}
                  onChange={handleInputChange}
                  className={styles.input}
                  placeholder="Digite a nova senha"
                  minLength="6"
                />
              )}
            </div>
          </div>
        </form>

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
            onClick={handleSubmit}
            disabled={loading}
          >
            {loading ? 'Salvando...' : 'Salvar Alterações'}
          </button>
        </div>
      </div>
    </div>
  );
}
