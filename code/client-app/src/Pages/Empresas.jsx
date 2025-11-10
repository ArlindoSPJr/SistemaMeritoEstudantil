import React, { useEffect, useState } from 'react';
import EmpresaService from '../Services/EmpresaService';
import styles from './Empresa.module.css';
import Header from '../Components/header';

export default function Empresas() {
  const [empresas, setEmpresas] = useState([]);

  useEffect(() => {
    const fetchEmpresas = async () => {
      try {
        const data = await EmpresaService.list();
        setEmpresas(data);
      } catch (error) {
        console.error('Erro ao buscar empresas:', error);
      }
    };

    fetchEmpresas();
  }, []);

  return (
    <div className={styles.container}>
      <Header />
      <h1 className={styles.title}>Lista de Empresas</h1>
      <ul className={styles.list}>
        {empresas.map((empresa) => (
          <li key={empresa.id} className={styles.listItem}>
            <p><strong>Nome Fantasia:</strong> {empresa.nomeFantasia}</p>
            <p><strong>Razão Social:</strong> {empresa.razaoSocial}</p>
            <p><strong>Email:</strong> {empresa.email}</p>
            <p><strong>CNPJ:</strong> {empresa.cnpj}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}
