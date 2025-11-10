import React, { useState } from 'react';
import styles from './FormularioAutoCadastro.module.css';
import AlunoService from '../Services/AlunoService';

export default function FormularioAutoCadastroAluno({ onBack }) {
  const [formData, setFormData] = useState({
    email: '',
    senha: '',
    cpf: '',
    rg: '',
    nome: '',
    endereco: '',
    curso: '',
    saldoMoedas: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await AlunoService.create(formData);
      alert('Aluno cadastrado com sucesso!');
    } catch (error) {
      console.error('Erro ao cadastrar aluno:', error);
      alert('Erro ao cadastrar aluno.');
    }
  };

  return (
    <div className={styles.formContainer}>
      <form className={styles.card} onSubmit={handleSubmit}>
        <h2 className={styles.title}>Cadastro de Aluno</h2>
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
        <label className={styles.label} htmlFor="cpf">CPF</label>
        <input
          id="cpf"
          name="cpf"
          className={styles.input}
          type="text"
          value={formData.cpf}
          onChange={handleChange}
          required
        />
        <label className={styles.label} htmlFor="rg">RG</label>
        <input
          id="rg"
          name="rg"
          className={styles.input}
          type="text"
          value={formData.rg}
          onChange={handleChange}
          required
        />
        <label className={styles.label} htmlFor="nome">Nome</label>
        <input
          id="nome"
          name="nome"
          className={styles.input}
          type="text"
          value={formData.nome}
          onChange={handleChange}
          required
        />
        <label className={styles.label} htmlFor="endereco">Endereço</label>
        <input
          id="endereco"
          name="endereco"
          className={styles.input}
          type="text"
          value={formData.endereco}
          onChange={handleChange}
          required
        />
        <label className={styles.label} htmlFor="curso">Curso</label>
        <input
          id="curso"
          name="curso"
          className={styles.input}
          type="text"
          value={formData.curso}
          onChange={handleChange}
          required
        />
        <label className={styles.label} htmlFor="saldoMoedas">Saldo de Moedas</label>
        <input
          id="saldoMoedas"
          name="saldoMoedas"
          className={styles.input}
          type="number"
          value={formData.saldoMoedas}
          onChange={handleChange}
        />
        <button className={styles.button} type="submit">Cadastrar</button>
        <button className={styles.backButton} type="button" onClick={onBack}>Voltar</button>
      </form>
    </div>
  );
}
