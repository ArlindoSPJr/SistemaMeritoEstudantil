import React, { useEffect, useState } from 'react';
import VantagensService from '../Services/VantagensService';
import LoginService from '../Services/LoginService';
import styles from './Vantagens.module.css';
import Header from '../Components/header';
import ModalCriarVantagem from '../Components/ModalCriarvantagem';
import ModalExcluir from '../Components/ModalExcluir';
import ModalEditarVantagem from '../Components/ModalEditarVantagem';
import ModalResgatarVantagem from '../Components/ModalResgatarVantagem';

export default function Vantagens() {
  const [vantagens, setVantagens] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [showResgatarModal, setShowResgatarModal] = useState(false);
  const [vantagemToDelete, setVantagemToDelete] = useState(null);
  const [vantagemToEdit, setVantagemToEdit] = useState(null);
  const [vantagemToResgatar, setVantagemToResgatar] = useState(null);

  // Verificar se é empresa parceira
  const isEmpresaParceira = LoginService.hasRole('EMPRESA_PARCEIRA');
  const userId = LoginService.getUserId();

  useEffect(() => {
    const fetchVantagens = async () => {
      try {
        let data;
        if (isEmpresaParceira && userId) {
          // Se é empresa parceira, busca apenas suas vantagens
          data = await VantagensService.listByEmpresa(userId);
        } else {
          // Se é aluno ou outro role, busca todas as vantagens
          data = await VantagensService.list();
        }
        setVantagens(data);
      } catch (error) {
        console.error('Erro ao buscar vantagens:', error);
      }
    };

    fetchVantagens();
  }, [isEmpresaParceira, userId]);

  const handleEdit = (vantagem) => {
    setVantagemToEdit(vantagem);
    setShowEditModal(true);
  };

  const handleDelete = (vantagem) => {
    setVantagemToDelete(vantagem);
    setShowDeleteModal(true);
  };

  const confirmDelete = async (vantagemId) => {
    try {
      await VantagensService.delete(vantagemId);
      // Atualizar a lista após deletar
      setVantagens(prev => prev.filter(v => v.id !== vantagemId));
    } catch (error) {
      console.error('Erro ao deletar vantagem:', error);
      alert('Erro ao deletar vantagem. Tente novamente.');
    }
  };

  const closeDeleteModal = () => {
    setShowDeleteModal(false);
    setVantagemToDelete(null);
  };

  const closeEditModal = () => {
    setShowEditModal(false);
    setVantagemToEdit(null);
  };

  const handleEditSuccess = (updatedVantagem) => {
    // Atualizar a vantagem na lista
    setVantagens(prev => 
      prev.map(v => v.id === updatedVantagem.id ? updatedVantagem : v)
    );
  };

  const handleResgatar = (vantagem) => {
    setVantagemToResgatar(vantagem);
    setShowResgatarModal(true);
  };

  const closeResgatarModal = () => {
    setShowResgatarModal(false);
    setVantagemToResgatar(null);
  };

  const handleResgatarSuccess = () => {
    // Recarregar lista de vantagens após resgate
    setVantagens([]);
  };

  return (
    <div className={styles.container}>
      <Header />
      <div className={styles.header}>
        <h1 className={styles.title}>{isEmpresaParceira ? 'Minhas Vantagens' : 'Vantagens'}</h1>
        {isEmpresaParceira && (
          <button className={styles.addButton} onClick={() => setShowModal(true)}>
            Criar Vantagem
          </button>
        )}
      </div>
      <div className={styles.grid}>
        {vantagens.map((vantagem) => (
          <div key={vantagem.id} className={styles.card}>
            <div className={styles.cardMain}>
              <div className={styles.imageContainer}>
                {vantagem.imageUrl ? (
                  <img
                    src={vantagem.imageUrl}
                    alt={vantagem.descricao}
                    className={styles.cardImage}
                  />
                ) : (
                  <div className={styles.imagePlaceholder}>
                    <span>📦</span>
                  </div>
                )}
              </div>
              <div className={styles.cardContent}>
                <h3 className={styles.cardTitle}>{vantagem.descricao}</h3>
                <p className={styles.cardPrice}>Custo: {vantagem.custoMoedas} moedas</p>
                <p className={styles.cardQuantity}>Quantidade: {vantagem.quantidade}</p>
                {!isEmpresaParceira && vantagem.empresaNome && (
                  <p className={styles.cardCompany}>Empresa: {vantagem.empresaNome}</p>
                )}
              </div>
            </div>
            {!isEmpresaParceira && (
              <div className={styles.cardActions}>
                <button 
                  className={styles.resgatarButton} 
                  title="Resgatar Vantagem"
                  onClick={() => handleResgatar(vantagem)}
                  disabled={!vantagem.ativo || vantagem.quantidade <= 0}
                >
                  🎁 Resgatar
                </button>
              </div>
            )}
            {isEmpresaParceira && (
              <div className={styles.cardActions}>
                <button 
                  className={styles.editButton} 
                  title="Editar"
                  onClick={() => handleEdit(vantagem)}
                >
                  ✏️
                </button>
                <button 
                  className={styles.deleteButton} 
                  title="Deletar"
                  onClick={() => handleDelete(vantagem)}
                >
                  🗑️
                </button>
              </div>
            )}
          </div>
        ))}
      </div>
      {isEmpresaParceira && showModal && <ModalCriarVantagem onClose={() => setShowModal(false)} />}
      {isEmpresaParceira && showDeleteModal && vantagemToDelete && (
        <ModalExcluir 
          vantagem={vantagemToDelete}
          onClose={closeDeleteModal}
          onConfirm={confirmDelete}
        />
      )}
      {isEmpresaParceira && showEditModal && vantagemToEdit && (
        <ModalEditarVantagem 
          vantagem={vantagemToEdit}
          onClose={closeEditModal}
          onSuccess={handleEditSuccess}
        />
      )}
      {!isEmpresaParceira && showResgatarModal && vantagemToResgatar && (
        <ModalResgatarVantagem 
          vantagem={vantagemToResgatar}
          onClose={closeResgatarModal}
          onSuccess={handleResgatarSuccess}
        />
      )}
    </div>
  );
}
