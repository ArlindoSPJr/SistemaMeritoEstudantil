import React, { useState } from 'react';
import styles from './FormularioAutoCadastro.module.css';
import EmpresaService from '../Services/EmpresaService';

export default function FormularioAutoCadastroEmpresa({ onBack }) {
  const [formData, setFormData] = useState({
    email: '',
    senha: '',
    nomeFantasia: '',
    razaoSocial: '',
    cnpj: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await EmpresaService.create(formData);
      alert('Empresa cadastrada com sucesso!');
    } catch (error) {
      console.error('Erro ao cadastrar empresa:', error);
      alert('Erro ao cadastrar empresa.');
    }
  };

  return (
    <div className={styles.formContainer}>
      <form className={styles.card} onSubmit={handleSubmit}>
        <h2 className={styles.title}>Cadastro de Empresa</h2>
        <label className={styles.label} htmlFor="email">Email</label>
        <input
          id="email"
          name="email"
          className={styles.input}
          type="email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <label className={styles.label} htmlFor="senha">Senha</label>
        <input
          id="senha"
          name="senha"
          className={styles.input}
          type="password"
          value={formData.senha}
          onChange={handleChange}
          required
        />
        <label className={styles.label} htmlFor="nomeFantasia">Nome Fantasia</label>
        <input
          id="nomeFantasia"
          name="nomeFantasia"
          className={styles.input}
          type="text"
          value={formData.nomeFantasia}
          onChange={handleChange}
          required
        />
        <label className={styles.label} htmlFor="razaoSocial">Razão Social</label>
        <input
          id="razaoSocial"
          name="razaoSocial"
          className={styles.input}
          type="text"
          value={formData.razaoSocial}
          onChange={handleChange}
          required
        />
        <label className={styles.label} htmlFor="cnpj">CNPJ</label>
        <input
          id="cnpj"
          name="cnpj"
          className={styles.input}
          type="text"
          value={formData.cnpj}
          onChange={handleChange}
          required
        />
        <button className={styles.button} type="submit">Cadastrar</button>
        <button className={styles.backButton} type="button" onClick={onBack}>Voltar</button>
      </form>
    </div>
  );
}