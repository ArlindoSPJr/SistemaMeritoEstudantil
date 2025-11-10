import React, { useState, useEffect } from 'react';
import styles from './Modal.module.css';
import LoginService from '../Services/LoginService';
import AlunoService from '../Services/AlunoService';
import EmpresaService from '../Services/EmpresaService';
import ModalExcluirPerfil from './ModalExcluirPerfil';
import ModalEditarPerfil from './ModalEditarPerfil';

export default function ModalPerfil({ onClose }) {
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);

  const userRole = LoginService.getUserRole();
  const isEmpresaParceira = LoginService.hasRole('EMPRESA_PARCEIRA');
  const isAluno = LoginService.hasRole('ALUNO');

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const userId = LoginService.getUserId();
        if (!userId) {
          throw new Error('ID do usuário não encontrado');
        }

        let data;
        if (isEmpresaParceira) {
          data = await EmpresaService.getById(userId);
          // Mapeia os dados da empresa para o formato esperado
          setUserData({
            id: data.id,
            email: data.email,
            nomeFantasia: data.nomeFantasia,
            razaoSocial: data.razaoSocial,
            cnpj: data.cnpj,
            role: userRole
          });
        } else if (isAluno) {
          data = await AlunoService.getById(userId);
          // Mapeia os dados do aluno para o formato esperado
          setUserData({
            id: data.id,
            email: data.email,
            nome: data.nome,
            cpf: data.cpf,
            rg: data.rg,
            endereco: data.endereco,
            curso: data.curso,
            saldoMoedas: data.saldoMoedas,
            role: userRole
          });
        }
      } catch (error) {
        console.error('Erro ao buscar dados do usuário:', error);
        // Em caso de erro, define dados vazios
        setUserData({
          id: LoginService.getUserId(),
          role: userRole,
          email: 'Erro ao carregar'
        });
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, [userRole, isEmpresaParceira, isAluno]);

  const handleEdit = () => {
    setShowEditModal(true);
  };

  const handleDelete = () => {
    setShowDeleteModal(true);
  };

  const confirmDelete = async () => {
    try {
      const userId = LoginService.getUserId();
      if (isEmpresaParceira) {
        await EmpresaService.delete(userId);
      } else if (isAluno) {
        await AlunoService.delete(userId);
      }
      
      // Após excluir, faz logout e redireciona
      LoginService.logout({ redirect: true, redirectTo: '/login' });
    } catch (error) {
      console.error('Erro ao excluir perfil:', error);
      alert('Erro ao excluir perfil. Tente novamente.');
    }
  };

  const closeDeleteModal = () => {
    setShowDeleteModal(false);
  };

  const closeEditModal = () => {
    setShowEditModal(false);
  };

  const handleEditSuccess = () => {
    // Recarrega os dados do usuário após edição bem-sucedida
    const fetchUserData = async () => {
      try {
        const userId = LoginService.getUserId();
        if (!userId) {
          throw new Error('ID do usuário não encontrado');
        }

        let data;
        if (isEmpresaParceira) {
          data = await EmpresaService.getById(userId);
          setUserData({
            id: data.id,
            email: data.email,
            nomeFantasia: data.nomeFantasia,
            razaoSocial: data.razaoSocial,
            cnpj: data.cnpj,
            role: userRole
          });
        } else if (isAluno) {
          data = await AlunoService.getById(userId);
          setUserData({
            id: data.id,
            email: data.email,
            nome: data.nome,
            cpf: data.cpf,
            rg: data.rg,
            endereco: data.endereco,
            curso: data.curso,
            saldoMoedas: data.saldoMoedas,
            role: userRole
          });
        }
      } catch (error) {
        console.error('Erro ao recarregar dados do usuário:', error);
      }
    };

    fetchUserData();
  };

  if (loading) {
    return (
      <div className={styles.modalOverlay}>
        <div className={styles.modalContent}>
          <button className={styles.closeButton} onClick={onClose}>&times;</button>
          <h2 className={styles.modalTitle}>Carregando...</h2>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose}>&times;</button>
        <h2 className={styles.modalTitle}>Meu Perfil</h2>
        
        <div style={{ marginBottom: '24px' }}>
          <div style={{ 
            background: 'rgba(255, 255, 255, 0.05)', 
            padding: '20px', 
            borderRadius: '8px',
            border: '1px solid rgba(255, 255, 255, 0.1)'
          }}>
            <div style={{ display: 'flex', alignItems: 'center', marginBottom: '16px' }}>
              <div style={{ 
                width: '60px', 
                height: '60px', 
                borderRadius: '50%', 
                background: 'linear-gradient(90deg, var(--accent-from), var(--accent-to))',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                fontSize: '24px',
                marginRight: '16px'
              }}>
                {isEmpresaParceira ? '🏢' : '👤'}
              </div>
              <div>
                <h3 style={{ color: 'white', margin: '0 0 4px 0' }}>
                  {isEmpresaParceira ? userData?.nomeFantasia : userData?.nome}
                </h3>
                <p style={{ color: 'rgba(255, 255, 255, 0.7)', margin: 0, fontSize: '14px' }}>
                  {userRole?.replace('ROLE_', '').replace('_', ' ')}
                </p>
              </div>
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '12px' }}>
              {isEmpresaParceira && (
                <>
                  <div>
                    <label style={{ color: 'rgba(255, 255, 255, 0.7)', fontSize: '12px' }}>
                      Razão Social
                    </label>
                    <p style={{ color: 'white', margin: '4px 0 0 0' }}>
                      {userData?.razaoSocial}
                    </p>
                  </div>
                  <div>
                    <label style={{ color: 'rgba(255, 255, 255, 0.7)', fontSize: '12px' }}>
                      CNPJ
                    </label>
                    <p style={{ color: 'white', margin: '4px 0 0 0' }}>
                      {userData?.cnpj}
                    </p>
                  </div>
                </>
              )}

              {isAluno && (
                <>
                  <div>
                    <label style={{ color: 'rgba(255, 255, 255, 0.7)', fontSize: '12px' }}>
                      CPF
                    </label>
                    <p style={{ color: 'white', margin: '4px 0 0 0' }}>
                      {userData?.cpf}
                    </p>
                  </div>
                  <div>
                    <label style={{ color: 'rgba(255, 255, 255, 0.7)', fontSize: '12px' }}>
                      RG
                    </label>
                    <p style={{ color: 'white', margin: '4px 0 0 0' }}>
                      {userData?.rg}
                    </p>
                  </div>
                  <div>
                    <label style={{ color: 'rgba(255, 255, 255, 0.7)', fontSize: '12px' }}>
                      Endereço
                    </label>
                    <p style={{ color: 'white', margin: '4px 0 0 0' }}>
                      {userData?.endereco}
                    </p>
                  </div>
                  <div>
                    <label style={{ color: 'rgba(255, 255, 255, 0.7)', fontSize: '12px' }}>
                      Curso
                    </label>
                    <p style={{ color: 'white', margin: '4px 0 0 0' }}>
                      {userData?.curso}
                    </p>
                  </div>
                  <div>
                    <label style={{ color: 'rgba(255, 255, 255, 0.7)', fontSize: '12px' }}>
                      Saldo de Moedas
                    </label>
                    <p style={{ color: 'white', margin: '4px 0 0 0' }}>
                      {userData?.saldoMoedas || 0} moedas
                    </p>
                  </div>
                </>
              )}

              <div style={{ gridColumn: '1 / -1' }}>
                <label style={{ color: 'rgba(255, 255, 255, 0.7)', fontSize: '12px' }}>
                  Email
                </label>
                <p style={{ color: 'white', margin: '4px 0 0 0' }}>
                  {userData?.email}
                </p>
              </div>
            </div>
          </div>
        </div>

        <div className={styles.buttonGroup}>
          <button 
            type="button" 
            className={`${styles.button} ${styles.secondaryButton}`} 
            onClick={onClose}
          >
            Fechar
          </button>
          <button 
            type="button" 
            className={`${styles.button} ${styles.primaryButton}`}
            onClick={handleEdit}
          >
            ✏️ Editar
          </button>
          <button 
            type="button" 
            className={`${styles.button}`}
            onClick={handleDelete}
            style={{
              background: 'linear-gradient(90deg, #ef4444, #dc2626)',
              color: 'white'
            }}
          >
            🗑️ Excluir
          </button>
        </div>
      </div>

      {showDeleteModal && userData && (
        <ModalExcluirPerfil 
          userData={userData}
          isEmpresaParceira={isEmpresaParceira}
          onClose={closeDeleteModal}
          onConfirm={confirmDelete}
        />
      )}

      {showEditModal && userData && (
        <ModalEditarPerfil 
          userData={userData}
          isEmpresaParceira={isEmpresaParceira}
          onClose={closeEditModal}
          onSuccess={handleEditSuccess}
        />
      )}
    </div>
  );
}
