import AlunoService from '../Services/AlunoService';

const handleSubmit = async (event) => {
  event.preventDefault();
  const aluno = {
    email: formValues.email,
    senha: formValues.senha,
    cpf: formValues.cpf,
    rg: formValues.rg,
    nome: formValues.nome,
    endereco: formValues.endereco,
    saldoMoedas: formValues.saldoMoedas,
    curso: formValues.curso,
  };
  try {
    await AlunoService.create(aluno);
    alert('Aluno cadastrado com sucesso!');
  } catch (error) {
    console.error('Erro ao cadastrar aluno:', error);
  }
};

<form onSubmit={handleSubmit}> {/* Existing form code */}</form>